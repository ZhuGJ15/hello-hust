package com.hust.hello.common.model.vo;

import com.hust.hello.common.model.entity.HelloPostConfig;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/28 12:19
 * @description:
 */
@Data
public class UserPostConfigListVO {
    private String postId;

    private String barId;

    private String barName;

    private Integer postStatus;

    private Integer postType;

    private String postTitle;

    private String postText;

    private String createTime;

    private Integer popularity;

    private String lastEditTime;

    private Integer viewSum;

    private Integer likeSum;

    private Integer collectSum;

    private Integer dislikeSum;

    private Integer commentSum;

    private boolean hasLike;

    private boolean hasDislike;

    private boolean hasCollect;

    public static UserPostConfigListVO convertToVO(HelloPostConfig postConfig, String barName,
                                                   boolean hasLike, boolean hasDislike, boolean hasCollect) {
        UserPostConfigListVO userPostConfigListVO = new UserPostConfigListVO();
        userPostConfigListVO.setPostId(postConfig.getPostId());
        userPostConfigListVO.setBarId(postConfig.getBarId());
        userPostConfigListVO.setBarName(barName);
        userPostConfigListVO.setPostStatus(postConfig.getPostStatus());
        userPostConfigListVO.setPostType(postConfig.getPostType());
        userPostConfigListVO.setPostTitle(postConfig.getPostTitle());
        userPostConfigListVO.setPostText(postConfig.getPostText());
        userPostConfigListVO.setCreateTime(DateFormatUtils.format(postConfig.getCreateTime(), "yyyy-MM-dd HH:mm"));
        userPostConfigListVO.setPopularity(postConfig.getPopularity());
        userPostConfigListVO.setLastEditTime(DateFormatUtils.format(postConfig.getLastEditTime(), "yyyy-MM-dd HH:mm"));
        userPostConfigListVO.setViewSum(postConfig.getViewSum());
        userPostConfigListVO.setLikeSum(postConfig.getLikeSum());
        userPostConfigListVO.setCollectSum(postConfig.getCollectSum());
        userPostConfigListVO.setDislikeSum(postConfig.getDislikeSum());
        userPostConfigListVO.setCommentSum(postConfig.getCommentSum());
        userPostConfigListVO.setHasLike(hasLike);
        userPostConfigListVO.setHasDislike(hasDislike);
        userPostConfigListVO.setHasCollect(hasCollect);
        return userPostConfigListVO;
    }
}
