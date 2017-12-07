package com.qinzhi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qinzhi.bean.Constants;
import com.qinzhi.domain.Level;
import com.qinzhi.domain.SysOperator;
import com.qinzhi.service.ILevelService;
import com.qinzhi.service.ISystemService;
import com.qinzhi.utils.JsonUtils;
import com.qinzhi.utils.RenderUtil;

@Controller
public class LevelController {

	private static final Logger logger = LoggerFactory.getLogger(LevelController.class);

	@Autowired
	private ISystemService systemService;

	@Autowired
	private ILevelService service;

	@RequestMapping(value = "/my_level")
	public String toLevel(@RequestParam(value = "operatorId", defaultValue = "") Long operatorId, Model model) {
		if (null != operatorId) {
			model.addAttribute("operatorId", operatorId);
			try {
				SysOperator o = this.systemService.getOperatorById(operatorId);
				if (null != o) {
					model.addAttribute("operator", o);
				}
			} catch (Exception e) {
				logger.error("catch exception when get Operator :e{}", e);
			}
		}
		return "/system/level";
	}

	@RequestMapping(value = "/levelinfo")
	public String levelinfo(Model model) {
		return "/system/level_info";
	}

	@RequestMapping("/update.json")
	public void Gupdateoods(HttpServletResponse response, Level level) {
		try {
			this.service.updateLevel(level);
			RenderUtil.renderText(Constants.SUCCESS, response);
		} catch (Exception e) {
			logger.error("  Exception: {}", e);
			RenderUtil.renderText(Constants.FAIL, response);
		}
	}

	@RequestMapping(value = "/get_level_info.json")
	@ResponseBody
	public Level getLevelInfo(@RequestParam(value = "id", defaultValue = "") Long id) {
		if (id != null) {
			try {
				return this.service.getLevelById(id);
			} catch (Exception e) {
				logger.error("Class: SystemController -> Method: getOperatorInfo -> Exception:{}", e);
			}
		}
		logger.debug("Class: SystemController ->Method :getOperatorInfo ->  to find operator info by id {}", id);
		return null;
	}

	@RequestMapping("/findLevelList.json")
	public void findLevelList(HttpServletResponse response, Model model) {
		List<Level> levelList = null;
		try {
			levelList = this.service.findLevelList(null);
		} catch (Exception e) {
			logger.error(" Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(levelList), response);
	}

}
