package com.hust.hello.common.model.vo;

import com.hust.hello.common.model.dto.CommentConfigDTO;
import com.hust.hello.common.model.entity.HelloCommentConfig;
import com.hust.hello.common.model.entity.HelloUserConfig;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/15 11:03
 * @description:
 */
@Data
public class CommentConfigVO {

    private String commentId;

    private String postId;

    private String objectId;

    private String creatorId;

    private String creatorName;

    private String creatorAvatarPath;

    private String commentContent;

    private String createTime;

    private String lastEditTime;

    private Integer likeSum;

    private Integer dislikeSum;

    private boolean hasLike;

    private boolean hasDislike;

    public static CommentConfigVO convertToVO(CommentConfigDTO commentConfigDTO, HelloUserConfig creatorConfig,
                                              boolean hasLike, boolean hasDislike) {
        CommentConfigVO commentConfigVO = new CommentConfigVO();
        commentConfigVO.setCommentId(commentConfigDTO.getCommentId());
        commentConfigVO.setPostId(commentConfigDTO.getPostId());
        commentConfigVO.setObjectId(commentConfigDTO.getObjectId());
        commentConfigVO.setCreatorId(commentConfigDTO.getCreatorId());
        commentConfigVO.setCommentContent(commentConfigDTO.getCommentContent());
        commentConfigVO.setCreateTime(DateFormatUtils.format(commentConfigDTO.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        commentConfigVO.setLastEditTime(DateFormatUtils.format(commentConfigDTO.getLastEditTime(), "yyyy-MM-dd HH:mm:ss"));
        commentConfigVO.setLikeSum(commentConfigDTO.getLikeSum());
        commentConfigVO.setDislikeSum(commentConfigDTO.getDislikeSum());
        commentConfigVO.setHasLike(hasLike);
        commentConfigVO.setHasDislike(hasDislike);
        commentConfigVO.setCreatorAvatarPath(creatorConfig.getAvatarPath());
        commentConfigVO.setCreatorName(creatorConfig.getUserName());
        return commentConfigVO;
    }

    public static CommentConfigVO convertToVO(HelloCommentConfig commentConfig, HelloUserConfig creatorConfig,
                                              boolean hasLike, boolean hasDislike) {
        CommentConfigVO commentConfigVO = new CommentConfigVO();
        commentConfigVO.setCommentId(commentConfig.getCommentId());
        commentConfigVO.setPostId(commentConfig.getPostId());
        commentConfigVO.setObjectId(commentConfig.getObjectId());
        commentConfigVO.setCreatorId(commentConfig.getCreatorId());
        commentConfigVO.setCommentContent(commentConfig.getCommentContent());
        commentConfigVO.setCreateTime(DateFormatUtils.format(commentConfig.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        commentConfigVO.setLastEditTime(DateFormatUtils.format(commentConfig.getLastEditTime(), "yyyy-MM-dd HH:mm:ss"));
        commentConfigVO.setLikeSum(commentConfig.getLikeSum());
        commentConfigVO.setDislikeSum(commentConfig.getDislikeSum());
        commentConfigVO.setHasLike(hasLike);
        commentConfigVO.setHasDislike(hasDislike);

        commentConfigVO.setCreatorAvatarPath(creatorConfig.getAvatarPath());
        commentConfigVO.setCreatorName(creatorConfig.getUserName());
        return commentConfigVO;
    }
}
