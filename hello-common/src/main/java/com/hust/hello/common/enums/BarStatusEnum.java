package com.hust.hello.common.enums;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/26 16:37
 * @description:
 */
public enum BarStatusEnum {

    TOBE_REVIEWED(0, "待审核"),
    NORMAL(1, "正常"),
    BANNED(2, "封禁"),
    DELETED(3, "删除")
    ;

    /** 状态代码 */
    private Integer code;

    /** 状态名称 */
    private String name;

    BarStatusEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static BarStatusEnum getEnum(Integer code){
        if(null == code){
            return null;
        }
        for(BarStatusEnum barStatusEnum: BarStatusEnum.values()){
            if(barStatusEnum.getCode().equals(code)){
                return barStatusEnum;
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
