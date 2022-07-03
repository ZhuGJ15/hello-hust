package com.hust.hello.dao.mapper;

import com.hust.hello.common.model.entity.HelloBarConfig;
import org.apache.ibatis.annotations.Param;

public interface HelloBarConfigMapper {
    int deleteWithVersionByPrimaryKey(@Param("version") Integer version, @Param("key") Long id);

    int deleteByPrimaryKey(Long id);

    int insert(HelloBarConfig record);

    int insertSelective(HelloBarConfig record);

    HelloBarConfig selectByPrimaryKey(Long id);

    int updateWithVersionByPrimaryKey(@Param("version") Integer version, @Param("record") HelloBarConfig record);

    int updateWithVersionByPrimaryKeySelective(@Param("version") Integer version, @Param("record") HelloBarConfig record);

    int updateByPrimaryKeySelective(HelloBarConfig record);

    int updateByPrimaryKey(HelloBarConfig record);
}