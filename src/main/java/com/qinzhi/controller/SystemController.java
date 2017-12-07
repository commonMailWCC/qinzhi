package com.qinzhi.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.common.collect.Lists;
import com.qinzhi.bean.ComboTree;
import com.qinzhi.bean.Constants;
import com.qinzhi.bean.JsonResult;
import com.qinzhi.bean.TextValue;
import com.qinzhi.bean.Tree;
import com.qinzhi.bean.TreeNode;
import com.qinzhi.domain.Level;
import com.qinzhi.domain.SysAuthority;
import com.qinzhi.domain.SysDic;
import com.qinzhi.domain.SysOperator;
import com.qinzhi.domain.SysRole;
import com.qinzhi.domain.SysRoleAuthorityMap;
import com.qinzhi.service.ILevelService;
import com.qinzhi.service.ISystemService;
import com.qinzhi.utils.ControllerUtil;
import com.qinzhi.utils.EasyUITreeUtil;
import com.qinzhi.utils.ExcelUtil;
import com.qinzhi.utils.JsonUtils;
import com.qinzhi.utils.Pageable;
import com.qinzhi.utils.RenderUtil;
import com.qinzhi.utils.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/web")
public class SystemController {
	private static final String ROLE = "role";

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

	private static final String OPERATOR = "operator";

	private static final String R_ID = "rId";

	// 用户类型: 0=商户
	private static final Integer OPERATOR_STATE_NORMAL = 0;

	// 用户角色: 2=商户
	private static final String OPERATOR_role_NORMAL = "2";

	@Autowired
	private ISystemService systemService;

	@Autowired
	private ILevelService service;

	@RequestMapping(value = "/to_authority_list")
	public String toAuthorityList() {
		return "/system/authority_list";
	}

	@RequestMapping(value = "/to_role_list")
	public String toRoleList() {
		return "/system/role_list";
	}

	@RequestMapping(value = "/to_dic_list")
	public String todicList() {
		return "/system/dic_list";
	}

	@RequestMapping(value = "/to_operator_list")
	public String toOperatorList(@RequestParam(value = "operatorId", defaultValue = "") Long operatorId,
			ModelMap model) {
		if (null != operatorId) {
			model.addAttribute("operatorId", operatorId);
		}
		return "/system/operator_list";
	}

	@RequestMapping(value = "/get_operator_info.json")
	@ResponseBody
	public SysOperator getOperatorInfo(@RequestParam(value = "id", defaultValue = "") Long id) {
		if (id != null) {
			try {
				List<Level> levelList = this.service.findLevelList(null);
				return this.systemService.getOperatorById(id);
			} catch (Exception e) {
				LOGGER.error("Class: SystemController -> Method: getOperatorInfo -> Exception:{}", e);
			}
		}
		LOGGER.debug("Class: SystemController ->Method :getOperatorInfo ->  to find operator info by id {}", id);
		return null;
	}

	/**
	 * 系统角色列表
	 *
	 * @param response
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/system_role_list.json")
	public void getSystemRole(HttpServletResponse response) {
		List<SysRole> roleList;
		List<TextValue> tv = null;
		try {
			roleList = this.systemService.findAllRole();
			tv = new ArrayList<TextValue>();
			for (SysRole role : roleList) {
				tv.add(new TextValue(role.getRoleId().toString(), role.getRoleName()));
			}
		} catch (Exception e) {
			LOGGER.error("Class: SystemController -> Method: getSystemRole -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(tv), response);
	}

	/**
	 * 增加管理员前期操作
	 *
	 * @param model
	 * @return String
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/operator_to_add")
	public String toAddOperator(ModelMap model) {
		try {
			List<SysRole> listRole = this.systemService.findAllRole();
			model.put("listRole", listRole);
		} catch (Exception e) {
			LOGGER.error("Class: SystemController -> Method: toAddOperator -> Exception: {}", e);
		}
		return "/system/add_operator";
	}

	/**
	 * 删除操作员
	 *
	 * @param ids
	 * @param request
	 * @author liwei
	 * @since 2014-12-1
	 */
	@RequestMapping(value = "/delete_operator")
	public void deleteOperator(@RequestParam(value = "ids", defaultValue = "") String ids, HttpServletRequest request,
			HttpServletResponse response) {
		Boolean flag = true;
		try {
			if (StringUtils.isNotEmpty(ids)) {
				flag = this.systemService.deleteOperator(ids);
			}
		} catch (Exception e) {
			LOGGER.error("Class: SystemController -> Method: deleteOperator -> Exception: {}", e);
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	@RequestMapping("/loaduser")
	public void leadInExcel(HttpServletResponse response, @RequestParam(value = "excel") CommonsMultipartFile excel) {
		JSONObject jso = new JSONObject();
		InputStream in;
		JsonResult result = new JsonResult(JsonResult.STATUS_SUCCESS, "");
		try {
			boolean isE2007 = true;
			// 判断是否是excel2007格式
			if (excel.getFileItem().getName().endsWith("xls")) {
				isE2007 = false;
			}
			in = excel.getInputStream();
			Map<String, Object> list = new HashMap<>();
			list = ExcelUtil.parseExcel(in, isE2007);
			String error = (String) list.get("message");
			int i = 0;
			if ("success".equals(error)) {
				List<SysOperator> operators = (List<SysOperator>) list.get("operators");
				for (SysOperator operator : operators) {
					SysOperator O = this.systemService.getOperatorByLoginName(operator.getOperatorLoginName());
					if (O != null) {
						continue;
					}
					// 设置默认会员等级[专业级]
					operator.setLevelId(1L);
					// 设置默认的用户类型 0=商户
					operator.setOperatorState(OPERATOR_STATE_NORMAL);
					String roles = OPERATOR_role_NORMAL;
					boolean flag = this.systemService.addOperator(operator, roles);
					if (flag) {
						i++;
					}
				}
				String message = "成功导入[" + i + "]";
				result.setMessage(message);
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

	@RequestMapping("/get_level_list.json")
	public void get_level_list(HttpServletResponse response, Model model) {
		List<Level> levelList = null;
		List<Map<String, String>> list = new ArrayList<>();
		try {
			levelList = this.service.findLevelList(null);
			for (Level level : levelList) {
				Map map = new HashMap<>();
				map.put("id", level.getId());
				map.put("text", level.getLevelName());
				list.add(map);
			}
		} catch (Exception e) {
			LOGGER.error(" Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(list), response);
	}

	/**
	 * 检查登陆名是否存在
	 *
	 * @param loginName
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/check_login_name_exists")
	public void checkLoginNameExists(@RequestParam(value = "loginName", defaultValue = "") String loginName,
			HttpServletResponse response) {
		if (loginName != null && !"".equals(loginName)) {
			try {
				SysOperator operator = this.systemService.getOperatorByLoginName(loginName);
				if (operator != null) {
					RenderUtil.renderText("已存在！", response);
				} else {
					RenderUtil.renderText("可用！", response);
				}
			} catch (Exception e) {
				LOGGER.error("Class : Systemcontroller ->Method : checkLoginNameExists-> Exception: {}", e);
			}
		}
	}

	/**
	 * 增加操作员
	 *
	 * @param operator
	 * @param operatorState
	 * @param pwd
	 * @param roles
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/add_operator")
	public void addOperator(SysOperator operator,
			@RequestParam(value = "operatorState", defaultValue = "") String operatorState,
			@RequestParam(value = "pwd1", defaultValue = "") String pwd,
			@RequestParam(value = "roles", defaultValue = "") String roles, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (StringUtils.isNotEmpty(operator.getOperatorLoginName()) && StringUtils.isNotBlank(pwd)) {
				operator.setOperatorPassword(pwd);
				boolean flag = this.systemService.addOperator(operator, roles);

				if (flag) {
					RenderUtil.renderText(Constants.SUCCESS, response);
				} else {
					RenderUtil.renderText(Constants.FAIL, response);
				}
			}
		} catch (Exception e) {
			RenderUtil.renderText(Constants.FAIL, response);
			LOGGER.error("Class: SystemController -> Method: addOperator -> Exception: {}", e);
		}

	}

	/**
	 * 根据id查找管理员信息
	 *
	 * @param id
	 * @param model
	 * @return String
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/operator_info")
	public String findOperatorInfo(@RequestParam(value = "id", defaultValue = "") String id, ModelMap model) {
		if (id != null && !"".equals(id)) {
			try {
				this.systemService.findOperatorInfo(id, model);
			} catch (Exception e) {
				LOGGER.error("Class: SystemController -> Method: findOperatorInfo -> Exception:" + e);
			}
		}
		LOGGER.debug("Class: SystemController ->Method :findOperatorInfo ->  to find operator info by id {}", id);
		return "/system/operator_info";
	}

	/**
	 * 根据id查找需要修改权限信息
	 *
	 * @param id
	 * @param model
	 * @return String
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/to_authority_info")
	public String toFindAuthorityInfo(@RequestParam(value = "id", defaultValue = "") String id, ModelMap model) {
		if (id != null && !"".equals(id)) {
			try {
				this.systemService.toFindAuthorityInfo(id, model);
			} catch (Exception e) {
				LOGGER.error("Class: SystemController ->Method: toFindAuthorityInfo -> Exception: {}", e);
			}
		}
		LOGGER.debug("Class: SystemController ->Method: toFindAuthorityInfo by roleId {}", id);
		return "/system/authority_info";
	}

	/**
	 * 根据id查找需要修改的管理员信息
	 *
	 * @param id
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/operator_to_update")
	public void toUpdateOperator(@RequestParam(value = "id", defaultValue = "") String id,
			HttpServletResponse response) {
		SysOperator operator = null;
		if (id != null && !"".equals(id)) {
			try {
				operator = this.systemService.getOperatorById(Long.parseLong(id));
			} catch (Exception e) {
				LOGGER.error("Class: SystemController -> Method: toUpdateOperator -> Exception: {}", e);
			}
			RenderUtil.renderJson(JsonUtils.toJson(operator), response);
		}
	}

	/**
	 * 更新管理员信息
	 *
	 * @param sysOperator
	 * @param operatorState
	 * @param pwd
	 * @param roles
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/update_operator")
	public void updateOperator(SysOperator sysOperator,
			@RequestParam(value = "operatorState", defaultValue = "") String operatorState,
			@RequestParam(value = "pwd1", defaultValue = "") String pwd,
			@RequestParam(value = "roles", defaultValue = "") String roles, HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = false;
		try {
			if (sysOperator.getOperatorId() != null) {
				roles = this.systemService.findByOperatorId(sysOperator.getOperatorId());
				flag = this.systemService.updateOperator(sysOperator, operatorState, pwd, roles);
			}
		} catch (Exception e) {
			LOGGER.error("Class: SystemController -> Method : updateOperator -> Exception: {}", e);
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	/**
	 * 更新管理员信息
	 *
	 * @param sysOperator
	 * @param operatorState
	 * @param pwd
	 * @param roles
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/update_level.json")
	public void update_level(@RequestParam(value = "levelId", defaultValue = "") Long levelId,
			@RequestParam(value = "operateId", defaultValue = "") Long operateId, HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = false;
		try {
			if (null != operateId) {
				SysOperator o = this.systemService.getOperatorById(operateId);
				if (null != levelId) {
					o.setLevelId(levelId);
					this.systemService.updateOperator(o);
					flag =true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Class: SystemController -> Method : updateOperator -> Exception: {}", e);
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	@RequestMapping(value = "/get_operator_roleIds.json")
	public void getOperatorRoleIds(@RequestParam(value = "id", defaultValue = "") Long id,
			HttpServletResponse response) {
		String result = "";
		try {
			result = this.systemService.findByOperatorId(id);
		} catch (Exception e) {
			LOGGER.error("Class: SystemController -> Method : getOperatorRoleIds -> Exception: {}", e);
		}
		RenderUtil.renderText(result, response);
	}

	/**
	 * 获取管理员信息
	 *
	 * @param operatorName
	 * @param loginName
	 * @param pageNo
	 * @param pageSize
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/find_operator_list")
	public void findOperators(SysOperator sysOperator, HttpServletResponse response) {
		Pageable<SysOperator> pageable = null;
		try {
			pageable = this.systemService.findOperators(sysOperator);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method: findOperators -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(pageable), response);

	}

	/**
	 * 角色操作
	 *
	 * @param model
	 * @return String
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/role_to_add")
	public String toAddRJole(ModelMap model) {
		List<SysAuthority> listAuthority;
		try {
			listAuthority = this.systemService.findAllAuthority();
			model.put("listAuthority", listAuthority);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method: toAddRole -> Exception: {}", e);
		}
		return "/system/add_role";
	}

	/**
	 * @param ids
	 * @param request
	 * @description: 删除角色
	 * @author liwei
	 * @date 2014-12-1 下午4:14:20
	 */
	@RequestMapping(value = "/delete_role")
	public void deleteRole(@RequestParam(value = "ids", defaultValue = "") String ids, HttpServletRequest request,
			HttpServletResponse response) {
		Boolean flag = true;
		try {
			if (StringUtils.isNotEmpty(ids)) {
				flag = this.systemService.deleteRole(ids);
			}
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： deleteRole -> Exception: {}", e);
			flag = false;
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	/**
	 * 增加角色
	 *
	 * @param role
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/add_role")
	public void addRole(SysRole role, HttpServletRequest request, HttpServletResponse response) {
		try {
			role.setCreateDate(new Date());
			boolean flag = this.systemService.addRole(role);
			if (flag) {
				RenderUtil.renderText(Constants.SUCCESS, response);
			} else {
				RenderUtil.renderText(Constants.FAIL, response);
			}
		} catch (Exception e) {
			RenderUtil.renderText(Constants.FAIL, response);
			LOGGER.error("Class ： SystemController -> Method ： addRole -> Exception: {}", e);
		}
	}

	@RequestMapping(value = "/role_to_update")
	public String toUpdateRole(@RequestParam(value = "id", defaultValue = "") String id, ModelMap model) {
		if (id != null && !"".equals(id)) {
			try {
				this.systemService.toUpdateRole(id, model);
			} catch (Exception e) {
				LOGGER.error("Class ： SystemController -> Method ： toUpdateRole -> Exception: {}", e);
			}
		}
		LOGGER.debug("Class ： SystemController -> Method ： toUpdateRole -> to update operator by id {}", id);
		return "/system/role_update";
	}

	/**
	 * 更新角色
	 *
	 * @param role
	 * @param response
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/update_role")
	public void updateRole(SysRole role, HttpServletRequest request, HttpServletResponse response) {

		boolean flag = false;
		;
		if (role.getRoleId() != null) {
			try {
				flag = this.systemService.updateRole(role);
			} catch (Exception e) {
				LOGGER.error("Class ： SystemController -> Method ： updateRole -> Exception: {}", e);
			}
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	/**
	 * 获取角色
	 *
	 * @param roleName
	 * @param pageNo
	 * @param pageSize
	 * @param response
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/find_role_list")
	public void findRoles(@RequestParam(value = "roleName", defaultValue = "") String roleName,
			@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE) int pageNo,
			@RequestParam(value = "rows", defaultValue = Constants.DEFAULT_ROWS) int pageSize,
			HttpServletResponse response) {
		Pageable<SysRole> pageable = null;
		try {
			pageable = this.systemService.findRoles(roleName, (pageNo - 1) * pageSize, pageSize);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： findRoles -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(pageable), response);

	}

	/**
	 * 权限操作
	 */
	@RequestMapping(value = "/authority_to_add")
	public String toAddAuthority() {
		return "/system/add_authority";
	}

	/**
	 * 增加权限
	 *
	 * @param authority
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/add_authority")
	public void addAuthority(SysAuthority authority, HttpServletRequest request, HttpServletResponse response) {
		boolean flag = false;
		SysOperator operator = new SysOperator();
		try {
			flag = this.systemService.addAuthority(authority);
			List<String> nameList = new ArrayList<String>();
			nameList.add(authority.getAuthorityName());
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： addAuthority -> Exception: {}", e);
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	/**
	 * 权限修改时填充名称和描述信息
	 *
	 * @param id
	 * @param response
	 * @author songyc
	 * @since 2015-1-22
	 */
	@RequestMapping(value = "/to_update_authority.json")
	public void fillUpdateAuthority(@RequestParam(value = "id", defaultValue = "") String id,
			HttpServletResponse response) {
		SysAuthority authority;
		try {
			authority = this.systemService.getAuthorityById(Long.parseLong(id));
			String name = authority.getAuthorityName();
			name = name.substring(name.indexOf("】") + 1, name.length());
			authority.setAuthorityName(name);
			RenderUtil.renderJson(JsonUtils.toJson(authority), response);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： fillUpdateAuthority -> Exception: {}", e);
			RenderUtil.renderJson(null, response);

		}

	}

	/**
	 * 更新权限 前期操作
	 *
	 * @param id
	 * @param model
	 * @return String
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/authority_to_update")
	public String toUpdateAuthority(@RequestParam(value = "id", defaultValue = "") String id, ModelMap model) {
		if (id != null && !"".equals(id)) {
			SysAuthority authority;
			try {
				authority = this.systemService.getAuthorityById(Long.parseLong(id));
				model.put("authority", authority);
			} catch (Exception e) {
				LOGGER.error("Class ： SystemController -> Method ： toUpdateAuthority -> Exception: {}", e);
			}

		}
		LOGGER.debug("system controller: to update authority by id {}", id);
		return "/system/update_authority";
	}

	/**
	 * 更新权限
	 *
	 * @param authority
	 * @param response
	 * @param request
	 * @param model
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/update_authority")
	public void updateAuthority(SysAuthority authority, HttpServletResponse response, HttpServletRequest request,
			ModelMap model) {
		boolean flag = false;
		if (authority.getAuthorityId() != null) {
			try {
				flag = this.systemService.updateAuthority(authority);
			} catch (Exception e) {
				LOGGER.error("Class ： SystemController -> Method ： updateAuthority -> Exception: {}", e);
			}
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	/**
	 * 以树状结构显示权限
	 *
	 * @param response
	 * @param model
	 * @author songyc
	 * @since 2015-1-22
	 */
	/*
	 * @RequestMapping(value = "/authority_list") public void
	 * findAuthoritysNew(HttpServletResponse response, ModelMap model) {
	 * List<Tree> authList; try { authList =
	 * this.systemService.getAuthorityTree(); LOGGER.info("权限信息：" +
	 * EnumDisplayUtil.toJson(authList));
	 * RenderUtil.renderJson(EnumDisplayUtil.toJson(authList), response); }
	 * catch (Exception e) { LOGGER.
	 * error("Class ： SystemController -> Method ： findAuthoritysNew -> Exception ：{} "
	 * , e); RenderUtil.renderJson(null, response); } }
	 */
	@RequestMapping(value = "/authority_list.json")
	@ResponseBody
	public List<ComboTree> findAuthoritysNew(HttpServletResponse response, ModelMap model) {
		List<Tree> authList;
		try {
			authList = this.systemService.getAuthorityTree();
			if (!CollectionUtils.isEmpty(authList)) {
				List<ComboTree> resultList = Lists.newArrayList();
				TreeNode treeNode;
				for (Tree authority : authList) {
					treeNode = (TreeNode) authority;
					if (treeNode != null) {
						ComboTree comboTree = new ComboTree();
						comboTree.setId(String.valueOf(treeNode.getId()));
						comboTree.setPid(String.valueOf(treeNode.getPid()));
						comboTree.setText(treeNode.getText());
						resultList.add(comboTree);
					}
				}
				if (!CollectionUtils.isEmpty(resultList)) {
					return EasyUITreeUtil.makeTree(resultList);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： findAuthoritysNew -> Exception ： {}", e);
			RenderUtil.renderJson(null, response);
		}
		return null;
	}

	@RequestMapping(value = "/operator_role_map_to_manage")
	public String toOperatorRoleMap(ModelMap model) {
		List<SysOperator> operator;
		try {
			operator = this.systemService.findAllOperator();
			model.put(OPERATOR, operator);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： toOperatorRoleMap -> Exception: {}", e);
		}
		return "/system/operator_role_map_manage";
	}

	/**
	 * 批量修改操作员角色关联
	 *
	 * @param operatorId
	 * @param response
	 * @author songyc
	 * @since 2015-1-22
	 */
	@RequestMapping(value = "/dynamic_operator_role")
	public void dynamicOperatorRole(@RequestParam(value = "operatorId", defaultValue = "") String operatorId,
			HttpServletResponse response) {
		Object[] o = null;
		try {
			if (StringUtils.isNotEmpty(operatorId)) {
				o = this.systemService.dynamicOperatorRole(Long.parseLong(operatorId));
			}
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： dynamicOperatorRole -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(o), response);
	}

	/**
	 * 新增操作员角色关联
	 *
	 * @param rId
	 * @param operatorId
	 * @param response
	 * @author songyc
	 * @since 2015-1-22
	 */
	@RequestMapping(value = "/operator_role_map_manage")
	public void addOperatorRoleMap(@RequestParam(value = R_ID, defaultValue = "") String rId,
			@RequestParam(value = "operatorId", defaultValue = "") String operatorId, HttpServletResponse response) {
		boolean flag = false;
		try {
			flag = this.systemService.addOperatorRoleMap(rId, operatorId);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： addOperatorRoleMap -> Exception: {}", e);
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	@RequestMapping(value = "/role_authority_map_to_manage")
	public String toRoleAuthorityMap(HttpServletRequest request, ModelMap model) {
		List<SysRole> role;
		try {
			role = this.systemService.findAllRole();
			model.put(ROLE, role);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： toRoleAuthorityMap -> Exception: {}", e);
		}
		return "/system/role_authority_map_manage";
	}

	/**
	 * 批量修改角色权限关联
	 *
	 * @param roleId
	 * @param response
	 * @param model
	 * @author songyc
	 * @since 2015-1-22
	 */
	@RequestMapping(value = "/dynamicRoleAuthority")
	public void dynamicRoleAuthority(@RequestParam(value = "roleId", defaultValue = "") String roleId,
			HttpServletResponse response, ModelMap model) {
		Object[] o = null;
		try {
			if (StringUtils.isNotEmpty(roleId)) {
				o = this.systemService.dynamicRoleAuthority(Long.parseLong(roleId));
			}
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： dynamicRoleAuthority -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(o), response);
	}

	/**
	 * 增加角色权限
	 *
	 * @param rId
	 * @param roleId
	 * @param response
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/role_authority_map_manage")
	public void addRoleAuthorityMap(@RequestParam(value = R_ID, defaultValue = "") String rId,
			@RequestParam(value = "roleId", defaultValue = "") String roleId, HttpServletResponse response) {
		boolean flag = false;
		try {
			flag = this.systemService.addRoleAuthorityMap(rId, roleId);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： addRoleAuthorityMap -> Exception: {}", e);
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	/**
	 * 删除权限
	 *
	 * @param id
	 * @param request
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/delete_authority")
	public void deleteAuthority(@RequestParam(value = "id", defaultValue = "") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = false;
		try {
			flag = this.systemService.deleteAuthority(id);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： deleteAuthority -> Exception: {}", e);
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);

	}

	/**
	 * 查找角色所有权限id
	 *
	 * @param id
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/selected_authority_for_role.json")
	public void getRoleAuthority(@RequestParam(value = "id", defaultValue = "") Long id, HttpServletResponse response) {
		StringBuffer strB = new StringBuffer();
		try {
			List<SysRoleAuthorityMap> roleAuthMapList = this.systemService.findAuthorityForDisplayTree(id);
			if (!CollectionUtils.isEmpty(roleAuthMapList)) {
				int listSize = roleAuthMapList.size();
				for (int i = 0; i < listSize; i++) {
					SysRoleAuthorityMap roleAuthMap = roleAuthMapList.get(i);
					if (i == listSize - 1) {
						strB.append(roleAuthMap.getSysAuthority().getAuthorityId());
					} else {
						strB.append(roleAuthMap.getSysAuthority().getAuthorityId()).append(",");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Class: SystemController-> Method: getRoleAuthority -> Exception: {}", e);
		}
		RenderUtil.renderText(strB.toString(), response);

	}

	/**
	 * 角色权限分配
	 *
	 * @param authIds
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/assign_role_authority")
	public void assignAuthorityForRole(@RequestParam(value = "authIds", defaultValue = "") String authIds,
			@RequestParam(value = "roleId", defaultValue = "") Long roleId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			this.systemService.assignAuthorityForRole(roleId, StringUtil.splitToLong(authIds));
			RenderUtil.renderText(Constants.SUCCESS, response);
		} catch (Exception e) {
			RenderUtil.renderText(Constants.FAIL, response);
			LOGGER.error("Class: SystemController-> Method: assignAuthorityForRole -> Exception: {}", e);
		}
	}

	/**
	 * 通过父权限id查询所以的子权限
	 *
	 * @param id
	 * @param pageNo
	 * @param pageSize
	 * @param response
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/find_authority_pid_list")
	public String findAuthorityByPId(@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE) int pageNo,
			@RequestParam(value = "rows", defaultValue = Constants.DEFAULT_ROWS) int pageSize,
			HttpServletResponse response) {
		Pageable<SysAuthority> authority = null;
		if (StringUtils.isNotBlank(id)) {
			try {
				authority = this.systemService.findAuthorityByPId(Long.parseLong(id), (pageNo - 1) * pageSize,
						pageSize);
			} catch (Exception e) {
				LOGGER.error("Class: SystemController -> Method: findOperatorByOrganization -> Exception:", e);
				return RenderUtil.renderJson(JsonUtils.toJson(false), response);
			}
		}
		return RenderUtil.renderJson(JsonUtils.toJson(authority), response);
	}

	@RequestMapping(value = "/find_dic_list")
	public void findDics(@RequestParam(value = "type", defaultValue = "") String type,
			@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE) int pageNo,
			@RequestParam(value = "rows", defaultValue = Constants.DEFAULT_ROWS) int pageSize,
			HttpServletResponse response) {
		Pageable<SysDic> pageable = null;
		try {
			pageable = this.systemService.findDics(type, (pageNo - 1) * pageSize, pageSize);
		} catch (Exception e) {
			LOGGER.error("Class ： SystemController -> Method ： findDics -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(pageable), response);

	}

	/**
	 * 增加字典
	 *
	 * @param model
	 * @return String
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/dic_to_add")
	public String toAddDic(ModelMap model) {
		try {

		} catch (Exception e) {
			LOGGER.error("Class: SystemController -> Method: toAddDic -> Exception: {}", e);
		}
		return "/system/add_dic";
	}

	/**
	 * 删除字典
	 *
	 * @param ids
	 * @param request
	 * @author liwei
	 * @since 2014-12-1
	 */
	@RequestMapping(value = "/delete_dic")
	public void deleteDic(@RequestParam(value = "ids", defaultValue = "") String ids, HttpServletRequest request,
			HttpServletResponse response) {
		Boolean flag = true;
		try {
			if (StringUtils.isNotEmpty(ids)) {
				flag = this.systemService.deleteDic(ids);
			}
		} catch (Exception e) {
			LOGGER.error("Class: SystemController -> Method: deleteDic -> Exception: {}", e);
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	/**
	 * 更新字典
	 *
	 * @param role
	 * @param response
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/update_dic")
	public void updateDic(SysDic dic, HttpServletRequest request, HttpServletResponse response) {

		boolean flag = false;
		if (dic.getId() != null) {
			try {
				flag = this.systemService.updateDic(dic);
			} catch (Exception e) {
				LOGGER.error("Class ： SystemController -> Method ： updateDic -> Exception: {}", e);
			}
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	/**
	 * 增加字典
	 *
	 * @param dic
	 * @param response
	 * @author songyc
	 * @since 2015-1-15
	 */
	@RequestMapping(value = "/add_dic")
	public void addDic(SysDic dic, HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean flag = this.systemService.addDic(dic);
			if (flag) {
				RenderUtil.renderText(Constants.SUCCESS, response);
			} else {
				RenderUtil.renderText(Constants.FAIL, response);
			}
		} catch (Exception e) {
			RenderUtil.renderText(Constants.FAIL, response);
			LOGGER.error("Class ： SystemController -> Method ： addDic -> Exception: {}", e);
		}
	}

	/**
	 * 列出字典列表
	 *
	 * @param response
	 * @author songyc
	 * @since 2015-1-16
	 */
	@RequestMapping(value = "/dic_list.json")
	public void getDicByType(HttpServletResponse response,
			@RequestParam(value = "type", defaultValue = "") String type) {
		List<SysDic> dicList;
		List<TextValue> tv = null;
		try {
			dicList = this.systemService.findDics(type);
			tv = new ArrayList<TextValue>();
			for (SysDic dic : dicList) {
				tv.add(new TextValue(dic.getDicKey(), dic.getDicValue()));
			}
		} catch (Exception e) {
			LOGGER.error("Class: SystemController -> Method: getDicByType -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(tv), response);
	}
}
