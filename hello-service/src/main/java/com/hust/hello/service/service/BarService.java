package com.hust.hello.service.service;

import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.enums.BarStatusEnum;
import com.hust.hello.common.enums.FileTypeEnum;
import com.hust.hello.common.enums.UserOperationTypeEnum;
import com.hust.hello.common.model.dto.BarNameListDTO;
import com.hust.hello.common.model.dto.BarPostListDTO;
import com.hust.hello.common.model.entity.HelloBarConfig;
import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.common.model.entity.HelloUserOperationRecord;
import com.hust.hello.common.model.vo.BarConfigVO;
import com.hust.hello.common.model.vo.BarHomePageVO;
import com.hust.hello.common.model.vo.BarPostListVO;
import com.hust.hello.common.model.vo.PostHomePageVO;
import com.hust.hello.common.utils.BosPathUtils;
import com.hust.hello.dao.mapper.customer.HelloBarConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloPostConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloUserConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloUserOperationCustomMapper;
import com.hust.hello.service.remote.BosService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jcodings.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/22 17:25
 * @description:
 */
@Service
@Slf4j
public class BarService {

    @Autowired
    BosService bosService;

    @Autowired
    CommonService commonService;

    @Autowired
    private HelloBarConfigCustomMapper barConfigMapper;

    @Autowired
    private HelloPostConfigCustomMapper postConfigMapper;

    @Autowired
    private HelloUserConfigCustomMapper userConfigMapper;

    @Autowired
    private HelloUserOperationCustomMapper userOperationMapper;

    /**
     * 获取首页展示的版块信息
     */
    public List<BarHomePageVO> getHomePageBarConfig(Integer barAmount, Integer postAmount){
        List<BarHomePageVO> res = new ArrayList<>();
        List<HelloBarConfig> barConfigList = barConfigMapper.getHomePageBarList(barAmount);
        for(HelloBarConfig barConfig : barConfigList){
            List<PostHomePageVO> postList = postConfigMapper.getHomePagePostList(barConfig.getBarId(), postAmount);
            res.add(BarHomePageVO.convertToVO(barConfig, postList));
        }
        return res;
    }

    /**
     * 获取某一个版块信息
     */
    public BarConfigVO getBarConfig(String barId){
        HelloBarConfig barConfig = barConfigMapper.getBarConfig(barId);
        if(null == barConfig){
            throw BusinessExceptionBuilder.createBarException("版面不存在");
        }
        BarStatusEnum barStatus = BarStatusEnum.getEnum(barConfig.getBarStatus());
        if(BarStatusEnum.TOBE_REVIEWED == barStatus) {
            throw BusinessExceptionBuilder.createBarException("该版面正在审核中");
        } else if (BarStatusEnum.BANNED == barStatus) {
            throw BusinessExceptionBuilder.createBarException("该版面已被封禁");
        } else if (BarStatusEnum.DELETED == barStatus) {
            throw BusinessExceptionBuilder.createBarException("该版面已被删除");
        } else {
            // todo 这里可以统计版面访问量
            return BarConfigVO.barConfigConvertVO(barConfig);
        }
    }

    /**
     * 获取版块信息列表
     */
    public List<BarHomePageVO> getBarList(Integer currentPage, Integer pageSize, Integer postAmount){
        List<HelloBarConfig> barConfigList = barConfigMapper.getAllBarList(currentPage, pageSize);
        List<BarHomePageVO> res = new ArrayList<>();
        for(HelloBarConfig barConfig : barConfigList){
            List<PostHomePageVO> postList = postConfigMapper.getHomePagePostList(barConfig.getBarId(), postAmount);
            res.add(BarHomePageVO.convertToVO(barConfig, postList));
        }
        return res;
    }

    /**
     * 获取热门版面
     */
    public List<Map<String, Object>> getHotBars(Integer barAmount){
        List<HelloBarConfig> hotBarConfigList = barConfigMapper.getHotBarConfigList(barAmount);
        List<Map<String, Object>> res = new ArrayList<>();
        int number = 1;
        for(HelloBarConfig barConfig : hotBarConfigList){
            Map<String, Object> barConfigTemp = new HashMap<>();
            barConfigTemp.put("number", number++);
            barConfigTemp.put("barId", barConfig.getBarId());
            barConfigTemp.put("barName", barConfig.getBarName());
            barConfigTemp.put("avatarUrl", barConfig.getAvatarPath());
            barConfigTemp.put("introduction", barConfig.getIntroduction());
            barConfigTemp.put("followerSum", barConfig.getFollowerSum());
            barConfigTemp.put("postSum", barConfig.getPostSum());
            res.add(barConfigTemp);
        }
        return res;
    }

    /**
     * paginate to get the list of posts in the bar by barId.
     */
    public HashMap<String, Object> getPostListByBarId(String userId, String barId, Integer currentPage, Integer pageSize) {
        HelloBarConfig barConfig = commonService.barStatusCheck(barId);

        List<BarPostListDTO> postDTOList = postConfigMapper.getPostListByBarId(barId, currentPage, pageSize);
        List<BarPostListVO> postVOList = new ArrayList<>();
        HashMap<String, String> creatorAvatarMap = new HashMap<>();
        for(BarPostListDTO postDTO : postDTOList) {
            // get creator avatar path
            if(!creatorAvatarMap.containsKey(postDTO.getCreatorId())){
                HelloUserConfig creatorConfig = userConfigMapper.getUserByUserId(postDTO.getCreatorId());
                creatorAvatarMap.put(postDTO.getCreatorId(), creatorConfig.getAvatarPath());
            }
            // get record of user operation, such as like, dislike and collect
            HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, postDTO.getPostId(), UserOperationTypeEnum.LIKE_POST.getCode());
            boolean hasLike = (null != likeRecord);

            HelloUserOperationRecord dislikeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, postDTO.getPostId(), UserOperationTypeEnum.DISLIKE_POST.getCode());
            boolean hasDislike = (null != dislikeRecord);

            HelloUserOperationRecord collectRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, postDTO.getPostId(), UserOperationTypeEnum.COLLECT_POST.getCode());
            boolean hasCollect = (null != collectRecord);

            postVOList.add(BarPostListVO.convertToVO(postDTO, creatorAvatarMap.get(postDTO.getCreatorId()),
                    hasLike, hasDislike, hasCollect));
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("postSum", barConfig.getPostSum());
        res.put("postList", postVOList);
        return res;
    }

    /**
     * get bar avatar url of BOS
     */
    public HashMap<String, String> getBarAvatarUrl(String barId) {
        HelloBarConfig barConfig = barConfigMapper.getBarConfig(barId);
        if(null == barConfig) {
            throw BusinessExceptionBuilder.createBarException("该版面不存在");
        }
        // 判断该贴吧是否有头像，没有则上传默认头像,并生成Bos链接
        if(StringUtils.isBlank(barConfig.getAvatarPath())){
            bosService.createFolder(barId);
            String avatarUrl = bosService.getDownloadUrl(
                    BosPathUtils.generateBarFileKey(barId, FileTypeEnum.BAR_AVATAR, BosPathUtils.BAR_DEFAULT_AVATAR), -1);

            HelloBarConfig barConfigUpdate = new HelloBarConfig();
            barConfigUpdate.setId(barConfig.getId());
            barConfig.setAvatarPath(avatarUrl);
            barConfigUpdate.setAvatarPath(avatarUrl);
            barConfigMapper.updateByPrimaryKeySelective(barConfigUpdate);
        }

        HashMap<String, String> res = new HashMap<>();
        res.put("avatarUrl", barConfig.getAvatarPath());
        return res;
    }

    public List<BarNameListDTO> getBarNameList() {
        return barConfigMapper.getBarNameList();
    }
}
