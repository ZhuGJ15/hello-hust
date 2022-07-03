package com.hust.hello.service.service;

import cn.dev33.satoken.stp.StpUtil;
import com.google.code.kaptcha.Producer;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.common.model.vo.UserConfigVO;
import com.hust.hello.dao.mapper.customer.HelloUserConfigCustomMapper;
import com.hust.hello.service.remote.BosService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.Date;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/16 21:01
 * @description:
 */
@Service
@Slf4j
public class PermissionService {

    @Autowired
    Producer kaptchaProducer;

    @Autowired
    MailService mailService;

    @Autowired
    HelloUserConfigCustomMapper userConfigMapper;

    @Autowired
    BosService bosService;
    
    /**
     * 生成图片验证码
     */
    public BufferedImage getKaptcha(HttpServletResponse response){
        // 获得
        String text = kaptchaProducer.createText();
        response.addHeader("hello-code", text);
        log.info("PermissionService. generateKaptcha. text:{}", text);
        BufferedImage image = kaptchaProducer.createImage(text);
        return image;
    }

    /**
     * 用户名是否已存在
     * @param userId 用户名
     * @return true-已存在; false-不存在
     */
    public Boolean isUserExist(String userId){
        String userName = userConfigMapper.getUserNameByUserId(userId);
        return !StringUtils.isBlank(userName);
    }

    /**
     * 邮箱是否已注册
     * @param email
     * @return
     */
    public Boolean isEmailExist(String email){
        String userId = userConfigMapper.getUserIdByEmail(email);
        return !StringUtils.isBlank(userId);
    }

    public Boolean updatePassword(String email, String password){
        HelloUserConfig userConfig = userConfigMapper.getUserByEmail(email);
        if(null == userConfig){
            throw BusinessExceptionBuilder.createParamException("该邮箱还未注册");
        }
        if(0 != userConfig.getAccountStatus()){
            throw BusinessExceptionBuilder.createParamException("账号状态异常.请联系管理员.");
        }
        if(StringUtils.equals(password, userConfig.getPassword())){
            throw BusinessExceptionBuilder.createParamException("新密码不能与原密码相同");
        }

        HelloUserConfig newConfig = new HelloUserConfig();
        newConfig.setId(userConfig.getId());
        newConfig.setPassword(password);
        userConfigMapper.updateByPrimaryKeySelective(newConfig);
        return true;
    }
    
    /**
     * 用户登录账号
     * @return 返回前端展示的用户数据
     */
    public UserConfigVO doLogin(String userId, String password){
        HelloUserConfig userConfig = userConfigMapper.getUserByUserId(userId);
        if(null == userConfig){
            throw BusinessExceptionBuilder.userLoginException("该用户不存在.");
        }
        if(!StringUtils.equals(password, userConfig.getPassword())){
            throw BusinessExceptionBuilder.userLoginException("密码错误.");
        }
        if(0 != userConfig.getAccountStatus()){
            throw BusinessExceptionBuilder.userLoginException("账号异常，无法登录. 请联系管理员");
        }
        // 登录成功
        HelloUserConfig userConfigUpdate = new HelloUserConfig();
        userConfigUpdate.setId(userConfig.getId());
        Date now = Date.from(Instant.now());
        // 更新账号登录天数
        if(!DateUtils.isSameDay(userConfig.getLastLoginTime(), now)){
            userConfigUpdate.setSignInDays(userConfig.getSignInDays() + 1);
            userConfig.setSignInDays(userConfig.getSignInDays() + 1);
        }
        // 更新用户登录时间
        userConfigUpdate.setLastLoginTime(now);
        // 判断用户是否有头像
        if(StringUtils.isBlank(userConfig.getAvatarPath())){
            log.info("user has not create avatar info in database. userId:{}", userId);
            // 先上传默认头像，并创建用户的文件路径
            String avatarPath = bosService.createFolder(userConfig.getUserId(), userConfig.getSex());
            // 更新用户信息
            userConfig.setAvatarPath(avatarPath);
            userConfigUpdate.setAvatarPath(avatarPath);
        }
        userConfigMapper.updateByPrimaryKeySelective(userConfigUpdate);
        // 登录成功，发放token
        StpUtil.login(userId);
        // todo 登录成功，后面可以将这个操作存入数据库中
        log.info("PermissionService success. 登录成功。token info:{}", StpUtil.getTokenInfo().toString());
        return UserConfigVO.userConfigConvertToVo(userConfig);
    }

    /**
     * 注册账号
     * @param newUser
     */
    public void doRegister(HelloUserConfig newUser){
        if (!StringUtils.isBlank(userConfigMapper.getUserNameByUserId(newUser.getUserId()))){
            throw BusinessExceptionBuilder.createParamException("用户名已存在");
        }
        if (!StringUtils.isBlank(userConfigMapper.getUserIdByEmail(newUser.getEmail()))) {
            throw BusinessExceptionBuilder.createParamException("该邮箱已注册");
        }
        // 根据用户性别，上传默认头像，并在bos中为新用户创建文件夹
        String avatarPath = bosService.createFolder(newUser.getUserId(), newUser.getSex());

        newUser.setAvatarPath(avatarPath);
        newUser.setStudentId("");
        newUser.setAccountStatus((byte) 0);
        newUser.setAccountClass(0);
        newUser.setAccountScore(0);
        newUser.setSignInDays(1);
        newUser.setRegistrationTime(Date.from(Instant.now()));
        newUser.setLastLoginTime(Date.from(Instant.now()));
        newUser.setFollowingBarsum(0);
        newUser.setPostSum(0);
        newUser.setFollowerSum(0);
        newUser.setFollowingUsersum(0);
        newUser.setReportedSum(0);
        newUser.setVersion(0);
        userConfigMapper.insert(newUser);
    }
}
