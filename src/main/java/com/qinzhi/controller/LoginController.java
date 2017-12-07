package com.qinzhi.controller;

import com.qinzhi.domain.SysOperator;
import com.qinzhi.security.ShiroPrincipal;
import com.qinzhi.security.ShiroUtils;
import com.qinzhi.utils.Digests;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 主控制器
 */
@Controller
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 不登陆直接进首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/")
	public String index(Model model) {
		ShiroPrincipal shiroPrincipal = ShiroUtils.getPrincipal();
		if (shiroPrincipal != null) {
			model.addAttribute("user", shiroPrincipal.getUser().getOperatorName());
			if (shiroPrincipal.getUser().getOperatorState() == 1) {
				model.addAttribute("userType", "网站");
			} else {
				model.addAttribute("userType", "商户");
				model.addAttribute("operatorId", shiroPrincipal.getUser().getOperatorId());
			}
		}
		return "system/index";
	}

	@RequestMapping(value = "/to_welcome.html")
	public String toWelcome() {
		return "welcome";
	}

	@RequestMapping(value = "/login")
	public String login(SysOperator sysOperator, Model model, HttpServletRequest request) {
		if (StringUtils.isBlank(sysOperator.getOperatorLoginName())
				|| StringUtils.isBlank(sysOperator.getOperatorPassword())) {
			return "login";
		}
		Subject subject = SecurityUtils.getSubject();
		String password;
		try {
			password = Digests.getMd5Bit32(sysOperator.getOperatorPassword());
		} catch (Exception e) {
			LOGGER.error("login error: {}", e);
			model.addAttribute("username", sysOperator.getOperatorLoginName());
			model.addAttribute("error", "登录失败");
			return "login";
		}
		UsernamePasswordToken token = new UsernamePasswordToken(sysOperator.getOperatorLoginName(),
				password);
		try {
			subject.login(token);
			return "redirect:/";
		} catch (UnknownAccountException ue) {
			LOGGER.error("login error: {}", ue);
			token.clear();
			model.addAttribute("error", "登录失败，您输入的账号不存在");
			return "login";
		} catch (IncorrectCredentialsException ie) {
			LOGGER.error("login error: {}", ie);
			token.clear();
			model.addAttribute("username", sysOperator.getOperatorLoginName());
			model.addAttribute("error", "登录失败，密码不匹配");
			return "login";
		} catch (Exception re) {
			LOGGER.error("login error: {}", re);
			token.clear();
			model.addAttribute("username", sysOperator.getOperatorLoginName());
			model.addAttribute("error", "登录失败");
			return "login";
		}
	}

	/**
	 * 登录成功后系统首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jumpindex", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request,
			@RequestParam(value = "appId", defaultValue = "") String appId) {
		ShiroPrincipal shiroPrincipal = ShiroUtils.getPrincipal();
		if (shiroPrincipal != null) {
			model.addAttribute("user", shiroPrincipal.getUser());
		} else {
			return "login";

		}
		model.addAttribute("appId", appId);
		return "system/index";
	}
}
