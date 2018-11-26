package com.zwxq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zwxq.bean.Confession;
import com.zwxq.bean.SchoolImage;
import com.zwxq.bean.ana.ScConfessionJson;

public interface ConfessionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Confession record);

    int insertSelective(Confession record);

    Confession selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Confession record);

    int updateByPrimaryKey(Confession record);

	List<Confession> findAllConfession(@Param(value="pageStart") int pageStart,@Param(value="pageSize")int pageSize);

	List<Confession> findschoolConfessionOfMyLove(@Param(value="pageStart") int pageStart,@Param(value="pageSize")int pageSize, @Param(value="openid")String openid);
}