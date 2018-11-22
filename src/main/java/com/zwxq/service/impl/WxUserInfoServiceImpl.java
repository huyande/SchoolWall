package com.zwxq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zwxq.bean.Wxuserinfo;
import com.zwxq.dao.WxuserinfoMapper;
import com.zwxq.service.WxUserInfoService;
@Service
public class WxUserInfoServiceImpl implements WxUserInfoService {

	@Autowired
	private WxuserinfoMapper wxuserinfoMapper;

	@Override
	public Wxuserinfo findByOpenId(String openid) {
		Wxuserinfo wxuserinfo =wxuserinfoMapper.findByOpenId(openid);
		return wxuserinfo;
	}

	@Override
	public int addWxUserInfo(Wxuserinfo wxuserinfo) {
		return wxuserinfoMapper.insert(wxuserinfo);
	}
	
}
