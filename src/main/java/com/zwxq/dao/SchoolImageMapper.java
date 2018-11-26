package com.zwxq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zwxq.bean.Confession;
import com.zwxq.bean.SchoolImage;
import com.zwxq.bean.ana.ScConfessionJson;

public interface SchoolImageMapper {

	/**
	 * 添加
	 * @param record
	 * @return
	 */
    int insert(SchoolImage record);

    /**
     * 查找所有
     * @param pageStart
     * @param pageSize
     * @return
     */
	List<SchoolImage> findAllschoolImage(@Param(value="pageStart") int pageStart,@Param(value="pageSize")int pageSize);

	/**
	 * 查找我的所有
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<SchoolImage> findSchoolImageOfMyImage(@Param(value="pageStart") int pageStart,@Param(value="pageSize")int pageSize,@Param(value="openid")String openid);

	SchoolImage selectByPrimaryKey(String id);

}