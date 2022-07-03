package com.hust.hello.dao.mapper.customer;

import com.hust.hello.common.model.dto.CommentConfigDTO;
import com.hust.hello.common.model.entity.HelloCommentConfig;
import com.hust.hello.dao.mapper.HelloCommentConfigMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/14 21:40
 * @description:
 */
public interface HelloCommentConfigCustomMapper extends HelloCommentConfigMapper {


    @Select({
            "select * from hello_comment_config",
            "where comment_id = \"${commentId}\""
    })
    HelloCommentConfig selectCommentByCommentId(@Param("commentId") String commentId);


    @Select({
            "<script>",
            "select * from hello_comment_config",
            "where post_id = \"${postId}\"",
            "and comment_status = 0",
            "order by create_time desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<CommentConfigDTO> selectCommentsOrderByTimeWithPostId(@Param("postId") String postId,
                                                               @Param("currentPage") Integer currentPage,
                                                               @Param("pageSize") Integer pageSize);

    @Select({
            "<script>",
            "select * from hello_comment_config",
            "where post_id = \"${postId}\"",
            "and comment_status = 0",
            "order by like_sum desc",
            "<if test=\"currentPage != null and pageSize != null\">",
            "<bind name=\"curPageSql\" value=\"(currentPage-1)*pageSize\">",
            "</bind>",
            " limit ${curPageSql}, ${pageSize}",
            "</if>",
            "</script>"
    })
    List<CommentConfigDTO> selectCommentsOrderByLikeWithPostId(@Param("postId") String postId,
                                                               @Param("currentPage") Integer currentPage,
                                                               @Param("pageSize") Integer pageSize);
}
