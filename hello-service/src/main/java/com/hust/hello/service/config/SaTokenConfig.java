package com.hust.hello.service.config;

import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.hust.hello.service.interceptor.HelloRouteInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/24 17:31
 * @description: 注册Sa-Token路由拦截器
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HelloRouteInterceptor((saRequest, saResponse, o) -> {
            // 根据路由划分不同模块，不同的鉴权规则
            SaRouter.match("/**", routerStaff -> StpUtil.checkLogin());
        }))
                .addPathPatterns("/**")
                .excludePathPatterns("/permission/**");
    }
}
