package com.hust.hello.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/2/8 17:05
 * @description: 解决跨域问题（暂未启用）
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/post/uploadPic")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .maxAge(3600);
//    }
}
