package com.hust.hello.service.service;

import com.baidubce.services.bos.model.BosObject;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.builder.SystemExceptionBuilder;
import com.hust.hello.common.enums.AccountStatusEnum;
import com.hust.hello.common.enums.FileTypeEnum;
import com.hust.hello.common.enums.PostStatusEnum;
import com.hust.hello.common.enums.UserOperationTypeEnum;
import com.hust.hello.common.model.entity.HelloBarConfig;
import com.hust.hello.common.model.entity.HelloPostConfig;
import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.common.model.entity.HelloUserOperationRecord;
import com.hust.hello.common.model.vo.PostRecommendVO;
import com.hust.hello.common.model.vo.UserConfigDisplayedVO;
import com.hust.hello.common.model.vo.UserConfigVO;
import com.hust.hello.common.utils.BosPathUtils;
import com.hust.hello.common.utils.HelloIdUtils;
import com.hust.hello.common.utils.HelloPictureUtils;
import com.hust.hello.common.utils.HelloStringUtils;
import com.hust.hello.dao.mapper.customer.HelloBarConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloPostConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloUserConfigCustomMapper;
import com.hust.hello.dao.mapper.customer.HelloUserOperationCustomMapper;
import com.hust.hello.service.remote.BosService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/18 12:42
 * @description:
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    BosService bosService;

    @Autowired
    CommonService commonService;

    @Autowired
    HelloUserOperationCustomMapper userOperationMapper;

    @Autowired
    HelloPostConfigCustomMapper postConfigMapper;

    @Autowired
    HelloUserConfigCustomMapper userConfigMapper;

    @Autowired
    HelloBarConfigCustomMapper barConfigMapper;

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    public UserConfigVO getUserInfo(String userId) {
        HelloUserConfig latestUserConfig = getLatestUserConfig(userId);
        return UserConfigVO.userConfigConvertToVo(latestUserConfig);
    }

    public HashMap<String, String> getUserAvatarUrl(String userId) {
        HelloUserConfig userConfig = userConfigMapper.getUserByUserId(userId);
        if(null == userConfig) {
            throw BusinessExceptionBuilder.userLoginException("该账号不存在.");
        }
        // 判断用户是否有头像，如果没有则上传默认头像
        if(StringUtils.isBlank(userConfig.getAvatarPath())) {
            String avatarPath = bosService.createFolder(userId, userConfig.getSex());
            userConfig.setAvatarPath(avatarPath);
            // 更新头像路径
            HelloUserConfig userConfigUpdate = new HelloUserConfig();
            userConfigUpdate.setId(userConfig.getId());
            userConfigUpdate.setAvatarPath(avatarPath);
            userConfigUpdate.setAvatarPath(avatarPath);
            userConfigMapper.updateByPrimaryKeySelective(userConfigUpdate);
        }
        HashMap<String, String> res = new HashMap<>();
        res.put("avatarUrl", userConfig.getAvatarPath());
        return res;
    }

    /**
     * 自动更新并返回最新用户信息
     * @param userId 用户id
     */
    public HelloUserConfig getLatestUserConfig(String userId){
        HelloUserConfig userConfig = userConfigMapper.getUserByUserId(userId);
        if(null == userConfig) {
            throw BusinessExceptionBuilder.userLoginException("该账号不存在.请重新登录");
        }
        HelloUserConfig updateConfig = new HelloUserConfig();
        updateConfig.setId(userConfig.getId());
        Date now = Date.from(Instant.now());
        // 更新账号登录天数
        if(!DateUtils.isSameDay(userConfig.getLastLoginTime(), now)){
            updateConfig.setSignInDays(userConfig.getSignInDays() + 1);
            userConfig.setSignInDays(userConfig.getSignInDays() + 1);
        }
        // 更新用户最后登录时间
        updateConfig.setLastLoginTime(now);
        // 判断用户是否有头像，没有则使用默认头像，并在bos上创建文件夹
        if(StringUtils.isBlank(userConfig.getAvatarPath())){
            String avatarPath = bosService.createFolder(userId, userConfig.getSex());
            userConfig.setAvatarPath(avatarPath);
            updateConfig.setAvatarPath(avatarPath);
        }
        userConfigMapper.updateByPrimaryKeySelective(updateConfig);
        return userConfig;
    }

    /**
     * [Deprecated] 返回一张头像图片
     */
    public BufferedImage getUserAvatar(String userId) {
        try{
            HelloUserConfig userConfig = userConfigMapper.getUserByUserId(userId);
            // 兼容判断用户是否有头像，如果用户没有头像
            if(StringUtils.isBlank(userConfig.getAvatarPath())){
                log.info("user has not create avatar info in database. userId:{}", userId);
                // 先上传默认头像，并创建用户的文件路径
                bosService.createFolder(userId, userConfig.getSex());
                // 更新用户信息
                HelloUserConfig newConfig = new HelloUserConfig();
                newConfig.setId(userConfig.getId());
                userConfig.setAvatarPath(userConfig.getSex() == 0 ? "girl_1.png" : "boy_1.png");
                newConfig.setAvatarPath(userConfig.getSex() == 0 ? "girl_1.png" : "boy_1.png");
                userConfigMapper.updateByPrimaryKeySelective(newConfig);
            }
            String avatarPath = HelloStringUtils.getPicPath(userId, FileTypeEnum.PIC_AVATAR, userConfig.getAvatarPath());
            BosObject picObject = bosService.download(avatarPath);
            return ImageIO.read(picObject.getObjectContent());
        } catch (Exception e){
            log.error("UserService. getUserAvatar. fail. ", e);
            throw SystemExceptionBuilder.createBosException("下载文件失败");
        }
    }

    /**
     * 获取其他用户的展示信息
     */
    public UserConfigDisplayedVO getDisplayedInfo(String userId, String otherUserId) {
        HelloUserConfig otherUserConfig = userConfigMapper.getUserByUserId(otherUserId);
        if(null == otherUserConfig) {
            throw BusinessExceptionBuilder.createParamException("该用户不存在");
        }
        AccountStatusEnum accountStatus = AccountStatusEnum.getEnum((int) otherUserConfig.getAccountStatus());
        // 如果用户注销了账号，或者用户的没有头像，则使用默认系统头像
        if(accountStatus == AccountStatusEnum.DELETED || StringUtils.isBlank(otherUserConfig.getAvatarPath())) {
            otherUserConfig.setAvatarPath(HelloStringUtils.DELETED_ACCOUNT_AVATAR_URL);
        }
        // todo 心跳确定用户是否在线
        boolean isOnline = false;

        // 确定是否关注该用户
        boolean isFollowing = false;
        HelloUserOperationRecord userOperationRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, otherUserId, UserOperationTypeEnum.FOLLOW_USER.getCode());
        if(null != userOperationRecord) {
            isFollowing = true;
        }
        return UserConfigDisplayedVO.convertToDisplayedVO(otherUserConfig, isOnline, isFollowing);
    }

    /**
     * 关注用户
     */
    public void doFollowUser(String userId, String followedUserId) {
        if(StringUtils.equals(userId, followedUserId)) {
            throw BusinessExceptionBuilder.createParamException("用户不能关注自己");
        }

        HelloUserConfig userConfig = commonService.userAccountStatusCheck(userId);
        HelloUserConfig followedUserConfig = commonService.userAccountStatusCheck(followedUserId);

        HelloUserOperationRecord operationRecord = userOperationMapper.selectRecordByUserIdAndOperationType(userId,
                followedUserId, UserOperationTypeEnum.FOLLOW_USER.getCode());

        // 判断是否已关注该用户
        if (null != operationRecord) {
            throw BusinessExceptionBuilder.createUserOperationException("不可重复关注");
        }

        HelloUserConfig updateUserConfig = new HelloUserConfig();
        updateUserConfig.setId(userConfig.getId());
        updateUserConfig.setFollowingUsersum(userConfig.getFollowingUsersum() + 1);

        HelloUserConfig updateFollowedUserConfig = new HelloUserConfig();
        updateFollowedUserConfig.setId(followedUserConfig.getId());
        updateFollowedUserConfig.setFollowerSum(followedUserConfig.getFollowerSum() + 1);

        HelloUserOperationRecord userOperationRecord = new HelloUserOperationRecord();
        userOperationRecord.setRecordId(HelloIdUtils.generateOperationRecordId(userId, UserOperationTypeEnum.FOLLOW_USER.getCode()));
        userOperationRecord.setOperatorId(userId);
        userOperationRecord.setObjectId(followedUserId);
        userOperationRecord.setOperateTime(Date.from(Instant.now()));
        userOperationRecord.setOperationType(UserOperationTypeEnum.FOLLOW_USER.getCode());
        userOperationRecord.setIsCancel(0);
        userOperationRecord.setVersion(0);

        commonService.updateDBForFollowUser(updateUserConfig, updateFollowedUserConfig, userOperationRecord);

        // todo 关注成功，这里可以做一些热度相关操作
    }

    /**
     * 取关用户
     */
    public void doCancelFollowUser(String userId, String followedUserId) {
        HelloUserConfig userConfig = commonService.userAccountStatusCheck(userId);
        HelloUserConfig followedUserConfig = commonService.userAccountStatusCheck(followedUserId);

        HelloUserOperationRecord operationRecord = userOperationMapper.selectRecordByUserIdAndOperationType(userId,
                followedUserId, UserOperationTypeEnum.FOLLOW_USER.getCode());
        if(null == operationRecord) {
            throw BusinessExceptionBuilder.createUserOperationException("未关注该用户");
        }

        HelloUserConfig updateUserConfig = new HelloUserConfig();
        updateUserConfig.setId(userConfig.getId());
        updateUserConfig.setFollowingUsersum(userConfig.getFollowingUsersum() - 1);

        HelloUserConfig updateFollowedUserConfig = new HelloUserConfig();
        updateFollowedUserConfig.setId(followedUserConfig.getId());
        updateFollowedUserConfig.setFollowerSum(followedUserConfig.getFollowerSum() - 1);

        HelloUserOperationRecord updateOperation = new HelloUserOperationRecord();
        updateOperation.setId(operationRecord.getId());
        updateOperation.setIsCancel(1);
        updateOperation.setCancelTime(Date.from(Instant.now()));

        commonService.updateDBForCancelFollowUser(updateUserConfig, updateFollowedUserConfig, updateOperation);
    }

    /**
     * uploading user avatar and return url of BOS
     */
    public HashMap<String, String> uploadUserAvatar(String userId, MultipartFile avatarFile) throws IOException {
        // check user account status
        HelloUserConfig userConfig = commonService.userAccountStatusCheck(userId);

        String fileType = avatarFile.getContentType();
        double size = avatarFile.getSize() * 1.0 / (1024 * 1024);
        String filename = avatarFile.getOriginalFilename();
        log.info("UserService. uploadUserAvatar. fileName: {}, fileType:{}, size:{} M", filename, fileType, size);
        if(!StringUtils.equals("image/jpg", fileType) && !StringUtils.equals("image/jpeg", fileType)
                && !StringUtils.equals("image/png", fileType)) {
            throw BusinessExceptionBuilder.createPostPicException("只支持JPG、JPEG或PNG格式图片");
        }
        if (size > 7.0) {
            throw BusinessExceptionBuilder.createPostPicException("图片过大.无法上传");
        }
        if(StringUtils.isBlank(filename)) {
            throw BusinessExceptionBuilder.createPostPicException("图片名称格式错误");
        }

        InputStream inputStream = HelloPictureUtils.compressPicture(avatarFile.getInputStream());
        String key = BosPathUtils.generateUserAvatarKey(userId);

        String eTag = bosService.upload(inputStream, key);

        if(StringUtils.isBlank(eTag)) {
            throw BusinessExceptionBuilder.createUserConfigException("上传头像失败");
        }

        // generate avatar url of BOS
        String avatarUrl = bosService.getDownloadUrl(key, -1);
        // update user avatar path
        HelloUserConfig updateUserConfig = new HelloUserConfig();
        updateUserConfig.setId(userConfig.getId());
        updateUserConfig.setAvatarPath(avatarUrl);
        userConfigMapper.updateByPrimaryKeySelective(updateUserConfig);

        HashMap<String, String> res = new HashMap<>();
        res.put("avatarUrl", avatarUrl);
        log.info("UserService. uploadUserAvatar. success. return: {}", res);
        return res;
    }

    /**
     * update user config for update-api
     */
    public void updateUserConfig(String userId, String userName, Integer showSex, Integer showConstellation) {
        // check user account status
        HelloUserConfig userConfig = commonService.userAccountStatusCheck(userId);

        HelloUserConfig updateUserConfig = new HelloUserConfig();
        updateUserConfig.setId(userConfig.getId());
        if(!StringUtils.isBlank(userName)) {
            updateUserConfig.setUserName(userName);
        }
        updateUserConfig.setShowSex(showSex);
        updateUserConfig.setShowConstellation(showConstellation);

        userConfigMapper.updateByPrimaryKeySelective(updateUserConfig);
    }

    /**
     * get default avatar list providing by system
     */
    public HashMap<String, List> getDefaultAvatarUrlList(String userId) {
        HelloUserConfig userConfig = commonService.userAccountStatusCheck(userId);

        List<String> avatarUrlList = new ArrayList<>();
        if(userConfig.getSex() == 0) {
            String[] avatarPicName = {"girl_1.png", "girl_2.png", "girl_3.png", "girl_4.png", "girl_5.png", "girl_6.png"};
            for(String name : avatarPicName) {
                avatarUrlList.add(HelloStringUtils.DEFAULT_GIRL_AVATAR_PREFIX + name);
            }
        } else {
            String[] avatarPicName = {"boy_1.png", "boy_2.png", "boy_3.png", "boy_4.png", "boy_5.png", "boy_6.png"};
            for(String name: avatarPicName) {
                avatarUrlList.add(HelloStringUtils.DEFAULT_BOY_AVATAR_PREFIX + name);
            }
        }
        HashMap<String, List> res = new HashMap<>();
        res.put("avatarList", avatarUrlList);
        return res;
    }

    /**
     * update user avatar with provided url
     */
    public void updateAvatarByUrl(String userId, String avatarUrl) {
        HelloUserConfig userConfig = commonService.userAccountStatusCheck(userId);

        /* 这里可以判断一下头像是否在bos中存在
        if(!bosService.isExist(avatarUrl)) {
            throw BusinessExceptionBuilder.createParamException("该图片路径不存在");
        }
         */
        HelloUserConfig updateUserConfig = new HelloUserConfig();
        updateUserConfig.setId(userConfig.getId());
        updateUserConfig.setAvatarPath(avatarUrl);

        userConfigMapper.updateByPrimaryKeySelective(updateUserConfig);
    }

    /**
     * get post list of user collected
     */
    public HashMap<String, Object> getUserCollectPostList(String userId, Integer currentPage, Integer pageSize) {
        List<HelloUserOperationRecord> collectPostRecord = userOperationMapper.selectCollectPostRecord(userId, currentPage, pageSize);

        List<PostRecommendVO> postCollectedVOList = new ArrayList<>();
        for(HelloUserOperationRecord record : collectPostRecord) {
            HelloPostConfig postConfig = postConfigMapper.getPostByPostId(record.getObjectId());
            if(null == postConfig) {
                continue;
            }
            if(PostStatusEnum.getEnum(postConfig.getPostStatus()) != PostStatusEnum.NORMAL) {
                continue;
            }
            HelloUserConfig userConfig = userConfigMapper.getUserByUserId(postConfig.getCreatorId());
            HelloBarConfig barConfig = barConfigMapper.getBarConfig(postConfig.getBarId());

            HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, postConfig.getPostId(), UserOperationTypeEnum.LIKE_POST.getCode());
            boolean hasLike = (null != likeRecord);
            HelloUserOperationRecord dislikeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, postConfig.getPostId(), UserOperationTypeEnum.DISLIKE_POST.getCode());
            boolean hasDislike = (null != dislikeRecord);
            postCollectedVOList.add(PostRecommendVO.convertToVO(postConfig, barConfig.getBarName(),
                    userConfig, hasLike, hasDislike, true));
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("postList", postCollectedVOList);
        return res;
    }

    /**
     * get user following other user list
     */
    public Map<String, Object> getFollowingUserList(String userId, Integer currentPage, Integer pageSize) {
        List<HelloUserOperationRecord> followUserRecordList = userOperationMapper.selectFollowUserRecord(userId, currentPage, pageSize);
        List<UserConfigDisplayedVO> userConfigDisplayedVOList = new ArrayList<>();
        for(HelloUserOperationRecord record : followUserRecordList) {
            HelloUserConfig userConfig = userConfigMapper.getUserByUserId(record.getObjectId());
            if(null == userConfig || AccountStatusEnum.getEnum((int)userConfig.getAccountStatus()) == AccountStatusEnum.DELETED) {
                continue;
            }
            // todo 这里 isonline
            userConfigDisplayedVOList.add(UserConfigDisplayedVO.convertToDisplayedVO(userConfig, false, true));
        }

        HashMap<String, Object> res = new HashMap<>();
        res.put("userList", userConfigDisplayedVOList);
        return res;
    }

    /**
     * get other user list following the user
     */
    public Map<String, Object> getFollowedUserList(String userId, Integer currentPage, Integer pageSize) {
        List<HelloUserOperationRecord> followedUserRecordList = userOperationMapper.selectFollowedUserRecord(userId, currentPage, pageSize);
        List<UserConfigDisplayedVO> userConfigDisplayedVOList = new ArrayList<>();
        for(HelloUserOperationRecord record : followedUserRecordList) {
            HelloUserConfig userConfig = userConfigMapper.getUserByUserId(record.getOperatorId());
            if(null == userConfig || AccountStatusEnum.getEnum((int)userConfig.getAccountStatus()) == AccountStatusEnum.DELETED) {
                continue;
            }
            // todo 这里 isonline
            HelloUserOperationRecord followRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, userConfig.getUserId(), UserOperationTypeEnum.FOLLOW_USER.getCode());
            boolean isFollowing = (null != followRecord);
            userConfigDisplayedVOList.add(UserConfigDisplayedVO.convertToDisplayedVO(userConfig, false, isFollowing));
        }

        HashMap<String, Object> res = new HashMap<>();
        res.put("userList", userConfigDisplayedVOList);
        return res;
    }
}
