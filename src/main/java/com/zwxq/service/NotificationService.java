package com.zwxq.service;

import java.util.List;

import com.zwxq.bean.Notification;

public interface NotificationService {

	int addNotificationMessage(Notification notification);

	List<Notification> findnotificationDataList(Integer page, Integer pageSize, String openid);

}
