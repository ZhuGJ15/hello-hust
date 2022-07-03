package com.hust.hello.service.controller;

import com.hust.hello.api.PermissionApi;
import com.hust.hello.api.param.UserRegisterParam;
import com.hust.hello.common.bean.Response;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.builder.ResponseBuilder;
import com.hust.hello.common.exception.BusinessException;
import com.hust.hello.common.model.vo.UserConfigVO;
import com.hust.hello.common.utils.HelloStringUtils;
import com.hust.hello.service.annotation.ControllerLog;
import com.hust.hello.service.service.MailService;
import com.hust.hello.service.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/16 11:30
 * @description:
 */
@RestController
@Slf4j
public class PermissionController implements PermissionApi {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MailService mailService;

    @Override
    @ControllerLog
    public void getVerifyCode(HttpServletResponse response) {
        try{
            BufferedImage image = permissionService.getKaptcha(response);
            response.setContentType("image/png");
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        }catch (Exception e){
            log.error("PermissionController. getVerifyCode. fail.", e);
        }
        log.info(HelloStringUtils.LOG_END);
    }

    @Override
    @ControllerLog
    public Response isUserExist(String userId) {
        try{
            if(permissionService.isUserExist(userId)){
                return ResponseBuilder.createSuccess(true);
            }
            return ResponseBuilder.createSuccess(false);
        }catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response isEmailExist(String email) {
        try{
            if(permissionService.isEmailExist(email)){
                return ResponseBuilder.createSuccess(true);
            }
            return ResponseBuilder.createSuccess(false);
        } catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response getEmailVerifyCode(String email) {
        try{
            HelloStringUtils.isEmailLegal(email);
            String code = HelloStringUtils.generateRandomCode(true,false,true,4);
            mailService.sendVerifyEmail(email, code);
            log.info("send verifyCode:{} to email:{}", code, email);
            return ResponseBuilder.createSuccess(true);
        }catch (BusinessException be){
            return ResponseBuilder.createSendEmailExceptionRes();
        } catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response retrievePassword(String email, String password, String verifyCode) {
        try{
            HelloStringUtils.isEmailLegal(email);
            if(!HelloStringUtils.isPasswordLegal(password)){
                throw BusinessExceptionBuilder.createParamException("密码格式错误.");
            }
            // todo 完善邮箱验证码
            if(!StringUtils.equals(verifyCode, "2222")){
                throw BusinessExceptionBuilder.createParamException("验证码错误.");
            }
            permissionService.updatePassword(email, password);
            return ResponseBuilder.createSuccess(true);
        }catch (BusinessException be){
            return ResponseBuilder.createParamExceptionRes(be.getMsg());
        }catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response userLogin(String userId, String password) {
        try{
            if(StringUtils.isBlank(userId) || StringUtils.isBlank(password)){
                throw BusinessExceptionBuilder.createParamException("用户名密码不能为空");
            }
            UserConfigVO userConfigVO = permissionService.doLogin(userId, password);
            return ResponseBuilder.createSuccess(userConfigVO);
        }catch (BusinessException be){
            return ResponseBuilder.createLoginExceptionRes(be.getMsg());
        }catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }

    @Override
    @ControllerLog
    public Response userRegister(UserRegisterParam userRegisterParam) {
        try{
            UserRegisterParam.paramCheck(userRegisterParam);
            permissionService.doRegister(UserRegisterParam.paramConvert(userRegisterParam));
            return ResponseBuilder.createSuccess(true);
        }catch (BusinessException be){
            return ResponseBuilder.createLoginExceptionRes(be.getMsg());
        }catch (Exception e){
            log.error("Exception: ", e);
            return ResponseBuilder.createSysExceptionRes();
        }
    }
}
