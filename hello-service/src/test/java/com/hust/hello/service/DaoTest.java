package com.hust.hello.service;

import com.hust.hello.common.model.entity.HelloBarConfig;
import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.common.model.vo.PostHomePageVO;
import com.hust.hello.dao.mapper.customer.HelloBarConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloPostConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloUserConfigCustomMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/19 14:36
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DaoTest {
    @Autowired
    HelloUserConfigCustomMapper userConfigMapper;

    @Autowired
    HelloBarConfigCustomMapper barConfigMapper;

    @Autowired
    HelloPostConfigCustomMapper postConfigMapper;

    @Test
    public void test(){
        try{
            System.out.println("test. start ==============");
//            List<HelloBarConfig> normalBarConfigList = barConfigMapper.getNormalBarConfigList();
//            for(HelloBarConfig barConfig : normalBarConfigList){
//                System.out.println(barConfig);
//                System.out.println(barConfig.getBarName());
//            }
//            List<HelloBarConfig> allBarList = barConfigMapper.getAllBarList(1, 10);
//            System.out.println(allBarList);
//            System.out.println(postConfigMapper.getHomePagePostList("bar_admin_001", 3));
//            System.out.println(postConfigMapper.getPostIdAndName("bar_admin_001"));
            System.out.println(barConfigMapper.getAllBarList(2, 2));
            // mysql和java中的date格式
//            dateFormatTest();
            System.out.println("test. end ================");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void dateFormatTest(){
        HelloUserConfig user = userConfigMapper.getUserByUserId("zhuganjun");
        System.out.println(user.getBirthday());
        System.out.println(user.getLastLoginTime());
        System.out.println(user.getRegistrationTime());
    }
}
