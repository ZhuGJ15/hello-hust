package com.hust.hello.common.enums;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/10/2 22:57
 * @description: 账号状态
 */
public enum AccountStatusEnum {
    NORMAL(0, "正常"),
    BANNED(1, "封禁"),
    MUTED(2, "禁言"),
    DELETED(3, "删除")
    ;


    /** 状态代码 */
    private Integer code;

    /** 状态名称 */
    private String name;

    AccountStatusEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static AccountStatusEnum getEnum(Integer code){
        if(null == code){
            return null;
        }
        for(AccountStatusEnum accountEnum: AccountStatusEnum.values()){
            if(accountEnum.getCode().equals(code)){
                return accountEnum;
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
