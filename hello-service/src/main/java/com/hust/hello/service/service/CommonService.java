package com.hust.hello.service.service;

import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.enums.AccountStatusEnum;
import com.hust.hello.common.enums.BarStatusEnum;
import com.hust.hello.common.enums.CommentStatusEnum;
import com.hust.hello.common.enums.PostStatusEnum;
import com.hust.hello.common.model.entity.*;
import com.hust.hello.dao.mapper.customer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/6 20:42
 * @description:
 */
@Service
@Slf4j
public class CommonService {

    @Autowired
    private HelloUserConfigCustomMapper userConfigMapper;

    @Autowired
    private HelloBarConfigCustomMapper barConfigMapper;

    @Autowired
    private HelloPostConfigCustomMapper postConfigMapper;

    @Autowired
    private HelloUserOperationCustomMapper userOperationMapper;

    @Autowired
    private HelloCommentConfigCustomMapper commentConfigMapper;

    /**
     * 校验用户账号是否正常（不包括禁言和临时封禁）
     */
    public HelloUserConfig userAccountStatusCheck(String userId) {
        HelloUserConfig userConfig = userConfigMapper.getUserByUserId(userId);
        if(null == userConfig) {
            throw BusinessExceptionBuilder.userLoginException("用户账号不存在");
        }
        AccountStatusEnum accountStatus = AccountStatusEnum.getEnum((int) userConfig.getAccountStatus());
        if(accountStatus == AccountStatusEnum.DELETED) {
            throw BusinessExceptionBuilder.userLoginException("用户账号已注销");
        }
        if(accountStatus == AccountStatusEnum.BANNED) {
            throw BusinessExceptionBuilder.userLoginException("用户账号已被封禁");
        }
        return userConfig;
    }

    /**
     * 校验贴吧状态是否正常
     */
    public HelloBarConfig barStatusCheck(String barId) {
        HelloBarConfig barConfig = barConfigMapper.getBarConfig(barId);
        if(null == barConfig) {
            throw BusinessExceptionBuilder.createBarException("该版面不存在");
        }
        BarStatusEnum barStatus = BarStatusEnum.getEnum(barConfig.getBarStatus());
        if(barStatus == BarStatusEnum.DELETED) {
            throw BusinessExceptionBuilder.createBarException("该版面已被删除");
        }
        if(barStatus == BarStatusEnum.BANNED) {
            throw BusinessExceptionBuilder.createBarException("该版面已被封禁");
        }
        if(barStatus == BarStatusEnum.TOBE_REVIEWED) {
            throw BusinessExceptionBuilder.createBarException("该版面正在审核中");
        }
        return barConfig;
    }

    /**
     * 检查贴文状态，如果正常，则返回
     */
    public HelloPostConfig postStatusCheck(String postId) {
        HelloPostConfig postConfig = postConfigMapper.getPostDeleteConfig(postId);
        if(null == postConfig) {
            throw BusinessExceptionBuilder.createPostException("贴子不存在");
        }
        PostStatusEnum postStatus = PostStatusEnum.getEnum(postConfig.getPostStatus());
        if(postStatus == PostStatusEnum.DELETED) {
            throw BusinessExceptionBuilder.createPostException("贴子已被删除");
        }
        if(postStatus == PostStatusEnum.BANNED) {
            throw BusinessExceptionBuilder.createPostException("贴子已被封禁");
        }
        if(postStatus == PostStatusEnum.REVIEWING) {
            throw BusinessExceptionBuilder.createPostException("贴子正在审核中");
        }
        return postConfig;
    }

    /**
     * 检查评论状态
     */
    public HelloCommentConfig commentStatusCheck(String commentId) {
        HelloCommentConfig commentConfig = commentConfigMapper.selectCommentByCommentId(commentId);
        if(null == commentConfig) {
            throw BusinessExceptionBuilder.createCommentException("评论不存在");
        }
        CommentStatusEnum commentStatus = CommentStatusEnum.getEnum(commentConfig.getCommentStatus());
        if(commentStatus == CommentStatusEnum.USER_DELETE) {
            throw BusinessExceptionBuilder.createCommentException("该评论已被删除");
        }
        if(commentStatus == CommentStatusEnum.ADMIN_DELETE) {
            throw BusinessExceptionBuilder.createCommentException("该评论已被管理员删除");
        }
        return commentConfig;
    }

    /**
     * 发布贴文的数据库事务操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForAddPost(HelloPostConfig postConfig, HelloUserConfig userConfig, HelloBarConfig barConfig) {
        postConfigMapper.insertSelective(postConfig);
        userConfigMapper.updateByPrimaryKeySelective(userConfig);
        barConfigMapper.updateByPrimaryKeySelective(barConfig);
    }

    /**
     * 更新贴文的数据库事务操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForUpdatePost(HelloPostConfig originalPostConfig,
                                      HelloPostConfig newPostConfig,
                                      HelloBarConfig originalBarConfig,
                                      HelloBarConfig newBarConfig) {
        postConfigMapper.updateByPrimaryKeySelective(originalPostConfig);
        postConfigMapper.insertSelective(newPostConfig);
        if(null != originalBarConfig) {
            barConfigMapper.updateByPrimaryKeySelective(originalBarConfig);
        }
        if(null != newBarConfig) {
            barConfigMapper.updateByPrimaryKeySelective(newBarConfig);
        }
    }

    /**
     * 关注用户的数据库事务操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForFollowUser(HelloUserConfig userConfig,
                                      HelloUserConfig followedUserConfig,
                                      HelloUserOperationRecord userOperationRecord){
        userConfigMapper.updateByPrimaryKeySelective(userConfig);
        userConfigMapper.updateByPrimaryKeySelective(followedUserConfig);
        userOperationMapper.insert(userOperationRecord);
    }

    /**
     * 取消关注用户的数据库事务操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForCancelFollowUser(HelloUserConfig userConfig,
                                            HelloUserConfig followedUserConfig,
                                            HelloUserOperationRecord userOperationRecord) {
        userConfigMapper.updateByPrimaryKeySelective(userConfig);
        userConfigMapper.updateByPrimaryKeySelective(followedUserConfig);
        userOperationMapper.updateByPrimaryKeySelective(userOperationRecord);
    }

    /**
     * 点赞、收藏、踩贴文的数据库更新操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForPostOperation(HelloPostConfig updatePostConfig, HelloUserOperationRecord userOperationRecord) {
        postConfigMapper.updateByPrimaryKeySelective(updatePostConfig);
        userOperationMapper.insert(userOperationRecord);
    }

    /**
     * 取消点赞、收藏、踩贴文等操作的数据库更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForCancelPostOperation(HelloPostConfig updatePostConfig, HelloUserOperationRecord updateRecord) {
        postConfigMapper.updateByPrimaryKeySelective(updatePostConfig);
        userOperationMapper.updateByPrimaryKeySelective(updateRecord);
    }

    /**
     * 发布评论，更新数据库
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForAddComment(HelloPostConfig updatePostConfig, HelloCommentConfig helloCommentConfig) {
        commentConfigMapper.insert(helloCommentConfig);
        postConfigMapper.updateByPrimaryKeySelective(updatePostConfig);
    }

    /**
     * 删除评论，更新数据库
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForDeleteComment(HelloPostConfig updatePostConfig, HelloCommentConfig updateCommentConfig) {
        commentConfigMapper.updateByPrimaryKeySelective(updateCommentConfig);
        postConfigMapper.updateByPrimaryKeySelective(updatePostConfig);
    }

    /**
     * 点赞评论，更新数据库事务操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForLikeComment(HelloCommentConfig updateCommentConfig, HelloUserOperationRecord operationRecord) {
        commentConfigMapper.updateByPrimaryKeySelective(updateCommentConfig);
        userOperationMapper.insert(operationRecord);
    }

    /**
     * 取消点赞评论，更新数据库事务操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDBForCancelLikeComment(HelloCommentConfig updateCommentConfig,
                                             HelloUserOperationRecord updateOperationRecord){
        commentConfigMapper.updateByPrimaryKeySelective(updateCommentConfig);
        userOperationMapper.updateByPrimaryKeySelective(updateOperationRecord);
    }
}
