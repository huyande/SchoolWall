package com.zwxq.service;

import java.util.List;

import com.zwxq.bean.Confession;
import com.zwxq.bean.SchoolImage;

public interface SchoolConfessionService {

	int postAdd(Confession confession);

	List<Confession> findConfession(int page, int size);

	List<Confession> findschoolConfessionOfMyLove(Integer page, Integer pageSize, String openid);
	/**
	 * 根据文章id 查询此信息
	 * @param confid
	 * @return
	 */
	Confession findConfessionByConfid(String confid);

	
}
