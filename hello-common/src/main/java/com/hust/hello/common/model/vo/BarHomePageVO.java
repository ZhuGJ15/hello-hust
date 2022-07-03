package com.hust.hello.common.model.vo;

import com.hust.hello.common.model.entity.HelloBarConfig;
import lombok.Data;

import java.util.List;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/26 12:34
 * @description:
 */
@Data
public class BarHomePageVO {
    private String barId;

    private String barName;

    private String introduction;

    private String avatarUrl;

    private Integer barType;

    private Integer followerSum;

    private Integer postSum;

    private Integer popularity;

    private List<PostHomePageVO> postList;

    public static BarHomePageVO convertToVO(HelloBarConfig barConfig, List<PostHomePageVO> postList){
        BarHomePageVO barHomePageVO = new BarHomePageVO();
        barHomePageVO.setBarId(barConfig.getBarId());
        barHomePageVO.setBarName(barConfig.getBarName());
        barHomePageVO.setBarType(barConfig.getBarType());
        barHomePageVO.setPopularity(barConfig.getPopularity());
        barHomePageVO.setPostList(postList);
        barHomePageVO.setAvatarUrl(barConfig.getAvatarPath());
        barHomePageVO.setFollowerSum(barConfig.getFollowerSum());
        barHomePageVO.setPostSum(barConfig.getPostSum());
        barHomePageVO.setIntroduction(barConfig.getIntroduction());
        return barHomePageVO;
    }
}
