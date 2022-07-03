package com.hust.hello.common.exception;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/10/2 23:09
 * @description: 系统异常
 */
public class SystemException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /** 异常代码 */
    private Integer code;
    /** 异常信息 */
    private String msg;

    public SystemException(Integer code, String msg, Throwable t){
        super(msg, t);
        this.code = code;
        this.msg = msg;
    }

    public SystemException(){
        super();
    }

    public SystemException(String msg){
        this(null, msg, null);
    }

    public SystemException(Integer code, String msg){
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
