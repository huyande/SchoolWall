package com.zwxq.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zwxq.bean.Notification;
import com.zwxq.bean.Wxuserinfo;
import com.zwxq.bean.ana.ScMessage;
import com.zwxq.service.NotificationService;
import com.zwxq.service.WxUserInfoService;
import com.zwxq.utils.JacksonUtil;

/**
 * 消息处理
 * @author Administrator
 *
 */
@RestController
@RequestMapping("mess")
public class MessageController {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	@RequestMapping("message")
	public String message(Integer page,Integer pageSize,String openid){
		
		List<Notification> notificationList= new ArrayList<>();
		List<ScMessage> scmessageList = new ArrayList<>();
		
		notificationList =notificationService.findnotificationDataList(page,pageSize,openid);
		for (Notification notification : notificationList) {
			Wxuserinfo wxuserinfo = wxUserInfoService.findByOpenId(notification.getFromopenid());
			ScMessage message = new ScMessage();
			message.setNickName(wxuserinfo.getNickname());
			message.setAvatarUrl(wxuserinfo.getAvatarurl());
			message.setCreateTime(notification.getCreatetime());
			message.setType(notification.getType());
			scmessageList.add(message);
		}
		return JacksonUtil.toJSon(scmessageList);
	}
	
}
