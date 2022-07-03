package com.hust.hello.common.builder;

import com.hust.hello.common.bean.Response;
import com.hust.hello.common.enums.ResCodeEnum;
import com.hust.hello.common.exception.BusinessException;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/15 12:14
 * @description:
 */
public class ResponseBuilder {


    public static Response createSuccess(Object data) {
        Response response = new Response();
        response.setResult(data);
        response.setMsg(ResCodeEnum.SUCCESS.getMsg());
        response.setCode(ResCodeEnum.SUCCESS.getCode());
        return response;
    }


    public static Response create(BusinessException exception) {
        Response response = new Response();
        response.setMsg(exception.getMsg());
        response.setCode(exception.getCode());
        return response;
    }


    public static Response createExceptionRes(int code, String msg) {
        Response response = new Response();
        response.setMsg(msg);
        response.setCode(code);
        return response;
    }

    public static Response createParamExceptionRes() {
        return createExceptionRes(ResCodeEnum.PARAM_ERROR.getCode(), ResCodeEnum.PARAM_ERROR.getMsg());
    }

    public static Response createParamExceptionRes(String msg) {
        return createExceptionRes(ResCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    public static Response createLoginExceptionRes() {
        return createExceptionRes(ResCodeEnum.LOGIN_ERROR.getCode(), ResCodeEnum.PARAM_ERROR.getMsg());
    }

    public static Response createLoginExceptionRes(String msg) {
        return createExceptionRes(ResCodeEnum.LOGIN_ERROR.getCode(), msg);
    }


    public static Response createSysExceptionRes() {
        return createExceptionRes(ResCodeEnum.SYS_ERROR.getCode(), ResCodeEnum.SYS_ERROR.getMsg());
    }

    public static Response createSendEmailExceptionRes() {
        return createExceptionRes(ResCodeEnum.MAIL_ERROR.getCode(), ResCodeEnum.MAIL_ERROR.getMsg());
    }

    /**
     * 版块错误
     */
    public static Response createBarExceptionRes() {
        return createExceptionRes(ResCodeEnum.BAR_ERROR.getCode(), ResCodeEnum.BAR_ERROR.getMsg());
    }

    public static Response createBarExceptionRes(String msg) {
        return createExceptionRes(ResCodeEnum.BAR_ERROR.getCode(), msg);
    }

    /**
     * 版块错误
     */
    public static Response createPostExceptionRes() {
        return createExceptionRes(ResCodeEnum.POST_ERROR.getCode(), ResCodeEnum.POST_ERROR.getMsg());
    }

    public static Response createPostExceptionRes(String msg) {
        return createExceptionRes(ResCodeEnum.POST_ERROR.getCode(), msg);
    }
    /*
    public static Response createSignatureExceptionRes() {
        return createExceptionRes(ResCodeEnum.SIGNATURE_ERROR.code, ResCodeEnum.SIGNATURE_ERROR.msg);
    }

    public static Response createUploadExceptionRes() {
        return createExceptionRes(ResCodeEnum.UPLOAD_ERROR.code, ResCodeEnum.UPLOAD_ERROR.msg);
    }

    public static Response createOnlineExceptionRes() {
        return createExceptionRes(ResCodeEnum.ONLINE_ERROR.code, ResCodeEnum.ONLINE_ERROR.msg);
    }

    public static Response createDataNotExistExceptionRes() {
        return createExceptionRes(ResCodeEnum.DATA_NOT_EXIST_ERROR.code, ResCodeEnum.DATA_NOT_EXIST_ERROR.msg);
    }

    public static Response createStatusExceptionRes() {
        return createExceptionRes(ResCodeEnum.STATUS_ERROR.code, ResCodeEnum.STATUS_ERROR.msg);
    }

    public static Response createUpdateErrorRes() {
        return createExceptionRes(ResCodeEnum.UPDATE_ERROR.code, ResCodeEnum.UPDATE_ERROR.msg);
    }

     */
}
