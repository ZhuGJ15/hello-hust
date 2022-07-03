package com.hust.hello.service;

import com.hust.hello.common.utils.HelloStringUtils;
import com.hust.hello.service.remote.BosService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/9 10:57
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BosServiceTest {
    @Autowired
    BosService bosService;

    @Test
    public void test(){
        log.info(HelloStringUtils.LOG_START);
        try{
//            uploadFile();
//            isExist("user_file");
//            isExist("user_file/");
//            isExist("user_file/hello_default/boy_1");
//            isExist("user_file/hello_default/boy_1.png");
            InputStream inputStream = this.getClass().getResourceAsStream("/image/boy/delete_avatar.png");
            String key = "sys_file/Default/deleted_avatar.png";
            bosService.upload(inputStream, key);
            String url = bosService.getDownloadUrl(key, -1);
            System.out.println(url);
        } catch (Exception e){
            log.error("Exception: ", e);
        } finally {
            log.info(HelloStringUtils.LOG_END);
        }
//        System.out.println(this.getClass().getClassLoader().getResource("image/boy/boy_1.png"));
//        InputStream resourceAsStream = this.getClass().getResourceAsStream("/image/boy/boy_1.png");
    }

    private void uploadFile(){
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/image/boy/boy_1.png");
        bosService.upload(resourceAsStream, HelloStringUtils.DEFAULT_PIC_PATH + HelloStringUtils.DEFAULT_USERID + "boy_1.png");
    }

    private void isExist(String key){
        System.out.println(bosService.isExist(key));
    }

}
