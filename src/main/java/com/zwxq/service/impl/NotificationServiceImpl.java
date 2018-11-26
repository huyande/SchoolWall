package com.zwxq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zwxq.bean.Notification;
import com.zwxq.dao.NotificationMapper;
import com.zwxq.service.NotificationService;
@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationMapper notificationMapper;
	
	/**
	 * 添加信息
	 */
	@Override
	public int addNotificationMessage(Notification notification) {
		return notificationMapper.insert(notification);
	}

	/**
	 * 查询信息
	 */
	@Override
	public List<Notification> findnotificationDataList(Integer page, Integer pageSize, String openid) {
		// TODO Auto-generated method stub
		return notificationMapper.findnotificationDataList((page-1)*pageSize,pageSize,openid);
	}

}
