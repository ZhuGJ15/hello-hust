package com.hust.hello.common.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/28 14:55
 * @description:
 */
@Data
public class BarPostListDTO {
    private String postId;

    private String barId;

    private Integer postType;

    private String postTitle;

    private String postText;

    private String creatorId;

    private Date createTime;

    private Integer popularity;

    private Date lastEditTime;

    private Integer viewSum;

    private Integer likeSum;

    private Integer collectSum;

    private Integer dislikeSum;

    private Integer commentSum;

    private Integer reportSum;
}
