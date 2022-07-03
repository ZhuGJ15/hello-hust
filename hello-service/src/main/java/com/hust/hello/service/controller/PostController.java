package com.hust.hello.service.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hust.hello.api.PostApi;
import com.hust.hello.api.param.PostCreateParam;
import com.hust.hello.api.param.PostReportParam;
import com.hust.hello.api.param.PostUpdateParam;
import com.hust.hello.common.bean.Response;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.builder.ResponseBuilder;
import com.hust.hello.common.builder.SystemExceptionBuilder;
import com.hust.hello.common.exception.BusinessException;
import com.hust.hello.common.utils.HelloStringUtils;
import com.hust.hello.service.annotation.ControllerLog;
import com.hust.hello.service.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/26 21:27
 * @description:
 */
@RestController
@Slf4j
public class PostController implements PostApi {

    @Autowired
    PostService postService;

    @Override
    @ControllerLog
    public Response getHotPostList(Integer postAmount) {
        try{
            if(postAmount <= 0){
                throw BusinessExceptionBuilder.createParamException("贴文数量只能为正数");
            }
            return ResponseBuilder.createSuccess(postService.getHotPosts(postAmount));
        }catch (BusinessException be){
            return ResponseBuilder.createPostExceptionRes(be.getMsg());
        }catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getPostContent(String postId) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(postId)){
                throw BusinessExceptionBuilder.createParamException("postId不能为空");
            }
            return ResponseBuilder.createSuccess(postService.getPostByPostId(userId, postId));
        } catch (BusinessException be) {
            return ResponseBuilder.createPostExceptionRes(be.getMsg());
        } catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response deletePost(String postId) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)){
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(postId)) {
                throw BusinessExceptionBuilder.createParamException("postId不能为空");
            }
            postService.deletePost(userId, postId);
            log.info("user(userId:{}) delete post(postId:{}) success. ", userId, postId);
            return ResponseBuilder.createSuccess(true);
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    public Response uploadPostPic(MultipartFile postPic) {
        try {
            String userId = StpUtil.getLoginIdAsString();
            log.info(HelloStringUtils.LOG_START);
            log.info("PostController. uploadPostPic. called. userId: {}", userId);
            if(StringUtils.isBlank(userId)){
                throw SystemExceptionBuilder.createCookieException();
            }
            return ResponseBuilder.createSuccess(postService.uploadPostPic(userId, postPic));
        } catch (BusinessException be) {
            log.info("BusinessException: {}", be.getMsg());
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        } finally {
            log.info(HelloStringUtils.LOG_END);
        }
    }

    @Override
    @ControllerLog
    public Response createPost(PostCreateParam postCreateParam) {
        try{
            PostCreateParam.paramCheck(postCreateParam);
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            PostUpdateParam updateParam = PostCreateParam.convertToCreateParam(postCreateParam);
            return ResponseBuilder.createSuccess(postService.updatePost(userId, updateParam));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response updatePost(PostUpdateParam postUpdateParam) {
        try{
            PostUpdateParam.paramCheck(postUpdateParam);
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            return ResponseBuilder.createSuccess(postService.updatePost(userId, postUpdateParam));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response likePost(String postId) {
        try {
            String userId = StpUtil.getLoginIdAsString();
            if (StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(postId)) {
                throw BusinessExceptionBuilder.createParamException("postId不能为空");
            }
            postService.doLikePost(userId, postId);
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
    public Response collectPost(String postId) {
        try {
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(postId)) {
                throw BusinessExceptionBuilder.createParamException("postId不能为空");
            }
            postService.doCollectPost(userId, postId);
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
    public Response dislikePost(String postId) {
        try {
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(postId)) {
                throw BusinessExceptionBuilder.createParamException("postId不能为空");
            }
            postService.doDislikePost(userId, postId);
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
    public Response reportPost(PostReportParam postReportParam) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            PostReportParam.paramCheck(postReportParam);
            postService.doReportPost(userId, postReportParam);
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
    public Response recommendPost(Integer currentPage, Integer pageSize) {
        try {
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(currentPage < 1 || pageSize < 1) {
                throw BusinessExceptionBuilder.createParamException("currentPage和pageSize不能小于1");
            }
            return ResponseBuilder.createSuccess(postService.doRecommendPost(userId, currentPage, pageSize));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }
}
