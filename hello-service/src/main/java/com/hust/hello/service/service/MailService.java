package com.hust.hello.service.service;

import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.utils.HelloTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author: zhuganjun ©
 * @data: 2021/9/19 17:58
 * @version: 1.0
 * @description: 邮件发送服务
 */
@Service
@Slf4j
public class MailService {
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${email.from}")
    private String from;
    
    @Value("#{'${email.testAlertEmailTo}'.split(',')}")
    private List<String> alarmReceivers;
    
    @Value("${projectUrl.backend}")
    private String backendUrl;

    private static String verifyCodeText = "【Hello-Hust】 您好！您的邮箱验证码为 %verifyCode% 。";

    /**
     * 发送邮箱验证码邮件
     * @return
     */
    public boolean sendVerifyEmail(String emailTo, String verifyCode){
        try{
            log.info("MailServive. sendVerifyEmail. param: emailTo:{}, verifyCode:{}", emailTo, verifyCode);
            String emailText = verifyCodeText.replaceAll("%verifyCode%", verifyCode);
            String[] recivers = new String[1];
            recivers[0] = emailTo;
            sendEmail(from, recivers, "Hello论坛邮箱验证码", emailText, false);
            log.info("MailServive. sendVerifyEmail success. ");
        }catch (MessagingException me){
            log.error("MailServive. sendVerifyEmail fail. ", me);
            throw BusinessExceptionBuilder.createEmailException("邮箱验证码发送失败");
        }
        return true;
    }

    /**
     * 发送报警信息给管理员
     * @param alarmReason 报警原因
     * @param monitorIndicators 正常指标
     * @param currentIndicators 当前指标
     * @param remark 备注信息
     * @return
     */
    public void sendAlarmEmail(String alarmReason, String monitorIndicators, String currentIndicators,
                                  String remark){
        // 配置文件中收件人不能为空
        if(null == alarmReceivers){
            //log.error("sendAlarmEmail ERROR. 'mail.testAlertEmailTo' can not be empty.");
            throw BusinessExceptionBuilder.createEmailException("报警邮件收件人为空. ");
        }
        // 收件人
        String[] receivers = alarmReceivers.toArray(new String[0]);
        // 读取邮件模板，组装邮件内容
        String template;
        String emailText;
        try{
            template = getTemplate("AlarmEmailTemplate");
            emailText = template.replaceAll("%alarmReason%", alarmReason)
                    .replaceAll("%monitorIndicators%", monitorIndicators)
                    .replaceAll("%currentIndicators%", currentIndicators)
                    .replaceAll("%alarmTime%", HelloTimeUtils.getTimeString())
                    .replaceAll("%remark%", remark)
                    .replaceAll("%backendUrl%", backendUrl);
        }catch (IOException e){
            log.error("sendAlarmEmail ERROR. GetAlarmEmailTemplate fail.", e);
            throw BusinessExceptionBuilder.createEmailException("报警邮件模板获取失败");
        }
        // 发送邮件
        try{
            sendEmail(from, receivers, "Hello-Hust系统报警", emailText, true);
            log.info("sendAlarmEmail success.");
        } catch (MessagingException e) {
            log.error("sendAlarmEmail ERROR. send fail", e);
            throw BusinessExceptionBuilder.createEmailException("报警邮件发送失败");
        }
    }

    /**
     * 发送邮件
     * @param emailFrom 发件人
     * @param receivers 收件人列表
     * @param subject 标题
     * @param text 邮件内容
     */
    private void sendEmail(String emailFrom, String[] receivers, String subject,
                           String text, Boolean isHtml) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper msgHelper = new MimeMessageHelper(message);
        msgHelper.setFrom(emailFrom);
        msgHelper.setTo(receivers);
        msgHelper.setSubject(subject);
        msgHelper.setText(text, isHtml);
        mailSender.send(msgHelper.getMimeMessage());
    }

    /**
     * 获取邮件模板内容
     * @param templateName 模板名称
     * @return 模板内容的字符串
     * @throws IOException
     */
    private String getTemplate(String templateName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:mailTemplates/" + templateName + ".html");
        InputStream in = resource.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder data = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            data.append(line).append("\n");
        }
        bufferedReader.close();
        reader.close();
        in.close();
        return data.toString();
    }
}
