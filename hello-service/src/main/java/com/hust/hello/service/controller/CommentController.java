package com.hust.hello.service.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hust.hello.api.CommentApi;
import com.hust.hello.api.param.CommentCreateParam;
import com.hust.hello.common.bean.Response;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.builder.ResponseBuilder;
import com.hust.hello.common.builder.SystemExceptionBuilder;
import com.hust.hello.common.exception.BusinessException;
import com.hust.hello.service.annotation.ControllerLog;
import com.hust.hello.service.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/14 21:18
 * @description:
 */
@RestController
@Slf4j
public class CommentController implements CommentApi {

    @Autowired
    private CommentService commentService;

    @Override
    @ControllerLog
    public Response addComment(CommentCreateParam param) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if (StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            commentService.addComment(userId, param);
            return ResponseBuilder.createSuccess(true);
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getCommentList(String postId, Integer currentPage, Integer pageSize, Integer sortType) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if (StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(postId)) {
                throw BusinessExceptionBuilder.createParamException("postId不能为空");
            }
            if(null != currentPage && currentPage < 1) {
                throw BusinessExceptionBuilder.createParamException("currentPage不能小于1");
            }
            if(null != pageSize && pageSize < 1) {
                throw BusinessExceptionBuilder.createParamException("pageSize不能小于1");
            }
            sortType = (null == sortType ? 1 : sortType);
            return ResponseBuilder.createSuccess(commentService.getCommentList(userId, postId, currentPage, pageSize, sortType));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getComment(String commentId) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(commentId)) {
                throw BusinessExceptionBuilder.createParamException("commentId不能为空");
            }
            return ResponseBuilder.createSuccess(commentService.getCommentConfig(userId, commentId));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response deleteComment(String commentId) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)){
                throw SystemExceptionBuilder.createCookieException();
            }
            commentService.deleteCommentConfig(userId, commentId);
            return ResponseBuilder.createSuccess(true);
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response likeComment(String commentId) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            commentService.likeComment(userId, commentId);
            return ResponseBuilder.createSuccess(true);
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response dislikeComment(String commentId) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            commentService.dislikeComment(userId, commentId);
            return ResponseBuilder.createSuccess(true);
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }
}
