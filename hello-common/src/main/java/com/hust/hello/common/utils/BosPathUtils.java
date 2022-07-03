package com.hust.hello.common.utils;

import com.hust.hello.common.enums.FileTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/2 18:23
 * @description:
 */
@Slf4j
public class BosPathUtils {

    public static final String BAR_DEFAULT_AVATAR = "default_avatar.png";

    private static final String BAR_FILE_PREFIX = "bar_file/";

    public static final String DEFAULT_AVATAR = "default_avatar.png";

    public static final String POST_FILE_PREFIX = "post/";

    public static final String AVATAR_FILE_PREFIX = "avatar/";

    public static final String PIC_SUFFIX_PNG = ".png";

    public static String generateBarFileKey(String barId, FileTypeEnum fileTypeEnum, String fileName) {
        String filePath = BAR_FILE_PREFIX + barId + "/" + fileTypeEnum.getPath() + fileName;
        return filePath;
    }

    /**
     * 生成随机的用户贴文图片地址
     */
    public static String generatePostPicKey(String userId, String fileSuffix) {
        String picPath = HelloStringUtils.DEFAULT_PIC_PATH + userId + "/" + POST_FILE_PREFIX + UUID.randomUUID() + fileSuffix;
        return picPath;
    }

    /**
     * generate bos key of user avatar file
     */
    public static String generateUserAvatarKey(String userId) {
        return HelloStringUtils.DEFAULT_PIC_PATH + userId + "/" + AVATAR_FILE_PREFIX + UUID.randomUUID() + PIC_SUFFIX_PNG;
    }

}
