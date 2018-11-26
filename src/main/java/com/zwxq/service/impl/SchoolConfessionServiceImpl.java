package com.zwxq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zwxq.bean.Confession;
import com.zwxq.bean.SchoolImage;
import com.zwxq.bean.ana.ScConfessionJson;
import com.zwxq.dao.ConfessionMapper;
import com.zwxq.service.SchoolConfessionService;

@Service
public class SchoolConfessionServiceImpl implements SchoolConfessionService{

	@Autowired
	private ConfessionMapper confessionMapper;
	
	@Override
	public int postAdd(Confession confession) {
		return confessionMapper.insert(confession);
	}

	@Override
	public List<Confession> findConfession(int pageStart, int pageSize) {
		
		return confessionMapper.findAllConfession((pageStart-1)*pageSize,pageSize);
	}

	@Override
	public List<Confession> findschoolConfessionOfMyLove(Integer page, Integer pageSize, String openid) {
		// TODO Auto-generated method stub
		return confessionMapper.findschoolConfessionOfMyLove((page-1)*pageSize,pageSize,openid);
	}

	@Override
	public Confession findConfessionByConfid(String confid) {
		return confessionMapper.selectByPrimaryKey(confid);
	}

}
