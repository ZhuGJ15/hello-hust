package com.hust.hello.service.interceptor;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import com.alibaba.fastjson.JSONObject;
import com.hust.hello.common.enums.ResCodeEnum;
import com.hust.hello.common.utils.HelloStringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.router.SaRouteFunction;
import cn.dev33.satoken.servlet.model.SaRequestForServlet;
import cn.dev33.satoken.servlet.model.SaResponseForServlet;
import cn.dev33.satoken.stp.StpUtil;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/24 20:07
 * @description: 基于路由的拦截式鉴权
 */
public class HelloRouteInterceptor implements HandlerInterceptor {

    private static final String LOGIN_STRING = "请登录账号";
    private static final String REPLACE_STRING = "该账号已被顶下线";
    private static final String KICKED_OFF_STRING = "该账号已被踢下线";

    /**
     * 默认为登录检验
     */
    public SaRouteFunction function = (req, res, handler) -> StpUtil.checkLogin();

    /**
     * 创建一个路由拦截器
     */
    public HelloRouteInterceptor() {
    }

    /**
     * 创建, 并指定[执行函数]
     * @param function [执行函数]
     */
    public HelloRouteInterceptor(SaRouteFunction function) {
        this.function = function;
    }

    /**
     * 静态方法快速构建一个
     * @param function 自定义模式下的执行函数
     * @return hello路由拦截器
     */
    public static HelloRouteInterceptor newInstance(SaRouteFunction function) {
        return new HelloRouteInterceptor(function);
    }

    // ----------------- 验证方法 -----------------

    /**
     * 每次请求之前触发的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            function.run(new SaRequestForServlet(request), new SaResponseForServlet(response), handler);
        } catch (NotLoginException nle){
            response.setContentType("application/json; charset=utf-8");
            JSONObject resJson;
            if("-1".equals(nle.getType()) || "-2".equals(nle.getType()) ||
                    "-3".equals(nle.getType())){
                resJson = HelloStringUtils.getResJsonString(ResCodeEnum.TOKEN_INVALID.getCode(), LOGIN_STRING, "");
            }else if("4".equals(nle.getType())){
                resJson = HelloStringUtils.getResJsonString(ResCodeEnum.TOKEN_REPLACE.getCode(), REPLACE_STRING, "");
            }else if("5".equals(nle.getType())){
                resJson = HelloStringUtils.getResJsonString(ResCodeEnum.TOKEN_KICKEDOFF.getCode(), KICKED_OFF_STRING, "");
            }else{
                resJson = HelloStringUtils.getResJsonString(ResCodeEnum.TOKEN_INVALID.getCode(), LOGIN_STRING, "");
            }
            response.getWriter().print(resJson.toJSONString());
            return false;
        } catch (StopMatchException e) {
            // 停止匹配，进入Controller
        } catch (BackResultException e) {
            // 停止匹配，向前端输出结果
            if(response.getContentType() == null) {
                response.setContentType("text/plain; charset=utf-8");
            }
            response.getWriter().print(e.getMessage());
            return false;
        }

        // 通过验证
        return true;
    }
}
