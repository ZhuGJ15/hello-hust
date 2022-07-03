package com.hust.hello.common.enums;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/6 22:16
 * @description:
 */
public enum PostTypeEnum {
    NORMAL(0, "正常"),
    POPULAR(1, "热门"),
    PREMIUM(2, "精品"),
    TOP(3, "置顶")
    ;

    /** 类型码 */
    private Integer code;

    /** 类型名称 */
    private String name;

    PostTypeEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 根据类型码获得该状态码对应的枚举对象
     * @param code 状态码
     * @return 枚举对象或 null
     */
    public static PostTypeEnum getEnum(Integer code){
        if(null == code){
            return null;
        }
        for(PostTypeEnum statusEnum : PostTypeEnum.values()){
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
