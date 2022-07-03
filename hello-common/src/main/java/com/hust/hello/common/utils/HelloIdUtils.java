package com.hust.hello.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author: zhuganjun ©
 * @date: 2021/9/25 13:18
 * @version: 1.0
 * @description: 生成UUID，确保id的唯一性
 */
@Slf4j
public class HelloIdUtils {

    private static final String RECORD_ID_PREFIX = "record_";

    private static final String COMMENT_ID_PREFIX = "comment_";

    /**
     * 创建用户id
     * @param stuId 学号
     * @return 用户uuid
     */
    public static String createUserId(String stuId){
        String userId;
        // 如果学号为空，则为校外人员或游客
        if(StringUtils.isBlank(stuId)){
            userId = "VISITOR_" + UUID.randomUUID();
        } else {
            userId = "STU_" + stuId + "_" + UUID.randomUUID();
        }
        return userId;
    }

    /**
     * 创建文件id
     * @param fileName 文件原名
     * @param creatorId 文件上传者id
     * @return 保存的文件id
     */
    public static String createFileId(String fileName, String creatorId){
        String fileId = creatorId + "_" + fileName + "_" + UUID.randomUUID();
        return fileId;
    }

    /**
     * 生成操作记录ID
     */
    public static String generateOperationRecordId(String userId, Integer operationTypeCode) {
        return RECORD_ID_PREFIX + userId + "_" + operationTypeCode + "_" + System.currentTimeMillis();
    }

    /**
     * 生成评论ID
     */
    public static String generateCommentId(String postId, String userId) {
        return COMMENT_ID_PREFIX + postId + "_" + userId + "_" + System.currentTimeMillis();
    }
}
