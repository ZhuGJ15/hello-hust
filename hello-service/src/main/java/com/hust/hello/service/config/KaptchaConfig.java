package com.hust.hello.service.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/18 11:56
 * @description: Kaptcha验证码配置类
 */
@Configuration
public class KaptchaConfig {

    /**
     * 生成图片验证码
     * @return
     */
    @Bean
    public Producer generateKaptcha(){
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");    // 验证码图片边框
        properties.setProperty("kaptcha.border.color", "105,179,90");  //边框颜色

        properties.setProperty("kaptcha.image.width", "135");
        properties.setProperty("kaptcha.image.height", "50");

        properties.setProperty("kaptcha.textproducer.font.size", "43");  // 字体大小
        properties.setProperty("kaptcha.textproducer.font.color", "black");  // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.names", "Arial");  // 字体
        properties.setProperty("kaptcha.textproducer.char.length", "4");     // 验证码长度

        // 验证码字符范围
        properties.setProperty("kaptcha.textproducer.char.string",
                "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        // session key
        properties.setProperty("kaptcha.session.key", "code");
        //干扰线颜色
        properties.setProperty("kaptcha.noise.color", "red");
        Config config = new Config(properties);

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }

}
