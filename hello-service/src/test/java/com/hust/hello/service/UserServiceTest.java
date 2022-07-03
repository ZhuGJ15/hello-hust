package com.hust.hello.service;

import com.hust.hello.api.param.UserRegisterParam;
import com.hust.hello.common.utils.HelloTimeUtils;
import com.hust.hello.dao.mapper.customer.HelloUserConfigCustomMapper;
import com.hust.hello.service.controller.PermissionController;
import com.hust.hello.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/18 13:57
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceTest {

    @Autowired
    PermissionController userController;

    @Autowired
    HelloUserConfigCustomMapper userConfigMapper;

    @Autowired
    UserService userService;

    @Test
    public void test(){
        try{
            System.out.println("test. start ==============");
            // 用户登录测试
//            loginTest();
            // 账号注册测试
//            registerTest();
//            System.out.println(HelloTimeUtils.getConstellation(Date.from(Instant.now())));
            userService.doFollowUser("zhuganjun", "helloEveryone");
            System.out.println("test. end ================");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loginTest(){
//        HelloUserConfig user = userConfigMapper.getUserByUserId("zhuganjun");
//        System.out.println(UserConfigVO.userConfigConvertToVo(user));
        System.out.println(userController.userLogin("zhuganjun", "zhu123"));
        System.out.println(userController.userLogin("aaa", "zzz"));
        System.out.println(userController.userLogin("", "aaa"));
    }

    private void registerTest(){
        UserRegisterParam newUser = new UserRegisterParam();
        newUser.setUserId("wuhudasima");
        newUser.setUserName("芜湖大司马");
        newUser.setEmail("dasima@hust.edu.cn");
        newUser.setPassword("sm123456");
        newUser.setSex("1");
        newUser.setBirthday("1998-01-05");
        System.out.println(userController.userRegister(newUser));
    }
}
