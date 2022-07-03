package com.hust.hello.common.exception;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/10/2 23:04
 * @description: 业务异常
 */
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = -3933220077891150431L;

    /** 异常代码 */
    private Integer code;
    /** 异常信息 */
    private String msg;

    public BusinessException(Integer code, String msg, Throwable t){
        super(msg, t);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(){
        super();
    }

    public BusinessException(String msg){
        this(null, msg, null);
    }

    public BusinessException(Integer code, String msg){
        this(code, msg, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
