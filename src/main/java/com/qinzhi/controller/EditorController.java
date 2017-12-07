package com.qinzhi.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baidu.ueditor.ActionEnter;
import com.qinzhi.domain.SysDic;
import com.qinzhi.service.ISystemService;
import com.qinzhi.utils.ControllerUtil;
import com.qinzhi.utils.JsonUtils;
import com.qinzhi.utils.RenderUtil;

/**
 * 主控制器
 */
@Controller
public class EditorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditorController.class);
	private static final String TYPE_NAVIGATION = "navigation";
	private static final String UPLOAD_DIR = "images/upload";
	private static final String FILE_SER = "/";

	@Autowired
	private ISystemService systemService;

	/**
	 * 编辑页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/to_edit.html")
	public String index(Model model, @RequestParam(value = "key", defaultValue = "1") String key) {
		SysDic dic = systemService.findDicByTypeAndKey(key, TYPE_NAVIGATION);
		if (null != dic) {
			String value = JsonUtils.toJson(dic.getDicValue());
			model.addAttribute("htmlValue", value.substring(1, value.length() - 1));
		}
		model.addAttribute("key", key);
		return "editor";
	}

	/**
	 * 编辑页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/to_save")
	public void save(Model model, @RequestParam(value = "key", defaultValue = "1") String key,
			@RequestParam(value = "data") String data, HttpServletResponse response) {
		boolean flag = true;
		try {
			SysDic dic = systemService.findDicByTypeAndKey(key, TYPE_NAVIGATION);
			if (null != dic) {
				dic.setDicValue(StringEscapeUtils.unescapeHtml4(data));
				systemService.updateDic(dic);
			}
		} catch (Exception e) {
			LOGGER.error("Class : EditorController ->Method : save-> Exception: {}", e);
			flag = false;
		}
		RenderUtil.renderText(ControllerUtil.returnString(flag), response);
	}

	/**
	 * 预览页面-普通用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/to_show.html")
	public String show(Model model, @RequestParam(value = "key", defaultValue = "1") String key) {
		SysDic dic = systemService.findDicByTypeAndKey(key, TYPE_NAVIGATION);
		if (null != dic) {
			String value = JsonUtils.toJson(dic.getDicValue());
			model.addAttribute("htmlValue", value.substring(1, value.length() - 1));
		}
		model.addAttribute("key", key);
		return "show";
	}

	@RequestMapping(value = "/imageUpload.json")
	public void upload(HttpServletRequest request, HttpServletResponse response, String action)
			throws IOException, InterruptedException {
		String a = "";
		response.setContentType("application/json");
		String rootPath = request.getSession().getServletContext().getRealPath("/");

		try {
			String exec = new ActionEnter(request, rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
