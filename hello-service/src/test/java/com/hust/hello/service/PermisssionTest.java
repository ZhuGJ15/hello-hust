package com.hust.hello.service;

import com.hust.hello.api.PermissionApi;
import com.hust.hello.service.service.BarService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/16 11:34
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PermisssionTest {

    @Autowired
    PermissionApi permissionApi;

    @Autowired
    BarService barService;

    @Test
    public void test(){
        try{
            System.out.println("test. start ==================");
//            System.out.println(permissionApi.isUserExist("admin_zgj"));
//            System.out.println(permissionApi.isEmailExist("admin@hust.edu.cn"));
//            System.out.println(permissionApi.isEmailExist("ad@hust.edu.cn"));
//            System.out.println(permissionApi.retrievePassword("admin@hust.edu.cn", "hello1234", "2222"));
//            System.out.println("test. end ====================");
            System.out.println(barService.getHotBars(10));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
