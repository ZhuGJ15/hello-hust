package com.hust.hello.common.enums;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/28 9:39
 * @description:
 */
public enum PostStatusEnum {

    REVIEWING(0, "待审核"),
    NORMAL(1, "正常"),
    BANNED(2, "被封禁"),
    DELETED(3, "被删除")
    ;

    /** 类型码 */
    private Integer code;

    /** 类型名称 */
    private String name;

    PostStatusEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 根据类型码获得该状态码对应的枚举对象
     * @param code 状态码
     * @return 枚举对象或 null
     */
    public static PostStatusEnum getEnum(Integer code){
        if(null == code){
            return null;
        }
        for(PostStatusEnum statusEnum : PostStatusEnum.values()){
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
