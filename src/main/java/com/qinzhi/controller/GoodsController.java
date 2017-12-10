 package com.qinzhi.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.qinzhi.bean.Constants;
import com.qinzhi.bean.JsonResult;
import com.qinzhi.bean.ResultMessage;
import com.qinzhi.domain.Goods;
import com.qinzhi.domain.Level;
import com.qinzhi.domain.SysOperator;
import com.qinzhi.security.ShiroPrincipal;
import com.qinzhi.security.ShiroUtils;
import com.qinzhi.service.IGoodsService;
import com.qinzhi.service.ILevelService;
import com.qinzhi.service.ISystemService;
import com.qinzhi.utils.ControllerUtil;
import com.qinzhi.utils.ExcelUtil;
import com.qinzhi.utils.JsonUtils;
import com.qinzhi.utils.Pageable;
import com.qinzhi.utils.RenderUtil;

@Controller
public class GoodsController {

	private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

	private static final String IMAGE_URI = "upload";
	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private ISystemService systemService;

	@Autowired
	private ILevelService service;

	@RequestMapping(value = "/to_goods_list")
	public String toGoodsList(Goods goods, Model model) {
		if (null != goods && null != goods.getOperatorId()) {
			model.addAttribute("operatorId", goods.getOperatorId());
		}
		return "/system/goods";
	}

	@RequestMapping("/findGoodsList.json")
	public void findGoodsList(Goods goods, HttpServletResponse response, Model model) {
		Pageable<Goods> pageable = null;
		try {
			pageable = this.goodsService.findGoodsList(goods);
			model.addAttribute("operatorId", goods.getOperatorId());
		} catch (Exception e) {
			logger.error("Class ： GoodsController -> Method: findGoodsList -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(pageable), response);
	}

	@RequestMapping("/addGoods.json")
	public void addGoods(HttpServletResponse response, Goods goods) {
		Goods queryGoods = new Goods();
		ResultMessage result = new ResultMessage();
		result.setSuccess(Constants.RESULT_SUCCESS);
		try {
			ShiroPrincipal shiroPrincipal = ShiroUtils.getPrincipal();
			SysOperator o = this.systemService.getOperatorById(shiroPrincipal.getId());
			goods.setOperatorId(shiroPrincipal.getId());
			queryGoods.setOperatorId(shiroPrincipal.getId());
			Level level = this.service.getLevelById(o.getLevelId());
			Integer count = this.goodsService.getCount(queryGoods);
			if (o.getLevelId() == 1) {
				Integer limit = Integer.valueOf(level.getLevelLimit());
				if (limit >= count) {
					result.setSuccess(Constants.RESULT_FAIL);
					result.setMessage("导入商品数超过您目前的会员级别,请升级会员级别");
					RenderUtil.renderText(result.getMessage(), response);
					return;
				}
			} else {
				float money = (count + 1) * Float.valueOf(level.getLevelWelfare());
				Integer hasMoney = Integer.valueOf(level.getLevelDesc());
				if (money > hasMoney) {
					result.setSuccess(Constants.RESULT_FAIL);
					result.setMessage("导入商品数超过您目前的会员级别,请升级会员级别");
					RenderUtil.renderText(result.getMessage(), response);
					return;
				}
			}
			this.goodsService.saveGoods(goods);
			RenderUtil.renderText(Constants.SUCCESS, response);
		} catch (Exception e) {
			logger.error("Class ： GoodsController -> Method: addGoods -> Exception: {}", e);
			result.setSuccess(Constants.RESULT_FAIL);
			result.setMessage("新增出现异常");
			RenderUtil.renderText(result.getMessage(), response);
		}
	}

	@RequestMapping("/update_goods.json")
	public void Gupdateoods(HttpServletResponse response, Goods goods) {
		try {
			this.goodsService.updateGoods(goods);
			RenderUtil.renderText(Constants.SUCCESS, response);
		} catch (Exception e) {
			logger.error("Class ： GoodsController -> Method: addGoods -> Exception: {}", e);
			RenderUtil.renderText(Constants.FAIL, response);
		}
	}

	/**
	 * 删除商品
	 *
	 * @param ids
	 * @param request
	 * @author liwei
	 * @since 2014-12-1
	 */
	@RequestMapping(value = "/delete_goods.json")
	public void deleteOperator(@RequestParam(value = "ids", defaultValue = "") String ids, HttpServletRequest request,
			HttpServletResponse response) {
		Boolean flag = true;
		try {
			if (StringUtils.isNotEmpty(ids)) {
				flag = this.goodsService.deleteGoods(ids);
			}
		} catch (Exception e) {
			logger.error("Class: SystemController -> Method: deleteOperator -> Exception: {}", e);
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	@ResponseBody
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String uploadImage(HttpServletRequest request, @RequestParam("goodsImages") MultipartFile file)
			throws IOException {
		String getOriginalFilename = file.isEmpty() ? null : file.getOriginalFilename();
		String requestUrl = request.getScheme() // 当前链接使用的协议
				+ "://" + request.getServerName()// 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath();
		logger.info("服务地址: " + requestUrl);
		logger.info("文件原名: " + getOriginalFilename);
		logger.info("文件名称: " + file.getName());
		logger.info("文件长度: " + file.getSize());
		logger.info("文件类型: " + file.getContentType());
		if (file.isEmpty()) {
			logger.error("upload image--------------------------------->failed");
			return "请选择一张图片"; 
		}
		String finalFileName = System.nanoTime()
				+ getOriginalFilename.substring(getOriginalFilename.lastIndexOf("."), getOriginalFilename.length());
		String realPath = request.getSession().getServletContext().getRealPath("/" + IMAGE_URI);
		/** 写入地址中 */
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, finalFileName));
		String resultUrl = requestUrl + "/" + IMAGE_URI + "/" + finalFileName;
		logger.info("upload image file result----------------------->" + resultUrl);
		return resultUrl;
	}

	@RequestMapping(value = "/get_goods.json")
	@ResponseBody
	public Goods getGoods(@RequestParam(value = "id", defaultValue = "") Long id) {
		if (id != null) {
			try {
				Goods goods = this.goodsService.getGoodsById(id);
				if (null != goods && null != goods.getGoodsDate()) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String dateString = formatter.format(goods.getGoodsDate());
					Date date = formatter.parse(dateString);
					goods.setGoodsDate(date);
				}
				return goods;
			} catch (Exception e) {
				logger.error("Class: SystemController -> Method: getOperatorInfo -> Exception:{}", e);
			}
		}
		logger.debug(" to find Goods info by id {}", id);
		return null;
	}

	@RequestMapping("/loadGoods")
	public void leadInExcel(HttpServletResponse response, @RequestParam(value = "excel") CommonsMultipartFile excel) {
		InputStream in;
		JsonResult result = new JsonResult(JsonResult.STATUS_SUCCESS, "");
		ShiroPrincipal shiroPrincipal = ShiroUtils.getPrincipal();
		Goods queryGoods = new Goods();
		try {
			SysOperator o = this.systemService.getOperatorById(shiroPrincipal.getId());
			queryGoods.setOperatorId(shiroPrincipal.getId());
			Level level = this.service.getLevelById(o.getLevelId());
			Integer count = this.goodsService.getCount(queryGoods);
			boolean isE2007 = true;
			// 判断是否是excel2007格式
			if (excel.getFileItem().getName().endsWith("xls")) {
				isE2007 = false;
			}
			in = excel.getInputStream();
			Map<String, Object> list = new HashMap<>();
			list = ExcelUtil.parseGoodsExcel(in, isE2007);
			String error = (String) list.get("message"); 
			int i = 0;
			if ("success".equals(error)) {
				List<Goods> goodss = (List<Goods>) list.get("goods");
				if (o.getLevelId() == 1) {
					Integer limit = Integer.valueOf(level.getLevelLimit());
					if (limit <= count + goodss.size()) {
						result.setStatus(JsonResult.STATUS_FAILED);
						result.setMessage("导入商品数超过您目前的会员级别,请升级会员级别");
					}
				} else {
					float money = (count + goodss.size()) * Float.valueOf(level.getLevelWelfare());
					Integer hasMoney = Integer.valueOf(level.getLevelDesc());
					if (money > hasMoney) {
						result.setStatus(JsonResult.STATUS_FAILED);
						result.setMessage("导入商品数超过您目前的会员级别,请升级会员级别");
					}
				}
				if (result.getStatus().equals(JsonResult.STATUS_SUCCESS)) {
					for (Goods goods : goodss) {
						goods.setOperatorId(shiroPrincipal.getId());
						this.goodsService.saveGoods(goods);
						i++;
					}
					String message = "成功导入[" + i + "]";
					result.setMessage(message);
				}
			} else {
				result.setStatus(JsonResult.STATUS_FAILED);
				result.setMessage(error);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(JsonResult.STATUS_FAILED);
			result.setMessage("导入出现异常");
		}
		RenderUtil.renderJson(JsonUtils.toJson(result), response);
	}

}
