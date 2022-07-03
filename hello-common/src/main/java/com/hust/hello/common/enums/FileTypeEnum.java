package com.hust.hello.common.enums;

/**
 * @author: zhuganjun ©
 * @date: 2021/9/25 13:49
 * @description: 文件类型及其对应的COS存储路径
 */
public enum FileTypeEnum {

    PIC_AVATAR(12, "avatar", "avatar/"),
    PIC_STU_CARD(11, "身份证明材料", "stu_card/"),
    PIC_POST(13, "贴文图片", "acc_post/"),

    BAR_AVATAR(21, "贴吧头像", "avatar/");

    /** 类型码 */
    private Integer code;

    /** 类型名称 */
    private String name;

    /** 保存路径 */
    private String path;

    FileTypeEnum(Integer code, String name, String path){
        this.code = code;
        this.name = name;
        this.path = path;
    }

    /**
     * 根据类型码获得该状态码对应的枚举对象
     * @param code 状态码
     * @return 枚举对象或 null
     */
    public static FileTypeEnum getEnum(Integer code){
        if(null == code){
            return null;
        }
        for(FileTypeEnum statusEnum : FileTypeEnum.values()){
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
