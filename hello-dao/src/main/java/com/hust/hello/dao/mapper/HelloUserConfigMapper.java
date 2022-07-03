package com.hust.hello.dao.mapper;

import com.hust.hello.common.model.entity.HelloUserConfig;
import org.apache.ibatis.annotations.Param;

public interface HelloUserConfigMapper {
    int deleteWithVersionByPrimaryKey(@Param("version") Integer version, @Param("key") Long id);

    int deleteByPrimaryKey(Long id);

    int insert(HelloUserConfig record);

    int insertSelective(HelloUserConfig record);

    HelloUserConfig selectByPrimaryKey(Long id);

    int updateWithVersionByPrimaryKey(@Param("version") Integer version, @Param("record") HelloUserConfig record);

    int updateWithVersionByPrimaryKeySelective(@Param("version") Integer version, @Param("record") HelloUserConfig record);

    int updateByPrimaryKeySelective(HelloUserConfig record);

    int updateByPrimaryKey(HelloUserConfig record);
}