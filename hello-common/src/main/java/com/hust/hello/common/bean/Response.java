package com.hust.hello.common.bean;

import com.hust.hello.common.enums.ResCodeEnum;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/10/2 23:17
 * @description: 返回值对象
 */
public class Response<T> {
    /** 返回状态代码 */
    private Integer code;
    /** 返回信息 */
    private String msg;
    /** 需要返回的结果对象 */
    private T result;

    /**
     * 判断是否成功返回
     * @return
     */
    public boolean isSuccess(){
        if(null != code && ResCodeEnum.SUCCESS.getCode() == code){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("{code:").append(code).append(",")
                .append("msg:").append(msg).append(",")
                .append("result:").append(result).append("}");
        return buffer.toString();
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

    public T getResult() {
        return result;
    }

    public void setResult(T resultObject) {
        this.result = resultObject;
    }
}
