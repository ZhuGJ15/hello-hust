package com.hust.hello.service.service;

import com.hust.hello.api.param.CommentCreateParam;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.enums.CommentStatusEnum;
import com.hust.hello.common.enums.UserOperationTypeEnum;
import com.hust.hello.common.model.dto.CommentConfigDTO;
import com.hust.hello.common.model.entity.HelloCommentConfig;
import com.hust.hello.common.model.entity.HelloPostConfig;
import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.common.model.entity.HelloUserOperationRecord;
import com.hust.hello.common.model.vo.CommentConfigVO;
import com.hust.hello.common.utils.HelloIdUtils;
import com.hust.hello.dao.mapper.customer.HelloCommentConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloUserConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloUserOperationCustomMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/14 21:39
 * @description:
 */
@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private HelloCommentConfigCustomMapper commentConfigMapper;

    @Autowired
    private HelloUserOperationCustomMapper userOperationMapper;

    @Autowired
    private HelloUserConfigCustomMapper userConfigMapper;

    /**
     * 添加评论
     */
    public void addComment(String userId, CommentCreateParam param) {
        HelloPostConfig postConfig = commonService.postStatusCheck(param.getPostId());

        HelloCommentConfig commentConfig = new HelloCommentConfig();
        commentConfig.setCommentId(HelloIdUtils.generateCommentId(param.getPostId(), userId));
        commentConfig.setPostId(param.getPostId());
        commentConfig.setObjectId(param.getObjectId());
        commentConfig.setCreatorId(userId);
        commentConfig.setCommentContent(param.getCommentContent());
        commentConfig.setCommentStatus(CommentStatusEnum.NORMAL.getCode());
        commentConfig.setCreateTime(Date.from(Instant.now()));
        commentConfig.setLastEditTime(Date.from(Instant.now()));
        commentConfig.setLikeSum(0);
        commentConfig.setDislikeSum(0);
        commentConfig.setVersion(0);

        HelloPostConfig updatePostConfig = new HelloPostConfig();
        updatePostConfig.setId(postConfig.getId());
        updatePostConfig.setCommentSum(postConfig.getCommentSum() + 1);

        commonService.updateDBForAddComment(updatePostConfig, commentConfig);
        // todo 这里可以加上一些热度操作
    }

    /**
     * 获取贴文列表
     */
    public Map<String, Object> getCommentList(String userId, String postId, Integer currentPage,
                                              Integer pageSize, Integer sortType) {
        // 这里检查贴文状态
        // 可以不检查，因为获取贴文内容的时候，必然已检查过
         HelloPostConfig postConfig = commonService.postStatusCheck(postId);

        List<CommentConfigDTO> commentConfigDTOList = null;
        // 根据排序类型，从数据库获取排好序的评论数据
        if(1 == sortType) {
            commentConfigDTOList = commentConfigMapper.selectCommentsOrderByTimeWithPostId(postId, currentPage, pageSize);
        } else {
            commentConfigDTOList = commentConfigMapper.selectCommentsOrderByLikeWithPostId(postId, currentPage, pageSize);
        }
        List<CommentConfigVO> commentConfigVOList = new ArrayList<>();
        for(CommentConfigDTO dto : commentConfigDTOList) {
            HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, dto.getCommentId(), UserOperationTypeEnum.LIKE_COMMENT.getCode());
            HelloUserOperationRecord dislikeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, dto.getCommentId(), UserOperationTypeEnum.DISLIKE_COMMENT.getCode());

            HelloUserConfig creatorConfig = userConfigMapper.getUserByUserId(dto.getCreatorId());
            commentConfigVOList.add(CommentConfigVO.convertToVO(dto, creatorConfig, (null != likeRecord), (null != dislikeRecord)));
        }

        Map<String, Object> res = new HashMap<>();
        res.put("commentSum", postConfig.getCommentSum());
        res.put("commentList", commentConfigVOList);
        return res;
    }

    /**
     * 获取某个贴文内容
     */
    public CommentConfigVO getCommentConfig(String userId, String commentId) {
        HelloCommentConfig commentConfig = commonService.commentStatusCheck(commentId);
        HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, commentId, UserOperationTypeEnum.LIKE_COMMENT.getCode());
        HelloUserOperationRecord disRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, commentId, UserOperationTypeEnum.DISLIKE_COMMENT.getCode());

        HelloUserConfig creatorConfig = userConfigMapper.getUserByUserId(commentConfig.getCreatorId());
        return CommentConfigVO.convertToVO(commentConfig, creatorConfig, (null != likeRecord), (null != disRecord));
    }

    /**
     * 删除评论
     */
    public void deleteCommentConfig(String userId, String commentId) {
        HelloCommentConfig commentConfig = commonService.commentStatusCheck(commentId);
        HelloPostConfig postConfig = commonService.postStatusCheck(commentConfig.getPostId());

        if(!StringUtils.equals(userId, commentConfig.getCreatorId())) {
            throw BusinessExceptionBuilder.createCommentException("无权删除该评论");
        }

        HelloCommentConfig updateCommentConfig = new HelloCommentConfig();
        updateCommentConfig.setId(commentConfig.getId());
        updateCommentConfig.setCommentStatus(CommentStatusEnum.USER_DELETE.getCode());

        HelloPostConfig updatePostConfig = new HelloPostConfig();
        updatePostConfig.setId(postConfig.getId());
        updatePostConfig.setCommentSum(postConfig.getCommentSum() - 1);

        commonService.updateDBForDeleteComment(updatePostConfig, updateCommentConfig);
    }

    /**
     * 点赞/取消点赞评论
     */
    public void likeComment(String userId, String commentId) {
        HelloCommentConfig commentConfig = commonService.commentStatusCheck(commentId);

        HelloUserOperationRecord dislikeCommentRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, commentId, UserOperationTypeEnum.DISLIKE_COMMENT.getCode());
        if(null != dislikeCommentRecord) {
            throw BusinessExceptionBuilder.createUserOperationException("已踩过该评论，请先取消踩评");
        }

        HelloUserOperationRecord likeCommentRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, commentId, UserOperationTypeEnum.LIKE_COMMENT.getCode());

        HelloCommentConfig updateCommentConfig = new HelloCommentConfig();
        updateCommentConfig.setId(commentConfig.getId());
        // 判断是否有点赞记录
        if(null == likeCommentRecord) {
            // 没有点赞记录，则新建点赞记录
            HelloUserOperationRecord newRecord = new HelloUserOperationRecord();
            newRecord.setRecordId(HelloIdUtils.generateOperationRecordId(userId, UserOperationTypeEnum.LIKE_COMMENT.getCode()));
            newRecord.setOperatorId(userId);
            newRecord.setObjectId(commentId);
            newRecord.setOperateTime(Date.from(Instant.now()));
            newRecord.setOperationType(UserOperationTypeEnum.LIKE_COMMENT.getCode());
            newRecord.setIsCancel(0);
            newRecord.setVersion(0);

            updateCommentConfig.setLikeSum(commentConfig.getLikeSum() + 1);

            commonService.updateDBForLikeComment(updateCommentConfig, newRecord);
            log.info("user(userId:{}) like comment(commentId:{}) success.", userId, commentId);
            // todo 点赞评论成功，这里可以做一些热度或用户活跃度操作
        } else {
            // 找到点赞记录，则取消点赞
            HelloUserOperationRecord updateOperationRecord = new HelloUserOperationRecord();
            updateOperationRecord.setId(likeCommentRecord.getId());
            updateOperationRecord.setCancelTime(Date.from(Instant.now()));
            updateOperationRecord.setIsCancel(1);

            updateCommentConfig.setLikeSum(commentConfig.getLikeSum() - 1);

            commonService.updateDBForCancelLikeComment(updateCommentConfig, updateOperationRecord);
            log.info("user(userId:{}) cancel like comment(commentId:{}) success.", userId, commentId);
            // todo 取消点赞成功
        }
    }

    /**
     * 踩/取消踩评论
     */
    public void dislikeComment(String userId, String commentId) {
        HelloCommentConfig commentConfig = commonService.commentStatusCheck(commentId);

        HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, commentId, UserOperationTypeEnum.LIKE_COMMENT.getCode());

        if(null != likeRecord) {
            throw BusinessExceptionBuilder.createUserOperationException("已点赞该评论,请先取消点赞");
        }

        HelloUserOperationRecord dislikeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, commentId, UserOperationTypeEnum.DISLIKE_COMMENT.getCode());

        HelloCommentConfig updateCommentConfig = new HelloCommentConfig();
        updateCommentConfig.setId(commentConfig.getId());

        // 判断是否有过踩评的记录
        if(null == dislikeRecord) {
            // 如果没有，则创建一条踩评记录
            HelloUserOperationRecord newRecord = new HelloUserOperationRecord();
            newRecord.setRecordId(HelloIdUtils.generateOperationRecordId(userId, UserOperationTypeEnum.DISLIKE_COMMENT.getCode()));
            newRecord.setOperatorId(userId);
            newRecord.setObjectId(commentId);
            newRecord.setOperateTime(Date.from(Instant.now()));
            newRecord.setOperationType(UserOperationTypeEnum.DISLIKE_COMMENT.getCode());
            newRecord.setIsCancel(0);
            newRecord.setVersion(0);

            updateCommentConfig.setDislikeSum(commentConfig.getDislikeSum() + 1);

            commonService.updateDBForLikeComment(updateCommentConfig, newRecord);
            log.info("user(userId:{}) dislike comment(commentId:{}) success.", userId, commentId);
            // todo 踩评成功,后续可以做其他操作
        } else {
            // 如果已有踩评记录，则取消踩评
            HelloUserOperationRecord updateOperationRecord = new HelloUserOperationRecord();
            updateOperationRecord.setId(dislikeRecord.getId());
            updateOperationRecord.setIsCancel(1);
            updateOperationRecord.setCancelTime(Date.from(Instant.now()));

            updateCommentConfig.setDislikeSum(commentConfig.getDislikeSum() - 1);

            commonService.updateDBForCancelLikeComment(updateCommentConfig, updateOperationRecord);
            log.info("user(userId:{}) cancel dislike comment(commentId:{}) success.", userId, commentId);
            // todo 取消踩评成功，后续可以做其他操作
        }
    }
}
