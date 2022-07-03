package com.hust.hello.common.enums;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/11 11:24
 * @description:
 */
public enum UserOperationTypeEnum {

    FOLLOW_USER(1, "关注用户"),
    FOLLOW_BAR(2, "关注贴吧"),
    COLLECT_POST(3, "收藏贴文"),
    LIKE_POST(4, "点赞贴文"),
    DISLIKE_POST(5, "踩帖"),
    REPORT_USER(6, "举报用户"),
    REPORT_POST(7, "举报贴文"),

    LIKE_COMMENT(8, "点赞评论"),
    DISLIKE_COMMENT(9, "踩评论"),
    REPORT_COMMENT(10, "举报评论"),

    APPLY_NEW_BAR(11, "申请创建新版面"),
    ;

    /** 类型码 */
    private Integer code;

    /** 类型名称 */
    private String name;

    UserOperationTypeEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 根据类型码获得该状态码对应的枚举对象
     * @param code 状态码
     * @return 枚举对象或 null
     */
    public static UserOperationTypeEnum getEnum(Integer code){
        if(null == code){
            return null;
        }
        for(UserOperationTypeEnum typeEnum : UserOperationTypeEnum.values()){
            if(typeEnum.getCode().equals(code)){
                return typeEnum;
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
