package com.hust.hello.service.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hust.hello.api.BarApi;
import com.hust.hello.common.bean.Response;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.builder.ResponseBuilder;
import com.hust.hello.common.builder.SystemExceptionBuilder;
import com.hust.hello.common.exception.BusinessException;
import com.hust.hello.service.annotation.ControllerLog;
import com.hust.hello.service.service.BarService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/22 17:24
 * @description:
 */
@RestController
@Slf4j
public class BarController implements BarApi {

    @Autowired
    private BarService barService;

    @Override
    @ControllerLog
    public Response getHomePageBarConfig(Integer barAmount, Integer postAmount) {
        try{
            if(barAmount < 2){
                throw BusinessExceptionBuilder.createParamException("首页贴吧数量不能过少");
            }
            if(postAmount < 0){
                throw BusinessExceptionBuilder.createParamException("postAmount不能为负数");
            }
            return ResponseBuilder.createSuccess(barService.getHomePageBarConfig(barAmount, postAmount));
        } catch (BusinessException be) {
            return ResponseBuilder.createParamExceptionRes(be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getBarConfig(String barId) {
        try{
            if(StringUtils.isBlank(barId)) {
                throw BusinessExceptionBuilder.createParamException("barId不能为空");
            }
            return ResponseBuilder.createSuccess(barService.getBarConfig(barId));
        }catch (BusinessException be){
            return ResponseBuilder.createBarExceptionRes(be.getMsg());
        }catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getBarConfigList(Integer currentPage, Integer pageSize, Integer postAmount) {
        try{
            if(currentPage <= 0){
                throw BusinessExceptionBuilder.createParamException("currentPage只能为正数");
            }
            if(pageSize <= 0){
                throw BusinessExceptionBuilder.createParamException("pageSize只能为正数");
            }
            if(postAmount < 0){
                throw BusinessExceptionBuilder.createParamException("postAmount不能为负数");
            }
            return ResponseBuilder.createSuccess(barService.getBarList(currentPage, pageSize, postAmount));
        }catch (BusinessException be){
            return ResponseBuilder.createBarExceptionRes(be.getMsg());
        } catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getHotBarList(Integer barAmount) {
        try{
            if(barAmount <= 0){
                throw BusinessExceptionBuilder.createParamException("barAmount只能为正数");
            }
            return ResponseBuilder.createSuccess(barService.getHotBars(barAmount));
        }catch (BusinessException be){
            return ResponseBuilder.createBarExceptionRes(be.getMsg());
        }catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getPostListByBarId(String barId, Integer currentPage, Integer pageSize) {
        try{
            if(StringUtils.isBlank(barId)){
                throw BusinessExceptionBuilder.createParamException("参数错误. barId不能为空");
            }
            if(currentPage < 1 || pageSize < 1) {
                throw BusinessExceptionBuilder.createParamException("参数错误. currentPage和pageSize不能小于1");
            }
            String userId = StpUtil.getLoginIdAsString();
            if(StringUtils.isBlank(userId)) {
                throw SystemExceptionBuilder.createCookieException();
            }
            return ResponseBuilder.createSuccess(barService.getPostListByBarId(userId, barId, currentPage, pageSize));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getAvatar(String barId) {
        try{
            if(StringUtils.isBlank(barId)){
                throw BusinessExceptionBuilder.createParamException("参数错误. barId不能为空");
            }
            return ResponseBuilder.createSuccess(barService.getBarAvatarUrl(barId));
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    public Response getBarNameList() {
        try{
            return ResponseBuilder.createSuccess(barService.getBarNameList());
        } catch (BusinessException be) {
            return ResponseBuilder.createExceptionRes(be.getCode(), be.getMsg());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response applyNewBar(String barName, String remark) {
        return null;
    }
}
