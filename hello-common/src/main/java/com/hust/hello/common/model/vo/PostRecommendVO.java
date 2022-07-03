package com.hust.hello.common.model.vo;

import com.hust.hello.common.model.entity.HelloPostConfig;
import com.hust.hello.common.model.entity.HelloUserConfig;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/27 16:17
 * @description:
 */
@Data
public class PostRecommendVO {
    private String postId;

    private String barId;

    private String barName;

    private Integer postType;

    private String postTitle;

    private String postText;

    private String creatorId;

    private String creatorName;

    private String creatorAvatarPath;

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

    public static PostRecommendVO convertToVO(HelloPostConfig postConfig, String barName, HelloUserConfig userConfig,
                                              boolean hasLike, boolean hasDislike, boolean hasCollect) {
        PostRecommendVO postRecommendVO = new PostRecommendVO();
        postRecommendVO.setPostId(postConfig.getPostId());
        postRecommendVO.setBarId(postConfig.getBarId());
        postRecommendVO.setBarName(barName);
        postRecommendVO.setPostType(postConfig.getPostType());
        postRecommendVO.setPostTitle(postConfig.getPostTitle());
        postRecommendVO.setPostText(postConfig.getPostText());
        postRecommendVO.setCreatorId(postConfig.getCreatorId());
        postRecommendVO.setCreatorName(userConfig.getUserName());
        postRecommendVO.setCreatorAvatarPath(userConfig.getAvatarPath());
        postRecommendVO.setCreateTime(DateFormatUtils.format(postConfig.getCreateTime(), "yyyy-MM-dd HH:mm"));
        postRecommendVO.setPopularity(postConfig.getPopularity());
        postRecommendVO.setLastEditTime(DateFormatUtils.format(postConfig.getLastEditTime(), "yyyy-MM-dd HH:mm"));
        postRecommendVO.setViewSum(postConfig.getViewSum());
        postRecommendVO.setLikeSum(postConfig.getLikeSum());
        postRecommendVO.setCollectSum(postConfig.getCollectSum());
        postRecommendVO.setDislikeSum(postConfig.getDislikeSum());
        postRecommendVO.setCommentSum(postConfig.getCommentSum());
        postRecommendVO.setHasLike(hasLike);
        postRecommendVO.setHasDislike(hasDislike);
        postRecommendVO.setHasCollect(hasCollect);

        return postRecommendVO;
    }
}
