package com.hust.hello.service;

import com.hust.hello.service.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: zhuganjun ©
 * @data: 2021/9/19 18:45
 * @version:
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = )
@Slf4j
public class MailServiceTest {
    
    @Autowired
    MailService mailService;
    
    @Test
    public void test(){
        System.out.println("test:start ===========");
        try{
//            test_getAlarmToFromConfig();
            sendVerifyCodeEmail();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("test:end   ===========");
    }
    
    private void test_getAlarmToFromConfig(){
        mailService.sendAlarmEmail("开发测试", "10", "15", "这是一个测试");
    }

    private void sendVerifyCodeEmail(){
        mailService.sendVerifyEmail("zgj98@foxmail.com", "super zgj");
    }
}
