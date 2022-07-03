package com.hust.hello.api.param;

import com.hust.hello.common.builder.BusinessExceptionBuilder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/12 12:51
 * @description:
 */
@Data
public class PostReportParam {

    private String postId;

    private String remark;

    public static void paramCheck(PostReportParam param) {
        if(StringUtils.isBlank(param.postId)) {
            throw BusinessExceptionBuilder.createParamException("postId不能为空");
        }
        if(StringUtils.isBlank(param.remark)) {
            throw BusinessExceptionBuilder.createParamException("remark不能为空");
        }
        if(StringUtils.length(param.remark) < 5) {
            throw BusinessExceptionBuilder.createParamException("remark字数过少，请详细描述理由");
        }
        if(StringUtils.length(param.remark) > 150) {
            throw BusinessExceptionBuilder.createParamException("理由请不要超过150字");
        }
    }
}
