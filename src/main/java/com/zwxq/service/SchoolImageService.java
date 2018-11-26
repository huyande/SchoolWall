package com.zwxq.service;

import java.util.List;

import com.zwxq.bean.SchoolImage;

public interface SchoolImageService {

	int postAdd(SchoolImage schoolImage);

	List<SchoolImage> findschoolImage(int page, int size);

	List<SchoolImage> findSchoolImageOfMyImage(Integer page, Integer pageSize,String openid);

	SchoolImage findSchoolByImageid(String confid);

	
}
