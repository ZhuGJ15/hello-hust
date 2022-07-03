package com.hust.hello.common.model.vo;

import com.hust.hello.common.model.entity.HelloUserConfig;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.Instant;
import java.util.Date;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/19 14:57
 * @description: 展示给前端的用户信息
 */
@Data
public class UserConfigVO {

    private Long id;

    private String userId;

    private String userName;

    private String email;

    private int sex;

    private String birthday;

    private int accountStatus;     // 账号状态

    private int accountClass;      // 账号等级

    private int accountScore;      // 账号积分

    private String registrationTime;    // 账号注册时长

    private String lastLoginTime;       // 最后一次登录时间

    private int signInDays;           // 签到天数

    private String avatarUrl;         // 头像路径

    /**
     * 将数据库中的用户数据转换为前端展示数据
     * @param userConfig
     * @return
     */
    public static UserConfigVO userConfigConvertToVo(HelloUserConfig userConfig){
        UserConfigVO userConfigVO = new UserConfigVO();
        userConfigVO.setId(userConfig.getId());
        userConfigVO.setUserId(userConfig.getUserId());
        userConfigVO.setUserName(userConfig.getUserName());
        userConfigVO.setEmail(userConfig.getEmail());
        userConfigVO.setSex(userConfig.getSex());
        userConfigVO.setBirthday(DateFormatUtils.format(userConfig.getBirthday(), "yyyy-MM-dd"));
        userConfigVO.setAccountStatus(userConfig.getAccountStatus());
        userConfigVO.setAccountClass(userConfig.getAccountClass());
        userConfigVO.setAccountScore(userConfig.getAccountScore());
        userConfigVO.setRegistrationTime(DateFormatUtils.format(userConfig.getRegistrationTime(), "yyyy-MM-dd"));
        userConfigVO.setLastLoginTime(DateFormatUtils.format(userConfig.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss"));
        userConfigVO.setSignInDays(userConfig.getSignInDays());
        userConfigVO.setAvatarUrl(userConfig.getAvatarPath());
        return userConfigVO;
    }

}
