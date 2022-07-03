package com.hust.hello.dao.mapper;

import com.hust.hello.common.model.entity.HelloCommentConfig;
import org.apache.ibatis.annotations.Param;

public interface HelloCommentConfigMapper {
    int deleteWithVersionByPrimaryKey(@Param("version") Integer version, @Param("key") Long id);

    int deleteByPrimaryKey(Long id);

    int insert(HelloCommentConfig record);

    int insertSelective(HelloCommentConfig record);

    HelloCommentConfig selectByPrimaryKey(Long id);

    int updateWithVersionByPrimaryKey(@Param("version") Integer version, @Param("record") HelloCommentConfig record);

    int updateWithVersionByPrimaryKeySelective(@Param("version") Integer version, @Param("record") HelloCommentConfig record);

    int updateByPrimaryKeySelective(HelloCommentConfig record);

    int updateByPrimaryKey(HelloCommentConfig record);
}