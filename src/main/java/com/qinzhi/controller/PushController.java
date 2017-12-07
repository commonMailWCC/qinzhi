package com.qinzhi.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qinzhi.bean.Constants;
import com.qinzhi.domain.Push;
import com.qinzhi.domain.SysDic;
import com.qinzhi.service.IPushService;
import com.qinzhi.service.ISystemService;
import com.qinzhi.utils.JsonUtils;
import com.qinzhi.utils.Pageable;
import com.qinzhi.utils.RenderUtil;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

/**
 * 主控制器
 */
@Controller
public class PushController {

	private static final Logger logger = LoggerFactory.getLogger(PushController.class);

	private static final String TYPE_PUSH = "push";

	private static final String TYPE_APPKEY = "AppKey";

	private static final String TYPE_SECRET = "Secret";

	@Autowired
	private ISystemService systemService;

	@Autowired
	private IPushService service;

	/**
	 * 编辑页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/to_push_list")
	public String index(Model model) {
		return "system/push";
	}

	@RequestMapping("/findPushList.json")
	public void findPushList(Push push, HttpServletResponse response, Model model) {
		Pageable<Push> pageable = null;
		try {
			pageable = this.service.findPushList(push);
		} catch (Exception e) {
			logger.error("Class ： PushController -> Method: findPushList -> Exception: {}", e);
		}
		RenderUtil.renderJson(JsonUtils.toJson(pageable), response);
	}

	@RequestMapping("/addPush.json")
	public void addPush(HttpServletResponse response, Push push) {
		try {
			push.setCreateTime(new Date());
			this.service.savePush(push);
			String MASTER_SECRET = "";
			String APP_KEY = "";
			// appKey
			List<SysDic> dicList = systemService.findDics(TYPE_PUSH);
			if (!dicList.isEmpty()) {
				for (SysDic dic : dicList) {
					if (TYPE_APPKEY.equals(dic.getDicKey())) {
						APP_KEY = dic.getDicValue();
					} else if (TYPE_SECRET.equals(dic.getDicKey())) {
						MASTER_SECRET = dic.getDicValue();
					}
				}
			}
			if (!push(MASTER_SECRET, APP_KEY, push.getMessage())) {
				RenderUtil.renderText(Constants.FAIL, response);
			} else {
				RenderUtil.renderText(Constants.SUCCESS, response);
			}
		} catch (Exception e) {
			logger.error("Class ： PushController -> Method: addPush -> Exception: {}", e);
			RenderUtil.renderText(Constants.FAIL, response);
		}
	}

	/**
	 * 组织发送信息
	 * 
	 * @param alert
	 * @return
	 */
	public static PushPayload buildPushObject_all_all_alert(String alert) {

		return PushPayload.alertAll(alert);

	}

	/**
	 * 推送信息
	 * 
	 * @param MASTER_SECRET
	 * @param APP_KEY
	 * @param alert
	 * @return
	 */
	public boolean push(String MASTER_SECRET, String APP_KEY, String alert) {
		boolean isSuccess = false;
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
		PushPayload payload = buildPushObject_all_all_alert(alert);

		try {
			PushResult result = jpushClient.sendPush(payload);
			isSuccess = true;
			logger.info("Got result - " + result);
		} catch (APIConnectionException e) {
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		}
		return isSuccess;
	}
}
