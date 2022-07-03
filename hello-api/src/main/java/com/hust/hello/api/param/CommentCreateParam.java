package com.hust.hello.api.param;

import com.hust.hello.common.builder.BusinessExceptionBuilder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/14 21:12
 * @description:
 */
@Data
public class CommentCreateParam {

    private String postId;

    private String objectId;

    private String commentContent;

    public static void paramCheck(CommentCreateParam param) {
        if(StringUtils.isBlank(param.getPostId())) {
            throw BusinessExceptionBuilder.createParamException("postId不能为空");
        }
        if(StringUtils.isBlank(param.getCommentContent())) {
            throw BusinessExceptionBuilder.createParamException("commentContent不能为空");
        }
        if(StringUtils.length(param.getCommentContent()) > 400) {
            throw BusinessExceptionBuilder.createParamException("评论内容过长");
        }
    }
}
