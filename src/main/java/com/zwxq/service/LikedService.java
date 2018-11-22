package com.zwxq.service;

import com.zwxq.bean.Liked;

public interface LikedService {

	boolean isLiked(String openid, String id);

	Integer likedCount(String id);

	int updataLikeState(String confid, String openid,int state);

	Liked ishasLikedData(String confid, String openid);

	int addLiked(Liked like);

}
