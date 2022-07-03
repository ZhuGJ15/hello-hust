package com.hust.hello.common.model.vo;

import lombok.Data;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/26 12:11
 * @description:
 */
@Data
public class PostHomePageVO {
    private String postId;

    private String postTitle;

    private Integer postType;

    private Integer popularity;
}
