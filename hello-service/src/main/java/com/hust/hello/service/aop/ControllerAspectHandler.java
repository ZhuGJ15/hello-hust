package com.hust.hello.service.aop;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.hust.hello.common.bean.Response;
import com.hust.hello.common.enums.ResCodeEnum;
import com.hust.hello.common.utils.HelloStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/3 13:36
 * @description: 自定义Controller层日志输出
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class ControllerAspectHandler {

    @Pointcut("@annotation(com.hust.hello.service.annotation.ControllerLog)")
    private void pointCut(){
    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String[] packages = StringUtils.split(signature.getDeclaringTypeName(), '.');
        String className = packages[packages.length - 1];
        String apiName = signature.getName();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String method = request.getMethod();

        String params = "";
        if(StringUtils.equals(method, "GET")){
            params = JSON.toJSONString(request.getParameterMap());
        }else {
            params = JSON.toJSONString(joinPoint.getArgs());
        }

        String userId = "";
        if(!StringUtils.equals(className, "PermissionController")){
            userId = StpUtil.getLoginIdAsString();
        }
        String ip = request.getRemoteAddr();        // 获取ip地址

        log.info(HelloStringUtils.LOG_START);
        log.info("{}. {}. called. params: {}", className, apiName, params);
        log.info("userId: {}.  ip: {}", StringUtils.isBlank(userId) ? "null" : userId, ip);
    }

    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void doAfter(JoinPoint joinPoint, Response result){
        Signature signature = joinPoint.getSignature();
        String[] packages = StringUtils.split(signature.getDeclaringTypeName(), '.');
        String className = packages[packages.length - 1];
        String apiName = signature.getName();

        if(ResCodeEnum.getEnum(result.getCode()) != ResCodeEnum.SUCCESS){
            log.info("{}. {}. failed. error: code:{}  msg:{}",
                    className, apiName, result.getCode(), result.getMsg());
        }else{
            log.info("{}. {}. success. return: {}", className, apiName, result.getResult());
        }
        log.info(HelloStringUtils.LOG_END);
    }
}
