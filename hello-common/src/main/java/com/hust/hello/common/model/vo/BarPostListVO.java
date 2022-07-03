package com.hust.hello.common.model.vo;

import com.hust.hello.common.model.dto.BarPostListDTO;
import com.hust.hello.common.model.entity.HelloUserConfig;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/28 14:51
 * @description: 版面的贴文列表
 */
@Data
public class BarPostListVO {
    private String postId;

    private String barId;

    private Integer postType;

    private String postTitle;

    private String postText;

    private String creatorId;

    private String creatorAvatarPath;

    private String createTime;

    private Integer popularity;

    private String lastEditTime;

    private Integer viewSum;

    private Integer likeSum;

    private Integer collectSum;

    private Integer dislikeSum;

    private Integer commentSum;

    private Integer reportSum;

    private Boolean hasLike;

    private Boolean hasCollect;

    private Boolean hasDislike;

    public static BarPostListVO convertToVO(BarPostListDTO postDTO, String creatorAvatarPath,
                                            boolean hasLike, boolean hasDislike, boolean hasCollect) {
        BarPostListVO postVO = new BarPostListVO();
        postVO.setPostId(postDTO.getPostId());
        postVO.setBarId(postDTO.getBarId());
        postVO.setPostType(postDTO.getPostType());
        postVO.setPostTitle(postDTO.getPostTitle());
        postVO.setPostText(postDTO.getPostText());
        postVO.setCreatorId(postDTO.getCreatorId());
        postVO.setCreatorAvatarPath(creatorAvatarPath);
        postVO.setCreateTime(DateFormatUtils.format(postDTO.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        postVO.setPopularity(postDTO.getPopularity());
        postVO.setLastEditTime(DateFormatUtils.format(postDTO.getLastEditTime(), "yyyy-MM-dd HH:mm:ss"));
        postVO.setViewSum(postDTO.getViewSum());
        postVO.setLikeSum(postDTO.getLikeSum());
        postVO.setCollectSum(postDTO.getCollectSum());
        postVO.setDislikeSum(postDTO.getDislikeSum());
        postVO.setCommentSum(postDTO.getCommentSum());
        postVO.setReportSum(postDTO.getReportSum());
        postVO.setHasLike(hasLike);
        postVO.setHasDislike(hasDislike);
        postVO.setHasCollect(hasCollect);
        return postVO;
    }
}
