package com.qinzhi.controller;

import com.qinzhi.bean.*;
import com.qinzhi.domain.Goods;
import com.qinzhi.domain.Punchlog;
import com.qinzhi.domain.SysOperator;
import com.qinzhi.entity.TokenTable;
import com.qinzhi.service.IGoodsService;
import com.qinzhi.service.IPunchlogService;
import com.qinzhi.service.ISystemService;
import com.qinzhi.service.TokenService;
import com.qinzhi.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 主控制器
 */
@Controller
@RequestMapping("/app")
public class AppController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

	private static final Integer TYPE_TOKEN_BUSINESS = 0;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private ISystemService systemService;

	@Autowired
	private IPunchlogService logService;

	@Autowired
	private TokenService tokenService;

	/**
	 * <普通用户注册>
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public void addOperator(@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "nickName", defaultValue = "") String nickName,
			@RequestParam(value = "gender", defaultValue = "") String gender, HttpServletRequest request,
			HttpServletResponse response) {
		String message = "";
		ResultMessage result = new ResultMessage();
		result.setSuccess(Constants.RESULT_FAIL);
		try {
			SysOperator operator = new SysOperator();
			if (StringUtils.isNotEmpty(userName) && StringUtils.isNotBlank(password)) {
				SysOperator o = this.systemService.getOperatorByLoginName(userName);
				if (o != null) {
					message = "该账号已存在！";
					result.setMessage(message);
					RenderUtil.renderJson(JsonUtils.toJson(result), response);
				} else {
					operator.setOperatorLoginName(userName);
					// 普通消费者（类别2）
					operator.setOperatorState(2);
					// 设置密码
					operator.setOperatorPassword(password);
					if (StringUtils.isEmpty(nickName)) {
						operator.setOperatorName(userName);
					} else {
						operator.setOperatorName(nickName);
					}
					operator.setGender(gender);
					// 设置权限-普通用户
					String roles = "3";
					boolean flag = this.systemService.addOperator(operator, roles);

					if (flag) {
						result.setSuccess(Constants.RESULT_SUCCESS);
						RenderUtil.renderJson(JsonUtils.toJson(result), response);
					} else {
						message = "注册失败!";
						result.setMessage(message);
						RenderUtil.renderJson(JsonUtils.toJson(result), response);
					}
				}
			} else {
				message = "请填写手机号和密码!";
				result.setMessage(message);
				RenderUtil.renderJson(JsonUtils.toJson(result), response);
			}
		} catch (Exception e) {
			LOGGER.error("Class: AppController -> Method: addOperator -> Exception: {}", e);
			message = "注册失败";
			result.setMessage(message);
			RenderUtil.renderJson(JsonUtils.toJson(result), response);
		}
	}

	/**
	 * <用户登录>
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "state", defaultValue = "0") String state, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String message = "";
		ResultToken result = new ResultToken();
		result.setSuccess(Constants.RESULT_FAIL);
		Subject subject = SecurityUtils.getSubject();
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			message = "请填写手机号和密码!";
			result.setMessage(message);
			RenderUtil.renderJson(JsonUtils.toJson(result), response);
		} else {
			try {
				userName = state + "," + userName;
				SysOperator user = systemService.getByLoginNameAndState(userName);
				password = Digests.getMd5Bit32(password);
				if (password.equals(user.getOperatorPassword())) {
					TokenTable t = tokenService.getTokenById(user.getOperatorId());
					String tokenInfo = Token.getTokenString(session);
					Calendar c = Calendar.getInstance();
					c.setTime(new Date()); // 设置当前日期
					c.add(Calendar.MONTH, 1); // 日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
					Date invalidTime = c.getTime(); // 结果
					System.out.println(invalidTime);
					if (null != t) {
						t.setUpdateTime(new Date());
						t.setInvalidTime(invalidTime);
						t.setUserToken(tokenInfo);
					} else {
						t = new TokenTable();
						t.setId(user.getOperatorId());
						t.setUserId(user.getOperatorId());
						t.setCreateTime(new Date());
						t.setInvalidTime(invalidTime);
						t.setUserToken(tokenInfo);
					}
					tokenService.save(t);
					result.setToken(tokenInfo);
					result.setSuccess(Constants.RESULT_SUCCESS);
				} else {
					LOGGER.error("login error: {}", userName + "," + password + "," + user.getOperatorPassword());
					message = "登录失败，账号或密码不匹配";
				}
			} catch (UnknownAccountException ue) {
				LOGGER.error("login error: {}", ue);
				message = "登录失败，您输入的账号不存在";
			} catch (IncorrectCredentialsException ie) {
				LOGGER.error("login error: {}", ie);
				message = "登录失败，密码不匹配";
			} catch (Exception re) {
				LOGGER.error("login error: {}", re);
				message = "登录失败";
			} finally {
				result.setMessage(message);
				RenderUtil.renderJson(JsonUtils.toJson(result), response);
			}
		}
	}

	/**
	 * <用户重置密码>
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public void reset(@RequestParam(value = "token", defaultValue = "") String token,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "newpass", defaultValue = "") String newpass, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String message = "";
		ResultToken result = new ResultToken();
		result.setSuccess(Constants.RESULT_FAIL);
		Subject subject = SecurityUtils.getSubject();
		if (StringUtils.isBlank(token) || StringUtils.isBlank(password) || StringUtils.isBlank(newpass)) {
			message = "请填写完整信息!";
			result.setMessage(message);
			RenderUtil.renderJson(JsonUtils.toJson(result), response);
		} else {
			try {
				// 普通用户
				String state = "2";
				// 验证token
				ResultMessage checkResult = checkToken(token);
				// 验证成功
				if (Constants.RESULT_SUCCESS == checkResult.getSuccess()) {
					TokenTable table = tokenService.getTokenByToken(token);
					SysOperator user = systemService.getOperatorById(table.getUserId());
					password = Digests.getMd5Bit32(password);
					if (password.equals(user.getOperatorPassword())) {
						newpass = Digests.getMd5Bit32(newpass);
						user.setOperatorPassword(newpass);
						systemService.updateOperator(user);

						// 重新生成token
						TokenTable t = tokenService.getTokenById(user.getOperatorId());
						String tokenInfo = Token.getTokenString(session);
						Calendar c = Calendar.getInstance();
						c.setTime(new Date()); // 设置当前日期
						c.add(Calendar.MONTH, 1); // 日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
						Date invalidTime = c.getTime(); // 结果
						System.out.println(invalidTime);
						if (null != t) {
							t.setUpdateTime(new Date());
							t.setInvalidTime(invalidTime);
							t.setUserToken(tokenInfo);
						} else {
							t = new TokenTable();
							t.setId(user.getOperatorId());
							t.setUserId(user.getOperatorId());
							t.setCreateTime(new Date());
							t.setInvalidTime(invalidTime);
							t.setUserToken(tokenInfo);
						}
						tokenService.save(t);
						result.setSuccess(Constants.RESULT_SUCCESS);
					} else {
						LOGGER.error("login error: {}", token + "," + password + "," + user.getOperatorPassword());
						message = "重置失败，您输入的账号或密码不存在";
					}
				} else {
					// 验证失败，返回失败消息
					result.setSuccess(checkResult.getSuccess());
					message = checkResult.getMessage();
				}
			} catch (UnknownAccountException ue) {
				LOGGER.error("login error: {}", ue);
				message = "重置失败，您输入的账号不存在";
			} catch (IncorrectCredentialsException ie) {
				LOGGER.error("login error: {}", ie);
				message = "重置失败，密码不匹配";
			} catch (Exception re) {
				LOGGER.error("login error: {}", re);
				message = "重置失败";
			} finally {
				result.setMessage(message);
				RenderUtil.renderJson(JsonUtils.toJson(result), response);
			}
		}
	}

	/**
	 * 设定安全的密码，通过md5，生成32位加密密码 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(SysOperator sysOperator) {
		// byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
		// sysOperator.setSalt(EncodeUtils.hexEncode(salt));
		// byte[] hashPassword =
		// Digests.sha1(sysOperator.getOperatorPassword().getBytes(), salt,
		// Constants.HASH_INTERATIONS);
		// sysOperator.setOperatorPassword(EncodeUtils.hexEncode(hashPassword));
		String password32 = null;
		try {
			password32 = Digests.getMd5Bit32(sysOperator.getOperatorPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		sysOperator.setOperatorPassword(password32);
	}

	/**
	 * <通过读卡内容获取商品信息>
	 *
	 * @param id
	 * @param goodsCode
	 * @param token
	 * @param latitude
	 * @param longitude
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public void load(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "token", defaultValue = "") String token,
			@RequestParam(value = "latitude", required = true) String latitude,
			@RequestParam(value = "longitude", required = true) String longitude, HttpServletRequest request,
			HttpServletResponse response) {
		QueryResult result = new QueryResult();
		result.setSuccess(Constants.RESULT_SUCCESS);
		String message = "";
		try {
			Goods goods = goodsService.getGoodsById(id);
			if (null != goods) {

				Punchlog log = new Punchlog();
				// [登录--非登录]用户反复读卡 读几次就加几次
				String str = GetLocation.getAdd(longitude, latitude);
				if (StringUtils.isNotBlank(str)) {
					Address address = JsonUtils.parseJson(str, Address.class);
					if (null != address && !address.getAddrList().isEmpty()) {
						List<AddrList> list = address.getAddrList();
						for (AddrList addr : list) {
							String admName = addr.getAdmName();
							String name = addr.getName();
							goods.setGoodsPlace(admName + " " + name);
							log.setGoodsPlace(goods.getGoodsPlace());
							goods.setGoodsCount(null == goods.getGoodsCount() ? 1 : goods.getGoodsCount() + 1);
							goodsService.updateGoods(goods);
							break;
						}
					}
				}
				// 有token 记录该商户扫描的地点和次数
				if (StringUtils.isNotBlank(token)) {
					boolean validToken = false;

					TokenTable t = tokenService.getTokenByToken(token);
					Date now = new Date();
					if (null != t && token.equals(t.getUserToken()) && now.getTime() < t.getInvalidTime().getTime()) {
						validToken = true;
					}
					if (validToken) {
						log.setGoodsId(id);
						log.setGoodsDate(new Date());
						log.setGoodsName(goods.getGoodsName());
						log.setGoodsPrice(goods.getGoodsPrice());
						log.setUserId(t.getUserId());

						logService.savePunchlog(log);
					} else {
						message = "token失效,请重新登录";
						result.setSuccess(Constants.RESULT_FAIL);
						result.setMessage(message);
						RenderUtil.renderJson(JsonUtils.toJson(result), response);
						return;
					}
				}
				// 接口回调的商品信息;
				result.setData(goods);
			} else {
				message = "未查询到对应的商品信息";
				result.setSuccess(Constants.RESULT_FAIL);
				result.setMessage(message);
				RenderUtil.renderJson(JsonUtils.toJson(result), response);
			}

		} catch (Exception e) {
			LOGGER.error("Class : AppController ->Method : load-> Exception: {}", e);
			result.setSuccess(Constants.RESULT_FAIL);
			result.setMessage("查询商品出现异常!");
			result.setData(null);
		}
		RenderUtil.renderJson(JsonUtils.toJson(result), response);
	}

	@RequestMapping(value = "/queryHistory", method = RequestMethod.POST)
	public void findHistoryList(@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize, HttpServletResponse response) {
		Pageable<Punchlog> pageable = null;
		HistoryResult history = new HistoryResult();
		try {
			TokenTable t = tokenService.getTokenByToken(token);
			// 验证token
			ResultMessage checkResult = checkToken(token);
			// 验证成功
			if (Constants.RESULT_SUCCESS == checkResult.getSuccess()) {
				SysOperator o = this.systemService.getOperatorById(t.getUserId());
				if (null != o) {
					Punchlog log = new Punchlog();
					log.setUserId(o.getOperatorId());
					log.setPage(pageNum);
					log.setRows(pageSize);
					pageable = this.logService.findLogList(log);
					history.setSuccess(Constants.RESULT_SUCCESS);
					history.setCount(pageable.getTotal());
					List<QueryHistory> list = new ArrayList<QueryHistory>();
					for (Punchlog punch : pageable.getData()) {
						QueryHistory q = new QueryHistory();
						BeanUtils.copyProperties(punch, q);
						list.add(q);
					}
					history.setData(list);
				} else {
					history.setSuccess(Constants.RESULT_FAIL);
					history.setMessage("未查询到该用户的查询记录");
				}
			} else {
				// 验证失败，返回失败消息
				history.setSuccess(checkResult.getSuccess());
				history.setMessage(checkResult.getMessage());
			}
		} catch (Exception e) {
			history.setSuccess(Constants.RESULT_FAIL);
			history.setMessage("查询出现异常!");
			history.setData(null);
			LOGGER.error("Class ： AppController -> Method: findHistoryList -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(history), response);
	}

	/**
	 * 获取商品列表
	 * 
	 * @param token
	 * @param pageIndex
	 * @param pageSize
	 * @param noSendCard
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/findGoodsList", method = RequestMethod.POST)
	public void findGoodsList(@RequestParam(value = "token", defaultValue = "") String token,
			@RequestParam(value = "pageIndex", defaultValue = "1") String pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "10") String pageSize,
			@RequestParam(value = "noSendCard", defaultValue = "0") String noSendCard, HttpServletResponse response,
			Model model) {

		GoodsResult result = new GoodsResult();
		// 验证token
		ResultMessage checkResult = checkToken(token);
		// 验证成功
		if (Constants.RESULT_SUCCESS == checkResult.getSuccess()) {
			Goods goods = new Goods();
			goods.setOperatorId(Long.parseLong(checkResult.getMessage()));
			goods.setPage(Integer.parseInt(pageIndex));
			goods.setRows(Integer.parseInt(pageSize));
			// 只获取未发卡的商品
			if ("1".equals(noSendCard)) {
				goods.setGoodsIsSend(0);
			}
			try {
				// 获取商品列表信息
				Pageable<Goods> pageable = this.goodsService.findGoodsList(goods);
				result.setSuccess(Constants.RESULT_SUCCESS);
				result.setCount(pageable.getTotal());
				result.setData(pageable.getData());
			} catch (Exception e) {
				result.setSuccess(Constants.RESULT_FAIL);
				result.setMessage("查询出现异常!");
				result.setData(null);
				LOGGER.error("Class ： AppController -> Method: findGoodsList -> Exception: {}", e);
			}
		} else {
			// 验证失败，返回失败消息
			result.setSuccess(checkResult.getSuccess());
			result.setMessage(checkResult.getMessage());
		}
		RenderUtil.renderJson(JsonUtils.toJson(result), response);
		return;
	}

	/**
	 * 发卡成功确认
	 * 
	 * @param token
	 * @param number
	 */
	@RequestMapping(value = "/doGoodSend", method = RequestMethod.POST)
	public void doGoodSend(@RequestParam(value = "token", defaultValue = "") String token,
			@RequestParam(value = "number", required = true) String number, HttpServletResponse response) {
		String message = "";
		// 验证token
		ResultMessage result = checkToken(token);
		// 验证成功
		if (Constants.RESULT_SUCCESS == result.getSuccess()) {
			if (StringUtils.isNotBlank(number)) {
				try {
					Goods goods = goodsService.getGoodsById(Long.parseLong(number));
					if (null != goods) {
						// 设置为已发卡
						goods.setGoodsIsSend(1);
						goodsService.updateGoods(goods);
						result.setSuccess(Constants.RESULT_SUCCESS);
					} else {
						message = "未查询到对应的商品信息";
						result.setSuccess(Constants.RESULT_FAIL);
						result.setMessage(message);
					}

				} catch (Exception e) {
					LOGGER.error("Class : AppController ->Method : load-> Exception: {}", e);
					result.setSuccess(Constants.RESULT_FAIL);
					result.setMessage("查询商品出现异常!");
				}
			} else {
				message = "商品编号不能为空";
				result.setSuccess(Constants.RESULT_FAIL);
				result.setMessage(message);
			}
		}
		RenderUtil.renderJson(JsonUtils.toJson(result), response);
		return;
	}

	private ResultMessage checkToken(String token) {
		ResultMessage result = new ResultMessage();
		String message = "";
		// 判断是否为空
		if (StringUtils.isNotBlank(token)) {
			boolean validToken = false;

			TokenTable t = tokenService.getTokenByToken(token);
			Date now = new Date();
			if (null != t && token.equals(t.getUserToken()) && now.getTime() < t.getInvalidTime().getTime()) {
				validToken = true;
			}
			if (validToken) {
				result.setSuccess(Constants.RESULT_SUCCESS);
				result.setMessage(t.getUserId().toString());
			} else {
				message = "token失效,请重新登录";
				result.setSuccess(Constants.RESULT_FAIL);
				result.setMessage(message);
			}
		} else {
			message = "token不能为空";
			result.setSuccess(Constants.RESULT_FAIL);
			result.setMessage(message);
		}
		return result;
	}

}
