package com.hust.hello.service;

import com.hust.hello.dao.mapper.customer.HelloBarConfigCustomMapper;
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
 * @date: 2022/4/6 12:48
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BarServiceTest {

    @Autowired
    private HelloBarConfigCustomMapper barConfigMapper;

    @Test
    public void test() {
        System.out.println("START =====================");
        try{
            System.out.println(barConfigMapper.getBarNameList());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("END ========================");
        }
    }
}
