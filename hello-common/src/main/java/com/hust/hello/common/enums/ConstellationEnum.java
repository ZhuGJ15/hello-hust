package com.hust.hello.common.enums;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/8 14:26
 * @description:
 */
public enum ConstellationEnum {

    ARIES(1, "白羊"),
    TAURUS(2, "金牛"),
    GEMINI(3, "双子"),
    CANCER(4, "巨蟹"),
    LEO(5, "狮子"),
    VIRGO(6, "处女"),
    LIBRA(7, "天秤"),
    SCORPIO(8, "天蝎"),
    SAGITTARIUS(9, "射手"),
    CAPRICORN(10, "摩羯"),
    AQUARIUS(11, "水瓶"),
    PISCES(12, "双鱼");

    /** 星座代码 */
    private Integer code;

    /** 星座名称 */
    private String name;

    ConstellationEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 根据类型码获得该状态码对应的枚举对象
     * @param code 状态码
     * @return 枚举对象或 null
     */
    public static ConstellationEnum getEnum(Integer code){
        if(null == code){
            return null;
        }
        for(ConstellationEnum aenum : ConstellationEnum.values()){
            if(aenum.getCode().equals(code)){
                return aenum;
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
