package com.qinzhi.controller;

import com.qinzhi.bean.Constants;
import com.qinzhi.domain.SysOperator;
import com.qinzhi.service.ISystemService;
import com.qinzhi.utils.RenderUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 主控制器
 */
@Controller
@RequestMapping(value = "/non")
public class NonLoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonLoginController.class);

	// 用户类型: 0=商户
	private static final Integer OPERATOR_STATE_NORMAL = 0;

	// 用户角色: 2=商户
	private static final String OPERATOR_role_NORMAL = "2";

	@Autowired
	private ISystemService systemService;

	@RequestMapping(value = "/signup.html")
	public String toSignup() {
		return "signup";
	}

	@RequestMapping(value = "/signup")
	public void signup(SysOperator operator,
			@RequestParam(value = "operatorState", defaultValue = "") String operatorState,
			@RequestParam(value = "pwd1", defaultValue = "") String pwd,
			@RequestParam(value = "roles", defaultValue = "") String roles, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (StringUtils.isNotEmpty(operator.getOperatorLoginName()) && StringUtils.isNotBlank(pwd)) {
				operator.setOperatorPassword(pwd);
				// 设置默认会员等级[VIP会员]
				operator.setOperatorLevel("VIP会员");
				// 设置默认的用户类型 0=商户
				operator.setOperatorState(OPERATOR_STATE_NORMAL);
				roles = OPERATOR_role_NORMAL;
				boolean flag = this.systemService.addOperator(operator, roles);
				if (flag) {
					RenderUtil.renderText(Constants.SUCCESS, response);
				} else {
					RenderUtil.renderText(Constants.FAIL, response);
				}
			}
		} catch (Exception e) {
			RenderUtil.renderText(Constants.FAIL, response);
			LOGGER.error("catch exception when signup-> Exception: {}", e);
		}
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
	public void checkLoginNameExists(@RequestParam(value = "operatorLoginName", defaultValue = "") String loginName,
			HttpServletResponse response) {
		if (loginName != null && !"".equals(loginName)) {
			try {
				SysOperator operator = this.systemService.getOperatorByLoginName(loginName);
				if (operator != null) {
					RenderUtil.renderJson("{\"valid\":false}", response);
				} else {
					RenderUtil.renderJson("{\"valid\":true}", response);
				}
			} catch (Exception e) {
				LOGGER.error("Class : Systemcontroller ->Method : checkLoginNameExists-> Exception: {}", e);
			}
		}
	}

}
