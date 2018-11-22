package com.zwxq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zwxq.bean.Liked;
import com.zwxq.dao.LikedMapper;
import com.zwxq.service.LikedService;
@Service
public class LikedServiceImpl implements LikedService{

	@Autowired
	private LikedMapper likedMapper;
	
	@Override
	public boolean isLiked(String openid, String confid) {
		Liked liked=likedMapper.findOpenIdAndConfid(openid,confid);
		return (liked==null)?false:true;
	}

	@Override
	public Integer likedCount(String id) {
		return likedMapper.likedCount(id);
	}

	@Override
	public int updataLikeState(String confid, String openid,int state) {
		return likedMapper.updateByConfidAndOpenid(confid,openid,state);
	}

	@Override
	public Liked ishasLikedData(String confid, String openid) {
		return likedMapper.ishasLikedData(confid,openid);
	}

	@Override
	public int addLiked(Liked like) {
		return likedMapper.insert(like);
	}

}
