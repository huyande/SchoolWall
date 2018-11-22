package com.zwxq.service;

import java.util.List;

import com.zwxq.bean.Confession;

public interface SchoolConfessionService {

	int postAdd(Confession confession);

	List<Confession> findConfession(int page, int size);

	
}
