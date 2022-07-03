package com.hust.hello.service;

import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.common.utils.HelloStringUtils;
import com.hust.hello.dao.mapper.customer.HelloUserConfigCustomMapper;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/11/14 22:42
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class JavaTest {

    @Autowired
    HelloUserConfigCustomMapper userConfigMapper;

    @Test
    public void test(){
        System.out.println("test:start ===========");
        try{
            long l = System.currentTimeMillis();
            System.out.println(String.valueOf(l));
            // 日期测试
//            dateTest();
            // 正则表达式，字符串测试
//            strTest();
            // 随机码生成测试
//            System.out.println(HelloStringUtils.generateRandomCode(true, false, true, 4));
//            System.out.println(HelloStringUtils.generateRandomCode(true, true, true, 5));
//            System.out.println(HelloStringUtils.generateRandomCode(true, true, false, 7));
//            String classPath = "com.hello.hust.controller.UserController";
//            String[] path = StringUtils.split(classPath, '.');
//
//            System.out.println(path[path.length - 1]);
//            System.out.println(UUID.randomUUID());

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("test:end   ===========");
    }

    private void solution(){
        Map<String, Integer> test = new HashMap<>();
        test.put("hello", 1);
        test.put("hello", 2);
        for(String s : test.keySet()){
            s.startsWith("he");
        }
        System.out.println(test.get("hello"));
        int[] tmp = new int[5];
        int len = tmp.length;
    }

    private void dateTest() throws ParseException {
//        HelloUserConfig user = userConfigMapper.getUserByUserId("zhuganjun");
//        Date lastLoginTime = user.getLastLoginTime();
//        Date now = Date.from(Instant.now());
//        long res = now.getTime() - lastLoginTime.getTime();
//        System.out.println(lastLoginTime);
//        System.out.println(now);
//        System.out.println(res / 3600000);

//        String[] date = {
//                "2019-01-01",
//                "2020-01-33",
//        };
        String DATE = "yyyy-MM-dd";
//        for(int i = 0; i < date.length; i++){
//            System.out.println(DateUtils.parseDate(date[i], DATE));
//        }

        String dateStr1 = "2022-01-19";
        String dateStr2 = "2022-01-20";
        Date date1 = DateUtils.parseDate(dateStr1, DATE);
        Date date2 = DateUtils.parseDate(dateStr2, DATE);
        Date now = Date.from(Instant.now());
        System.out.println(DateUtils.isSameDay(date1, date2));
        System.out.println(DateUtils.isSameDay(date1, now));
        System.out.println(DateUtils.isSameDay(date2, now));
    }

    private void strTest(){
        String[] str = {
                "zhuGADjun",
                "GANzhu",
        };

        String pattern = "[a-z]*[A-Z]*";

        Pattern r = Pattern.compile(pattern);
        for(int i = 0; i < str.length; i++){
            Matcher m = r.matcher(str[i]);
            System.out.println(m.matches());
        }
    }
}
