package com.hust.hello.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.enums.FileTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/19 20:22
 * @description: 字符串处理工具类
 */
@Slf4j
public class HelloStringUtils {

    // 打印日志时的前缀
    public static final String LOG_START = "START >>>>>>>>>>>>>>>";
    public static final String LOG_END = "END <<<<<<<<<<<<<<<<<";

    // 头像名称长度、前缀
    private static final Integer AVATAR_LENGTH = 6;
    public static final String AVATAR_PREFIX_CUSTOM = "custom_";
    public static final String AVATAR_PREFIX_DEFAULT = "default_";

    public static final String UPPER_CASE_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXVZ";
    public static final String LOWER_CASE_LETTER = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMBER_LETTER = "0123456789";

    public static final String DEFAULT_PIC_PATH = "user_file/";
    public static final String DEFAULT_USERID = "hello_default/";
    public static final String DEFAULT_AVATAR_PATH = "user_file/hello_default/avatar/default_1.jpg";

    public static final String POST_ID_PREFIX = "post_";

    public static final String DELETED_ACCOUNT_AVATAR_URL = "sys_file/Default/deleted_avatar.png";

    public static final String DEFAULT_BOY_AVATAR_PREFIX = "https://hello-hust.gz.bcebos.com/sys_file/Default/avatar/boy/";
    public static final String DEFAULT_GIRL_AVATAR_PREFIX = "https://hello-hust.gz.bcebos.com/sys_file/Default/avatar/girl/";

    /**
     * generate a post id, when user create a new post
     */
    public static String generateNewPostId(String userId) {
        return POST_ID_PREFIX + userId + "_"+ System.currentTimeMillis();
    }

    public static String getUserFileStorePath(String userId){
        return DEFAULT_PIC_PATH + userId + "/";
    }

    /**
     * 生成文件对象的保存路径前缀
     */
    public static String generateFileObjectPath(FileTypeEnum fileTypeEnum, String userId){
        String filePath = getUserFileStorePath(userId) + fileTypeEnum.getPath();
        return filePath;
    }

    /**
     * generate file path in BOS, by userId, file type and file name
     */
    public static String getPicPath(String userId, FileTypeEnum fileType, String fileName){
        String filePath = DEFAULT_PIC_PATH + userId + "/" + fileType.getPath() + fileName;
        return filePath;
    }

    /**
     * 获取默认路径前缀
     */
    public static String generateDefaultPath(FileTypeEnum fileTypeEnum){
        String filePath = DEFAULT_PIC_PATH + DEFAULT_USERID + fileTypeEnum.getPath();
        return filePath;
    }

    /**
     * 判断用户名是否合法
     * 用户名只能包含大小写字母
     * @param userId
     * @return
     */
    public static boolean isUserIdLegal(String userId){
        int len = userId.length();
        if(len < 3 || len > 15){
            throw BusinessExceptionBuilder.createParamException("用户名长度不符合要求");
        }
        HashMap<String, Integer> countMap = countLetter(userId);
        if(countMap.get("numberCount") != 0 || countMap.get("otherLetterCount") != 0){
            throw BusinessExceptionBuilder.createParamException("用户名只能包含大小写字母");
        }
        return true;
    }

    /**
     * 判断账号密码是否合法
     * 密码至少包含数字和字母
     * @param password
     * @return
     */
    public static boolean isPasswordLegal(String password){
        int len = password.length();
        if(len < 8 || len > 20){
            throw BusinessExceptionBuilder.createParamException("密码长度不符合要求");
        }
        HashMap<String, Integer> countMap = countLetter(password);
        if(countMap.get("lowerCaseLetterCount") == 0 &&
                countMap.get("upperCaseLetterCount") == 0){
            throw BusinessExceptionBuilder.createParamException("密码中需要包含字母");
        }
        if(countMap.get("numberCount") == 0){
            throw BusinessExceptionBuilder.createParamException("密码中需要包含数字");
        }
        return true;
    }

    /**
     * 判断邮箱是否合法
     * 必须以 ‘@hust.edu.cn' 结尾
     * @param email
     * @return
     */
    public static boolean isEmailLegal(String email){
        if(!StringUtils.endsWith(email, "@hust.edu.cn")){
            throw BusinessExceptionBuilder.createParamException("邮箱格式错误");
        }
        return true;
    }

    /**
     * 字符类型计数
     * @param str
     * @return
     */
    private static HashMap<String, Integer> countLetter(String str){
        int lowerCaseLetterCount = 0;
        int upperCaseLetterCount = 0;
        int numberCount = 0;
        int otherLetterCount = 0;
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c >= 'a' && c <= 'z'){
                lowerCaseLetterCount++;
                continue;
            }
            if(c >= 'A' && c <= 'Z'){
                upperCaseLetterCount++;
                continue;
            }
            if(c >= '0' && c <= '9'){
                numberCount++;
                continue;
            }
            otherLetterCount++;
        }
        HashMap<String, Integer> res = new HashMap<>();
        res.put("lowerCaseLetterCount", lowerCaseLetterCount);
        res.put("upperCaseLetterCount", upperCaseLetterCount);
        res.put("numberCount", numberCount);
        res.put("otherLetterCount", otherLetterCount);
        return res;
    }

    /**
     * 生成随机验证码
     * @return
     */
    public static String generateRandomCode(Boolean hasUpperCaseLetter,
                   Boolean hasLowerCaseLetter, Boolean hasNumber, int length){
        Random random = new Random();
        char[] code = new char[length];
        int i = 0;
        while(i < length){
            if(hasUpperCaseLetter && i < length){
                code[i] = UPPER_CASE_LETTER.charAt(random.nextInt(26));
                i++;
            }
            if(hasLowerCaseLetter && i < length) {
                code[i] = LOWER_CASE_LETTER.charAt(random.nextInt(26));
                i++;
            }
            if(hasNumber && i < length) {
                code[i] = NUMBER_LETTER.charAt(random.nextInt(10));
                i++;
            }
        }
        return new String(code);
    }

    public static JSONObject getResJsonString(Integer code, String msg, Object result){
        Map<String, Object> res = new HashMap<>();
        res.put("code", code);
        res.put("msg", msg);
        res.put("result", result);
        return new JSONObject(res);
    }

    /**
     * 生成头像名称
     */
    public static String generateAvatarName(){
        String s = HelloStringUtils.generateRandomCode(true, true, false, AVATAR_LENGTH);
        return AVATAR_PREFIX_CUSTOM + s;
    }
}
