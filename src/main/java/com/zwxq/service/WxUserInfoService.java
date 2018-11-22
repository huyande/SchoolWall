package com.zwxq.service;

import com.zwxq.bean.Wxuserinfo;

public interface WxUserInfoService {

	Wxuserinfo findByOpenId(String openid);

	int addWxUserInfo(Wxuserinfo wxuserinfo);

}
