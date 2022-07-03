package com.hust.hello.dao.mapper.customer;

import com.hust.hello.common.model.dto.BarPostListDTO;
import com.hust.hello.common.model.entity.HelloPostConfig;
import com.hust.hello.common.model.vo.PostHomePageVO;
import com.hust.hello.dao.mapper.HelloPostConfigMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/26 12:02
 * @description:
 */
public interface HelloPostConfigCustomMapper extends HelloPostConfigMapper {

    @Select({
            "select id, post_id, bar_id, post_status, post_type, creator_id, create_time, popularity, last_edit_time, " +
                    "view_sum, like_sum, collect_sum, comment_sum, dislike_sum, report_sum from hello_post_config",
            "where post_id = \"${postId}\""
    })
    HelloPostConfig getPostDeleteConfig(@Param("postId") String postId);

    @Select({
            "select post_title, post_text from hello_post_config",
            "where post_id = \"${postId}\""
    })
    HelloPostConfig getPostTitleAndContentByPostId(@Param("postId") String postId);

    @Select({
            "<script>",
            "select post_id, post_title, post_type, popularity from hello_post_config",
            "where bar_id = \"${barId}\"",
            "and post_status = 1",
            "order by post_type desc, popularity desc",
            "<if test=\"postAmount != null\">",
            "limit ${postAmount}",
            "</if>",
            "</script>"
    })
    List<PostHomePageVO> getHomePagePostList(@Param("barId") String barId,
                                             @Param("postAmount") Integer postAmount);

    @Select({
            "select post_id, post_title, post_type, popularity from hello_post_config",
            "where post_status = 1",
            "order by popularity desc",
            "limit ${postAmount}"
    })
    List<PostHomePageVO> getHotPostList(@Param("postAmount") Integer postAmount);

    @Select({
            "select * from hello_post_config",
            "where post_id = \"${postId}\""
    })
    HelloPostConfig getPostByPostId(@Param("postId") String postId);

    @Select({
            "select count(*) from hello_post_config",
            "where creator_id = \"${userId}\"",
            "and post_status != 3"
    })
    Integer getPostAmountByUserId(@Param("userId") String userId);

    @Select({
            "<script>",
            "select * from hello_post_config",
            "where creator_id = \"${userId}\"",
            "and post_status != 3",
            "order by create_time desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<HelloPostConfig> getPostListByUserId(@Param("userId") String userId,
                                              @Param("currentPage") Integer currentPage,
                                              @Param("pageSize") Integer pageSize);

    @Select({
            "<script>",
            "select post_id from hello_post_config",
            "where creator_id = \"${userId}\"",
            "and post_status != 3",
            "order by create_time desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<String> getPostIdListByUserId(@Param("userId") String userId,
                                       @Param("currentPage") Integer currentPage,
                                       @Param("pageSize") Integer pageSize);

    @Select({
            "<script>",
            "select post_id, bar_id, post_type, post_title, post_text, creator_id, create_time, popularity, " +
            "last_edit_time, view_sum, like_sum, collect_sum, dislike_sum, comment_sum, report_sum from hello_post_config",
            "where bar_id = \"${barId}\"",
            "and post_status = 1",
            "order by post_type desc, popularity desc, create_time desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<BarPostListDTO> getPostListByBarId(@Param("barId") String barId,
                                            @Param("currentPage") Integer currentPage,
                                            @Param("pageSize") Integer pageSize);

    @Select({
            "<script>",
            "select * from hello_post_config",
            "where post_status = 1",
            "and post_type != 3",
            "order by popularity desc, post_type desc, create_time desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<HelloPostConfig> getPostListForRecommand(@Param("currentPage") Integer currentPage,
                                                  @Param("pageSize") Integer pageSize);
}
