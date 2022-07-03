package com.hust.hello.api.param;

import com.hust.hello.common.builder.BusinessExceptionBuilder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/6 20:09
 * @description:
 */
@Data
public class PostUpdateParam {
    private String originalPostId;

    private String barId;

    private String postTitle;

    private String postContent;

    public static void paramCheck(PostUpdateParam param) {
        if(StringUtils.isBlank(param.originalPostId)) {
            throw BusinessExceptionBuilder.createParamException("原贴文id不能为空");
        }
        if(StringUtils.isBlank(param.barId)) {
            throw BusinessExceptionBuilder.createPostPicException("barId不能为空");
        }
        if(StringUtils.isBlank(param.postTitle)){
            throw BusinessExceptionBuilder.createParamException("postTitle不能为空");
        }
        if(StringUtils.length(param.postTitle) > 25) {
            throw BusinessExceptionBuilder.createParamException("postTitle长度不能超过25");
        }
        if(StringUtils.isBlank(param.postContent)) {
            throw BusinessExceptionBuilder.createParamException("postContent不能为空");
        }
        if(StringUtils.length(param.postContent) > 3000) {
            throw BusinessExceptionBuilder.createParamException("postContent长度过大");
        }
    }
}
