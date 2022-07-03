package com.hust.hello.common.builder;

import com.hust.hello.common.bean.Response;
import com.hust.hello.common.enums.ResCodeEnum;
import com.hust.hello.common.exception.SystemException;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/3 9:06
 * @description: 系统异常创建对象
 */
public class SystemExceptionBuilder {

    /**
     * 系统错误
     * @param msg
     * @return
     */
    public static SystemException createException(String msg){
        return new SystemException(ResCodeEnum.SYS_ERROR.getCode(), msg);
    }

    public static SystemException createCookieException(){
        return new SystemException(ResCodeEnum.SYS_ERROR.getCode(), ResCodeEnum.SYS_ERROR.getMsg());
    }

    public static SystemException createBosException(){
        return new SystemException(ResCodeEnum.BOS_ERROR.getCode(), ResCodeEnum.BOS_ERROR.getMsg());
    }

    public static SystemException createBosException(String msg){
        return new SystemException(ResCodeEnum.BOS_ERROR.getCode(), msg);
    }

    public static SystemException createMySqlException(String msg) {
        return new SystemException(ResCodeEnum.DATA_ERROR.getCode(), msg);
    }

    public static SystemException createMySqlException() {
        return new SystemException(ResCodeEnum.DATA_ERROR.getCode(), ResCodeEnum.DATA_ERROR.getMsg());
    }
}
