package com.hust.hello.api;

import com.hust.hello.api.param.UserRegisterParam;
import com.hust.hello.common.bean.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/16 11:18
 * @description:
 */
@RequestMapping(value = "/permission")
public interface PermissionApi {

    @RequestMapping(value = {"/getKaptcha"}, method = RequestMethod.GET)
    void getVerifyCode(HttpServletResponse response);

    @RequestMapping(value = "/isUserExist", method = RequestMethod.GET)
    Response isUserExist(@RequestParam(value = "userId") String userId);

    @RequestMapping(value = {"/isEmailExist"}, method = RequestMethod.GET)
    Response isEmailExist(@RequestParam(value = "email")String email);

    @RequestMapping(value = {"/getEmailVerifyCode"}, method = RequestMethod.GET)
    Response getEmailVerifyCode(@RequestParam(value = "email") String email);

    @RequestMapping(value = {"/retrievePassword"}, method = RequestMethod.GET)
    Response retrievePassword(@RequestParam(value = "email") String email,
                              @RequestParam(value = "newPassword") String newPassword,
                              @RequestParam(value = "verifyCode") String verifyCode);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    Response userLogin(@RequestParam(value = "userId")String userId,
                       @RequestParam(value = "password")String password);

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    Response<Boolean> userRegister(@RequestBody UserRegisterParam userRegisterParam);
}
