package com.hust.hello.service.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hust.hello.api.UserApi;
import com.hust.hello.common.bean.Response;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.builder.ResponseBuilder;
import com.hust.hello.common.builder.SystemExceptionBuilder;
import com.hust.hello.common.enums.FileTypeEnum;
import com.hust.hello.common.exception.BusinessException;
import com.hust.hello.common.model.vo.UserConfigVO;
import com.hust.hello.common.utils.HelloStringUtils;
import com.hust.hello.service.annotation.ControllerLog;
import com.hust.hello.service.service.PostService;
import com.hust.hello.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/15 11:43
 * @description:
 */

@RestController
@Slf4j
public class UserController implements UserApi {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Override
    @ControllerLog
    public Response getBasicInfo() {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)){
                throw SystemExceptionBuilder.createCookieException();
            }
            UserConfigVO userInfoVO = userService.getUserInfo(userId);
            return ResponseBuilder.createSuccess(userInfoVO);
        } catch (BusinessException be){
            return ResponseBuilder.createLoginExceptionRes(be.getMsg());
        } catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getUserAvatar() {
        try {
            String userId = StpUtil.getLoginIdAsString();
            if (StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            // 下面是返回图片的方式
            // BufferedImage picImage = userService.getUserAvatar(userId);
            // response.setContentType("image/png");
            // ServletOutputStream outputStream = response.getOutputStream();
            // ImageIO.write(picImage, "png", outputStream);
            return ResponseBuilder.createSuccess(userService.getUserAvatarUrl(userId));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getUserPostList(Integer currentPage, Integer pageSize) {
        try {
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)){
                throw SystemExceptionBuilder.createCookieException();
            }
            if(currentPage < 1 || pageSize < 1){
                throw BusinessExceptionBuilder.createParamException("参数错误. currentPage和pageSize不能小于1");
            }
            return ResponseBuilder.createSuccess(postService.getPostList(userId, currentPage, pageSize));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getUserPostIDList(Integer currentPage, Integer pageSize) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)){
                throw SystemExceptionBuilder.createCookieException();
            }
            if(null != currentPage || null != pageSize){
                if(currentPage < 1 || pageSize < 1){
                    throw BusinessExceptionBuilder.createParamException("参数错误. currentPage和pageSize不能小于1");
                }
            }
            return ResponseBuilder.createSuccess(postService.getPostIdList(userId, currentPage, pageSize));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getDisplayedInfo(String otherUserId) {
        try {
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)){
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(otherUserId)) {
                throw BusinessExceptionBuilder.createParamException("otherUserId不能为空");
            }
            return ResponseBuilder.createSuccess(userService.getDisplayedInfo(userId, otherUserId));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response followOtherUser(String otherUserId) {
        try {
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(otherUserId)) {
                throw BusinessExceptionBuilder.createParamException("otherUserId不能为空");
            }
            userService.doFollowUser(userId, otherUserId);
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
    public Response cancelFollowOtherUser(String otherUserId) {
        try {
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(otherUserId)) {
                throw BusinessExceptionBuilder.createParamException("otherUserId不能为空");
            }
            userService.doCancelFollowUser(userId, otherUserId);
            return ResponseBuilder.createSuccess(true);
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    public Response uploadUserAvatar(MultipartFile avatarFile) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            return ResponseBuilder.createSuccess(userService.uploadUserAvatar(userId, avatarFile));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }

    }

    @Override
    @ControllerLog
    public Response updateUserConfig(String userName, Integer showSex, Integer showConstellation) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(StringUtils.isBlank(userName) && null == showSex && null == showConstellation) {
                throw BusinessExceptionBuilder.createParamException("参数不能都为空");
            }
            // check the param(userName) is legal or not
            if(!StringUtils.isBlank(userName)) {
                if(StringUtils.length(userName) < 2 || StringUtils.length(userName) > 9) {
                    throw BusinessExceptionBuilder.createParamException("昵称长度为2-9个字符");
                }
            }
            // check the param(showSex, showConstellation) is legal or not
            if(null != showSex){
                showSex = ((0 == showSex || 1 == showSex) ? showSex : 0);
            }
            if(null != showConstellation) {
                showConstellation = ((0 == showConstellation || 1 == showConstellation) ? showConstellation : 0);
            }

            userService.updateUserConfig(userId, userName, showSex, showConstellation);
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
    public Response getDefaultAvatarList() {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            return ResponseBuilder.createSuccess(userService.getDefaultAvatarUrlList(userId));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response selectAvatar(String avatarUrl) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            userService.updateAvatarByUrl(userId, avatarUrl);
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
    public Response getCollectPostList(Integer currentPage, Integer pageSize) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(currentPage < 1 || pageSize < 1) {
                throw BusinessExceptionBuilder.createParamException("currentPage和pageSize不能小于1");
            }
            return ResponseBuilder.createSuccess(userService.getUserCollectPostList(userId, currentPage, pageSize));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getFollowingUserList(Integer currentPage, Integer pageSize) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(currentPage < 1 || pageSize < 1) {
                throw BusinessExceptionBuilder.createParamException("currentPage和pageSize不能小于1");
            }
            return ResponseBuilder.createSuccess(userService.getFollowingUserList(userId, currentPage, pageSize));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getFollowedUserList(Integer currentPage, Integer pageSize) {
        try{
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            if(currentPage < 1 || pageSize < 1) {
                throw BusinessExceptionBuilder.createParamException("currentPage和pageSize不能小于1");
            }
            return ResponseBuilder.createSuccess(userService.getFollowedUserList(userId, currentPage, pageSize));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }
}
