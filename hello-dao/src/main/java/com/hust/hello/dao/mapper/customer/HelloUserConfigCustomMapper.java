package com.hust.hello.dao.mapper.customer;

import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.dao.mapper.HelloUserConfigMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/18 13:16
 * @description:
 */
public interface HelloUserConfigCustomMapper extends HelloUserConfigMapper {

    @Select({
            "select * from hello_user_config",
            "where user_id = \"${userId}\""
    })
    HelloUserConfig getUserByUserId(@Param("userId") String userId);

    @Select({
            "select user_name from hello_user_config",
            "where user_id = \"${userId}\" limit 1"
    })
    String getUserNameByUserId(@Param("userId") String userId);

    @Select({
            "select user_id from hello_user_config",
            "where email = \"${email}\" limit 1"
    })
    String getUserIdByEmail(@Param("email") String email);

    @Select({
            "select * from hello_user_config",
            "where email = \"${email}\""
    })
    HelloUserConfig getUserByEmail(@Param("email") String email);

    @Select({
            "select post_sum from hello_user_config",
            "where user_id = \"${userId}\""
    })
    Integer getPostSumByUserId(@Param("userId") String userId);
}
