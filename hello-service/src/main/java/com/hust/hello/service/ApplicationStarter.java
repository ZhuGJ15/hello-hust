package com.hust.hello.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/10/2 22:34
 * @description:
 */

@SpringBootApplication
@MapperScan(value = "com.hust.hello.dao")
@ComponentScan(basePackages = {"com.hust.hello"})
@EnableTransactionManagement
public class ApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
