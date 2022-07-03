package com.hust.hello.common.builder;

import com.hust.hello.common.bean.Response;
import com.hust.hello.common.enums.ResCodeEnum;
import com.hust.hello.common.exception.BusinessException;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/10/2 23:23
 * @description: 创建业务异常对象
 */
public class BusinessExceptionBuilder {

    /**
     * 通用的业务异常对象创建方式
     * @param errorCode 业务错误码
     * @param msg 业务错误信息
     * @return
     */
    public static BusinessException createException(Integer errorCode, String msg){
        return new BusinessException(errorCode, msg);
    }

    /**
     * 通用的业务异常对象创建方式
     * @param response 需要自己创建一个返回值对象
     * @return 抛出业务异常
     */
    public static BusinessException createException(Response response){
        return new BusinessException(response.getCode(), response.getMsg());
    }

    /**
     * 用户登录失败
     * @return
     */
    public static BusinessException userLoginException(String msg){
        return new BusinessException(ResCodeEnum.LOGIN_ERROR.getCode(), msg);
    }

    /**
     * 用户注册失败
     * @return
     */
    public static BusinessException userRegisterException(String msg){
        return new BusinessException(ResCodeEnum.REGISTER_ERROR.getCode(), msg);
    }

    /**
     * 创建参数错误的异常对象
     * @return 抛出参数错误的异常
     */
    public static BusinessException createParamException(){
        return new BusinessException(ResCodeEnum.PARAM_ERROR.getCode(), ResCodeEnum.PARAM_ERROR.getMsg());
    }

    public static BusinessException createParamException(String msg){
        return new BusinessException(ResCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    /**
     * 邮件服务异常
     * @return 抛出邮件异常对象
     */
    public static BusinessException createEmailException(){
        return new BusinessException(ResCodeEnum.MAIL_ERROR.getCode(), ResCodeEnum.MAIL_ERROR.getMsg());
    }

    public static BusinessException createEmailException(String msg){
        return new BusinessException(ResCodeEnum.MAIL_ERROR.getCode(), msg);
    }

    /**
     * 板块错误
     */
    public static BusinessException createBarException(){
        return new BusinessException(ResCodeEnum.BAR_ERROR.getCode(), ResCodeEnum.BAR_ERROR.getMsg());
    }

    public static BusinessException createBarException(String msg) {
        return new BusinessException(ResCodeEnum.BAR_ERROR.getCode(), msg);
    }

    /**
     * 贴文错误
     */
    public static BusinessException createPostException(){
        return new BusinessException(ResCodeEnum.POST_ERROR.getCode(), ResCodeEnum.POST_ERROR.getMsg());
    }

    public static BusinessException createPostException(String msg) {
        return new BusinessException(ResCodeEnum.POST_ERROR.getCode(), msg);
    }

    /**
     * 贴文图片错误
     */
    public static BusinessException createPostPicException(String msg) {
        return new BusinessException(ResCodeEnum.POST_PIC_ERROR.getCode(), msg);
    }

    /**
     * 用户操作失败
     */
    public static BusinessException createUserOperationException(String msg){
        return new BusinessException(ResCodeEnum.USER_OPERATION_ERROR.getCode(), msg);
    }

    /**
     * 评论错误
     */
    public static BusinessException createCommentException(String msg){
        return new BusinessException(ResCodeEnum.COMMENT_ERROR.getCode(), msg);
    }

    /**
     * Exception related to user config.
     * May appear when updating user config.
     */
    public static BusinessException createUserConfigException(String msg) {
        return new BusinessException(ResCodeEnum.USER_CONFIG_ERROR.getCode(), msg);
    }
}
