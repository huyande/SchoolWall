package com.zwxq.dao;

import org.apache.ibatis.annotations.Param;

import com.zwxq.bean.Liked;

public interface LikedMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Liked record);

    int insertSelective(Liked record);

    Liked selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Liked record);

    int updateByPrimaryKey(Liked record);

	Liked findOpenIdAndConfid(@Param(value="openid") String openid, @Param(value="confid") String confid);

	Integer likedCount(@Param(value="confid") String confid);

	int updateByConfidAndOpenid(@Param(value="confid")String confid, @Param(value="openid")String openid,@Param(value="state")int state);

	Liked ishasLikedData(@Param(value="confid")String confid, @Param(value="openid")String openid);
}