package com.hust.hello.api.param;

import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.exception.BusinessException;
import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.common.utils.HelloStringUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/18 14:09
 * @description:
 */
@Data
public class UserRegisterParam {
    private String userId;

    private String userName;

    private String password;

    private String email;

    private String sex;

    private String birthday;

    private String verifyCode;

    private Integer showSex = 0;

    private Integer showConstellation = 0;

    public static void paramCheck(UserRegisterParam userRegisterParam){
        if(StringUtils.isBlank(userRegisterParam.userId)){
            throw BusinessExceptionBuilder.userRegisterException("用户名不能为空");
        }
        HelloStringUtils.isUserIdLegal(userRegisterParam.getUserId());
        if(StringUtils.isBlank(userRegisterParam.userName)){
            throw BusinessExceptionBuilder.userRegisterException("昵称不能为空");
        }
        if(userRegisterParam.userName.length() < 2 || userRegisterParam.userName.length() > 9){
            throw BusinessExceptionBuilder.userRegisterException("昵称不能太短或太长");
        }
        if(StringUtils.isBlank(userRegisterParam.password)){
            throw BusinessExceptionBuilder.userRegisterException("密码不能为空");
        }
        HelloStringUtils.isPasswordLegal(userRegisterParam.password);
        if(StringUtils.isBlank(userRegisterParam.email)){
            throw BusinessExceptionBuilder.userRegisterException("邮箱不能为空");
        }
        // todo 邮箱验证码
        if(!StringUtils.equals(userRegisterParam.verifyCode, "2222")){
            throw BusinessExceptionBuilder.userRegisterException("验证码错误");
        }
        HelloStringUtils.isEmailLegal(userRegisterParam.email);
        if(null == userRegisterParam.sex){
            throw BusinessExceptionBuilder.userRegisterException("性别不能为空");
        }
        if(!StringUtils.equals(userRegisterParam.sex, "0") &&
            !StringUtils.equals(userRegisterParam.sex, "1")){
            throw BusinessExceptionBuilder.userRegisterException("性别只能为女(0)或男(1)");
        }
        if(null == userRegisterParam.birthday){
            throw BusinessExceptionBuilder.userRegisterException("生日不能为空");
        }
        try{
            DateUtils.parseDate(userRegisterParam.birthday, "yyyy-MM-dd");
        }catch (Exception e){
            throw BusinessExceptionBuilder.userRegisterException("生日格式错误");
        }
        if(null == userRegisterParam.showSex) {
            userRegisterParam.setShowSex(0);
        }
        if(userRegisterParam.showSex != 0 && userRegisterParam.showSex != 1) {
            throw BusinessExceptionBuilder.userRegisterException("showSex只能为0或1");
        }
        if(null == userRegisterParam.showConstellation) {
            userRegisterParam.setShowConstellation(0);
        }
        if(userRegisterParam.showConstellation != 0 && userRegisterParam.showConstellation != 1) {
            throw BusinessExceptionBuilder.userRegisterException("showConstellation只能为0或1");
        }
    }

    public static HelloUserConfig paramConvert(UserRegisterParam userRegisterParam){
        HelloUserConfig userConfig = new HelloUserConfig();
        userConfig.setUserId(userRegisterParam.getUserId());
        userConfig.setUserName(userRegisterParam.getUserName());
        userConfig.setPassword(userRegisterParam.getPassword());
        userConfig.setEmail(userRegisterParam.getEmail());
        userConfig.setShowSex(userRegisterParam.getShowSex());
        userConfig.setShowConstellation(userRegisterParam.getShowConstellation());
        try{
            userConfig.setBirthday(DateUtils.parseDate(userRegisterParam.getBirthday(), "yyyy-MM-dd"));
        }catch (Exception be){
            throw BusinessExceptionBuilder.createParamException("生日格式解析错误");
        }
        userConfig.setSex(Byte.parseByte(userRegisterParam.sex));
        return userConfig;
    }
}
