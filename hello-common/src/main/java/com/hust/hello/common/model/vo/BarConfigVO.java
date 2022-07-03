package com.hust.hello.common.model.vo;

import com.hust.hello.common.model.entity.HelloBarConfig;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/22 17:29
 * @description:
 */
@Data
public class BarConfigVO {
    private String barId;

    private String barName;

    private String introduction;

    private String avatarUrl;

    private Integer barType;

    private Integer followerSum;

    private Integer postSum;

    private Integer popularity;

    private List<String> barPrincipal;

    private List<String> barAdmin;

    private String createTime;

    private String creatorId;

    public static BarConfigVO barConfigConvertVO(HelloBarConfig barConfig){
        BarConfigVO barConfigVO = new BarConfigVO();
        barConfigVO.setBarId(barConfig.getBarId());
        barConfigVO.setBarName(barConfig.getBarName());
        barConfigVO.setIntroduction(barConfig.getIntroduction());
        barConfigVO.setBarType(barConfig.getBarType());
        barConfigVO.setFollowerSum(barConfig.getFollowerSum());
        barConfigVO.setPostSum(barConfig.getPostSum());
        barConfigVO.setPopularity(barConfig.getPopularity());
        barConfigVO.setAvatarUrl(barConfig.getAvatarPath());

        ArrayList<String> principalList = new ArrayList<>(Arrays.asList(barConfig.getBarPrincipal().split(";")));
        barConfigVO.setBarPrincipal(principalList);
        ArrayList<String> adminList = new ArrayList<>(Arrays.asList(barConfig.getBarAdmin().split(";")));
        barConfigVO.setBarAdmin(adminList);

        barConfigVO.setCreateTime(DateFormatUtils.format(barConfig.getCreateTime(), "yyyy-MM-dd"));
        barConfigVO.setCreatorId(barConfig.getCreatorId());

        return barConfigVO;
    }
}
