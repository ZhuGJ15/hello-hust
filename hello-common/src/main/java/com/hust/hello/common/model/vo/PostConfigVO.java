package com.hust.hello.common.model.vo;

import com.hust.hello.common.model.entity.HelloPostConfig;
import com.hust.hello.common.model.entity.HelloUserConfig;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/28 9:55
 * @description:
 */
@Data
public class PostConfigVO {
    private String postId;

    private String barId;

    private Integer postType;

    private String postTitle;

    private String postText;

    private String creatorId;

    private String creatorAvatarPath;

    private String createTime;

    private Integer popularity;

    private Integer viewSum;

    private Integer likeSum;

    private Integer collectSum;

    private Integer dislikeSum;

    private boolean hasLike;

    private boolean hasCollect;

    private boolean hasDislike;

    public static PostConfigVO convertToVO(HelloPostConfig postConfig, HelloUserConfig creatorConfig,
                                           boolean hasLike, boolean hasDislike, boolean hasCollect) {
        PostConfigVO postConfigVO = new PostConfigVO();
        postConfigVO.setPostId(postConfig.getPostId());
        postConfigVO.setBarId(postConfig.getBarId());
        postConfigVO.setPostType(postConfig.getPostType());
        postConfigVO.setPostTitle(postConfig.getPostTitle());
        postConfigVO.setPostText(postConfig.getPostText());
        postConfigVO.setCreatorId(postConfig.getCreatorId());
        postConfigVO.setCreatorAvatarPath(creatorConfig.getAvatarPath());
        postConfigVO.setCreateTime(DateFormatUtils.format(postConfig.getCreateTime(), "yyyy-MM-dd HH:mm"));
        postConfigVO.setPopularity(postConfig.getPopularity());
        postConfigVO.setViewSum(postConfig.getViewSum());
        postConfigVO.setLikeSum(postConfig.getLikeSum());
        postConfigVO.setCollectSum(postConfig.getCollectSum());
        postConfigVO.setDislikeSum(postConfig.getDislikeSum());
        postConfigVO.setHasLike(hasLike);
        postConfigVO.setHasDislike(hasDislike);
        postConfigVO.setHasCollect(hasCollect);
        return postConfigVO;
    }
}
