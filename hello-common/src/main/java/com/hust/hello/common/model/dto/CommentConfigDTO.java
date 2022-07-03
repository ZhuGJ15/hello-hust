package com.hust.hello.common.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/15 10:59
 * @description:
 */
@Data
public class CommentConfigDTO {

    private String commentId;

    private String postId;

    private String objectId;

    private String creatorId;

    private String commentContent;

    private Date createTime;

    private Date lastEditTime;

    private Integer likeSum;

    private Integer dislikeSum;
}
