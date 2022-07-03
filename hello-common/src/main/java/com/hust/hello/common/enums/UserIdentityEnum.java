package com.hust.hello.common.enums;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/10/2 22:41
 * @description: 用户身份枚举
 */
public enum UserIdentityEnum {
    VISITOR(0, "游客"),
    HUST(1, "华中科技大学"),      // Huazhong University of Science & Technology
    WHU(2, "武汉大学"),          // Wuhan University
    WHUT(3, "武汉理工大学"),      // Wuhan University of Technology
    CCNU(4, "华中师范大学"),      // Central China Normal University
    HZAU(5, "华中农业大学"),      // Huazhong Agricultural University
    CUG(6, "中国地质大学"),       // China University of Geosciences
    ZUEL(7, "中南财经政法大学")    // Zhongnan University of Economics and Law
    ;


    /** 身份代码 */
    private Integer code;

    /** 身份名称 */
    private String name;

    UserIdentityEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static UserIdentityEnum getEnum(Integer code){
        if(null == code){
            return null;
        }
        for(UserIdentityEnum userEnum: UserIdentityEnum.values()){
            if(userEnum.getCode().equals(code)){
                return userEnum;
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
