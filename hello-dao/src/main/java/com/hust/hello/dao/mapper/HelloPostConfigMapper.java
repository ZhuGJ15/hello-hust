package com.hust.hello.dao.mapper;

import com.hust.hello.common.model.entity.HelloPostConfig;
import org.apache.ibatis.annotations.Param;

public interface HelloPostConfigMapper {
    int deleteWithVersionByPrimaryKey(@Param("version") Integer version, @Param("key") Long id);

    int deleteByPrimaryKey(Long id);

    int insert(HelloPostConfig record);

    int insertSelective(HelloPostConfig record);

    HelloPostConfig selectByPrimaryKey(Long id);

    int updateWithVersionByPrimaryKey(@Param("version") Integer version, @Param("record") HelloPostConfig record);

    int updateWithVersionByPrimaryKeySelective(@Param("version") Integer version, @Param("record") HelloPostConfig record);

    int updateByPrimaryKeySelective(HelloPostConfig record);

    int updateByPrimaryKey(HelloPostConfig record);
}