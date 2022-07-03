package com.hust.hello.common.model.vo;

import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.common.utils.HelloTimeUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/8 13:28
 * @description:
 */
@Data
public class UserConfigDisplayedVO {
    private String userId;

    private String userName;

    private String avatarPath;

    private Integer accountStatus;

    private Integer accountClass;

    private String lastLoginTime;

    private Integer signInDays;

    private Integer postSum;

    private Integer followerSum;

    private Integer sex;

    private String constellation;

    private boolean isOnline = false;

    private boolean isFollowing = false;

    public static UserConfigDisplayedVO convertToDisplayedVO(HelloUserConfig userConfig, boolean isOnline, boolean isFollowing){
        UserConfigDisplayedVO userConfigDisplayedVO = new UserConfigDisplayedVO();
        userConfigDisplayedVO.setUserId(userConfig.getUserId());
        userConfigDisplayedVO.setUserName(userConfig.getUserName());
        userConfigDisplayedVO.setAvatarPath(userConfig.getAvatarPath());
        userConfigDisplayedVO.setAccountStatus((int)userConfig.getAccountStatus());
        userConfigDisplayedVO.setAccountClass(userConfig.getAccountClass());
        userConfigDisplayedVO.setOnline(isOnline);
        userConfigDisplayedVO.setLastLoginTime(DateFormatUtils.format(userConfig.getLastLoginTime(), "yyyy-MM-dd"));
        userConfigDisplayedVO.setSignInDays(userConfig.getSignInDays());
        userConfigDisplayedVO.setPostSum(userConfig.getPostSum());
        userConfigDisplayedVO.setFollowerSum(userConfig.getFollowerSum());
        userConfigDisplayedVO.setSex(userConfig.getShowSex() == 1 ? (int)userConfig.getSex() : -1);
        if (userConfig.getShowConstellation() == 0) {
            userConfigDisplayedVO.setConstellation("");
        } else {
            userConfigDisplayedVO.setConstellation(HelloTimeUtils.getConstellation(userConfig.getBirthday()));
        }
        userConfigDisplayedVO.setFollowing(isFollowing);
        return userConfigDisplayedVO;
    }
}
