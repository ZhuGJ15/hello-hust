package com.hust.hello.dao.mapper.customer;

import com.hust.hello.common.model.dto.BarNameListDTO;
import com.hust.hello.common.model.entity.HelloBarConfig;
import com.hust.hello.dao.mapper.HelloBarConfigMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/22 17:21
 * @description:
 */
public interface HelloBarConfigCustomMapper extends HelloBarConfigMapper {

    @Select({
            "select * from hello_bar_config",
            "where bar_id = \"${barId}\""
    })
    HelloBarConfig getBarConfig(@Param("barId") String barId);

    @Select({
            "select bar_name from hello_bar_config",
            "where bar_id = \"${barId}\""
    })
    String getBarNameByBarId(@Param("barId") String barId);

    @Select({
            "select bar_id, bar_name from hello_bar_config",
            "where bar_status = 1",
            "order by popularity desc"
    })
    List<BarNameListDTO> getBarNameList();

    @Select({
            "<script>",
            "select * from hello_bar_config",
            "where bar_status = 1",
            "order by bar_type desc, popularity desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<HelloBarConfig> getAllBarList(@Param("currentPage") Integer currentPage,
                                      @Param("pageSize") Integer pageSize);


    @Select({
            "<script>",
            "select * from hello_bar_config",
            "where bar_status = 1",
            "order by bar_type desc, popularity desc",
            "<if test=\"barAmount != null \">",
            "limit ${barAmount}",
            "</if>",
            "</script>"
    })
    List<HelloBarConfig> getHomePageBarList(@Param("barAmount") Integer barAmount);

    @Select({
            "select * from hello_bar_config",
            "where bar_status = 1",
            "order by popularity desc",
            "limit ${barAmount}"
    })
    List<HelloBarConfig> getHotBarConfigList(@Param("barAmount") Integer barAmount);

    @Select({
            "select post_sum from hello_bar_config",
            "where barId = \"${barId}\""
    })
    Integer getPostSumByBarId(@Param("barId") String barId);

    @Select({
            "select avatar_path from hello_bar_config",
            "where barId = \"${barId}\""
    })
    String getAvatarPathByBarId(@Param("barId") String barId);

    @Select({
            "select count(*) from hello_bar_config",
            "where bar_type = 3"
    })
    Integer getTopBarAccount();

    @Select({
            "select count(*) from hello_bar_config",
            "where bar_type = 2"
    })
    Integer getPremiumBarAccount();

    @Select({
            "select count(*) from hello_bar_config",
            "where bar_type = 1"
    })
    Integer getNormalBarAccount();
}
