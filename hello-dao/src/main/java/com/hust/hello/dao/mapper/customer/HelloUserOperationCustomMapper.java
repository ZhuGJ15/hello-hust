package com.hust.hello.dao.mapper.customer;

import com.hust.hello.common.model.entity.HelloUserOperationRecord;
import com.hust.hello.dao.mapper.HelloUserOperationRecordMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/11 10:59
 * @description:
 */
public interface HelloUserOperationCustomMapper extends HelloUserOperationRecordMapper {

    @Select({
            "select * from hello_user_operation_record",
            "where record_id = \"${recordId}\""
    })
    HelloUserOperationRecord selectRecordByRecordId(@Param("recordId") String recordId);


    @Select({
            "select * from hello_user_operation_record",
            "where operator_id = \"${operatorId}\"",
            "and object_id = \"${objectId}\"",
            "and operation_type = \"${operationType}\"",
            "and is_cancel = 0"
    })
    HelloUserOperationRecord selectRecordByUserIdAndOperationType(@Param("operatorId") String operatorId,
                                                                  @Param("objectId") String objectId,
                                                                  @Param("operationType") Integer operationType);

    @Select({
            "select * from hello_user_operation_record",
            "where operator_id=\"${operatorId}\"",
            "and object_id=\"${objectId}\"",
            "and operation_type = 7"
    })
    HelloUserOperationRecord selectReportRecord(@Param("operatorId") String operatorId,
                                                @Param("objectId") String objectId);

    @Select({
            "<script>",
            "select * from hello_user_operation_record",
            "where operator_id=\"${userId}\"",
            "and operation_type = 3",
            "and is_cancel = 0",
            "order by operate_time desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<HelloUserOperationRecord> selectCollectPostRecord(@Param("userId") String userId,
                                                           @Param("currentPage") Integer currentPage,
                                                           @Param("pageSize") Integer pageSize);

    @Select({
            "<script>",
            "select * from hello_user_operation_record",
            "where operator_id=\"${userId}\"",
            "and operation_type = 1",
            "and is_cancel = 0",
            "order by operate_time desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<HelloUserOperationRecord> selectFollowUserRecord(@Param("userId") String userId,
                                                          @Param("currentPage") Integer currentPage,
                                                          @Param("pageSize") Integer pageSize);

    @Select({
            "<script>",
            "select * from hello_user_operation_record",
            "where object_id = \"${userId}\"",
            "and operation_type = 1",
            "and is_cancel = 0",
            "order by operate_time desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<HelloUserOperationRecord> selectFollowedUserRecord(@Param("userId") String userId,
                                                            @Param("currentPage") Integer currentPage,
                                                            @Param("pageSize") Integer pageSize);
}
