package com.hust.hello.common.enums;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/14 22:29
 * @description:
 */
public enum CommentStatusEnum {

    NORMAL(0, "正常"),
    USER_DELETE(1, "用户删除"),
    ADMIN_DELETE(2, "管理员删除"),
    ;

    /** 状态代码 */
    private Integer code;

    /** 状态名称 */
    private String name;

    CommentStatusEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static CommentStatusEnum getEnum(Integer code){
        if(null == code){
            return null;
        }
        for(CommentStatusEnum statusEnum: CommentStatusEnum.values()){
            if(statusEnum.getCode().equals(code)){
                return statusEnum;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
