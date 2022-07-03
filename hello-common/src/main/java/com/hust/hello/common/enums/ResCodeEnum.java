package com.hust.hello.common.enums;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/10/2 23:12
 * @description: 后端返回结果码
 */
public enum ResCodeEnum {
    SUCCESS(0, "成功"),

    SYS_ERROR(1000, "系统错误. 请联系管理员."),

    PARAM_ERROR(1001, "参数解析错误. 请联系管理员"),
    MAIL_ERROR(1002, "邮件发送错误. 请联系管理员"),
    BOS_ERROR(1003, "BOS错误. 请联系管理员"),
    COOKIE_ERROR(1004, "Cookie解析错误. 请联系管理员"),
    DATA_ERROR(1005, "数据更新失败"),

    LOGIN_ERROR(1010, "账号登录失败."),
    REGISTER_ERROR(1011, "账号注册失败"),
    TOKEN_INVALID(1012, "请登录账号"),
    TOKEN_REPLACE(1013, "该账号已被顶下线"),
    TOKEN_KICKEDOFF(1014, "该账号已被踢下线"),
    USER_NOTEXIST(1015, "该账号不存在"),

    BAR_ERROR(1020,"版面请求错误"),

    POST_ERROR(1030, "贴文请求错误"),
    POST_PIC_ERROR(1031, "贴文图片错误"),

    USER_OPERATION_ERROR(1040, "用户操作失败"),

    COMMENT_ERROR(1050, "评论失败"),

    USER_CONFIG_ERROR(1060, "用户信息错误"),
    ;

    /** 返回状态代码 */
    private Integer code;

    /** 返回信息 */
    private String msg;

    ResCodeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static ResCodeEnum getEnum(Integer code){
        for(ResCodeEnum resCodeEnum : ResCodeEnum.values()){
            if(code == resCodeEnum.getCode()){
                return resCodeEnum;
            }
        }
        return null;
    }

    /**
     * 根据状态值获得状态信息
     * @param code 状态值
     * @return 状态信息
     */
    public static String getResMsg(Integer code){
        for(ResCodeEnum resCodeEnum : ResCodeEnum.values()){
            if(code == resCodeEnum.getCode()){
                return resCodeEnum.getMsg();
            }
        }
        return null;
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
