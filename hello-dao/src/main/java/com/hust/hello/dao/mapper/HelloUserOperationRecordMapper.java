package com.hust.hello.dao.mapper;

import com.hust.hello.common.model.entity.HelloUserOperationRecord;
import org.apache.ibatis.annotations.Param;

public interface HelloUserOperationRecordMapper {
    int deleteWithVersionByPrimaryKey(@Param("version") Integer version, @Param("key") Long id);

    int deleteByPrimaryKey(Long id);

    int insert(HelloUserOperationRecord record);

    int insertSelective(HelloUserOperationRecord record);

    HelloUserOperationRecord selectByPrimaryKey(Long id);

    int updateWithVersionByPrimaryKey(@Param("version") Integer version, @Param("record") HelloUserOperationRecord record);

    int updateWithVersionByPrimaryKeySelective(@Param("version") Integer version, @Param("record") HelloUserOperationRecord record);

    int updateByPrimaryKeySelective(HelloUserOperationRecord record);

    int updateByPrimaryKey(HelloUserOperationRecord record);
}