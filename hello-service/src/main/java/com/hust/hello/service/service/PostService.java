package com.hust.hello.service.service;

import com.hust.hello.api.param.PostReportParam;
import com.hust.hello.api.param.PostUpdateParam;
import com.hust.hello.common.builder.BusinessExceptionBuilder;
import com.hust.hello.common.enums.AccountStatusEnum;
import com.hust.hello.common.enums.PostStatusEnum;
import com.hust.hello.common.enums.PostTypeEnum;
import com.hust.hello.common.enums.UserOperationTypeEnum;
import com.hust.hello.common.model.entity.HelloBarConfig;
import com.hust.hello.common.model.entity.HelloPostConfig;
import com.hust.hello.common.model.entity.HelloUserConfig;
import com.hust.hello.common.model.entity.HelloUserOperationRecord;
import com.hust.hello.common.model.vo.PostRecommendVO;
import com.hust.hello.common.model.vo.UserPostConfigListVO;
import com.hust.hello.common.model.vo.PostConfigVO;
import com.hust.hello.common.model.vo.PostHomePageVO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/26 21:28
 * @description:
 */
@Service
@Slf4j
public class PostService {

    @Autowired
    private HelloUserConfigCustomMapper userConfigMapper;

    @Autowired
    private HelloPostConfigCustomMapper postConfigMapper;

    @Autowired
    private HelloBarConfigCustomMapper barConfigMapper;

    @Autowired
    private HelloUserOperationCustomMapper userOperationMapper;

    @Autowired
    private BosService bosService;

    @Autowired
    private CommonService commonService;

    /**
     * 获取热门贴文
     * @param postAmount 需要获取的贴文数量
     */
    public List<Map<String, Object>> getHotPosts(Integer postAmount){
        List<PostHomePageVO> hotPostList = postConfigMapper.getHotPostList(postAmount);
        List<Map<String, Object>> res = new ArrayList<>();
        int number = 1;
        for(PostHomePageVO hotPost : hotPostList){
            Map<String, Object> resTemp = new HashMap<>();
            resTemp.put("number", number++);
            resTemp.put("postId", hotPost.getPostId());
            resTemp.put("postTitle", hotPost.getPostTitle());
            res.add(resTemp);
        }
        return res;
    }

    /**
     * 获取贴文内容
     * @param postId 需要获取内容的贴文id
     * @return
     */
    public PostConfigVO getPostByPostId(String userId, String postId) {
        HelloPostConfig postConfig = postConfigMapper.getPostByPostId(postId);
        // 校验贴子状态
        if(null == postConfig) {
            throw BusinessExceptionBuilder.createPostException("贴子不存在");
        }
        PostStatusEnum postStatus = PostStatusEnum.getEnum(postConfig.getPostStatus());
        if(postStatus == PostStatusEnum.DELETED) {
            throw BusinessExceptionBuilder.createPostException("贴子已被删除");
        }
        if(postStatus == PostStatusEnum.BANNED) {
            throw BusinessExceptionBuilder.createPostException("贴子已被封禁");
        }
        if(postStatus == PostStatusEnum.REVIEWING) {
            throw BusinessExceptionBuilder.createPostException("贴子正在审核中");
        }

        // 查看用户是否有点赞记录
        HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, postId, UserOperationTypeEnum.LIKE_POST.getCode());
        boolean hasLike = (null != likeRecord);
        // 查看用户是否有踩记录
        HelloUserOperationRecord dislikeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, postId, UserOperationTypeEnum.DISLIKE_POST.getCode());
        boolean hasDislike = (null != dislikeRecord);
        // 查看用户是否有收藏记录
        HelloUserOperationRecord collectRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, postId, UserOperationTypeEnum.COLLECT_POST.getCode());
        boolean hasCollect = (null != collectRecord);

        // 更新贴文浏览量
        HelloPostConfig updatePostConfig = new HelloPostConfig();
        updatePostConfig.setId(postConfig.getId());
        updatePostConfig.setViewSum(postConfig.getViewSum() + 1);
        postConfigMapper.updateByPrimaryKeySelective(updatePostConfig);

        // getting creator config of post, to get creator avatar
        HelloUserConfig creatorConfig = userConfigMapper.getUserByUserId(postConfig.getCreatorId());
        return PostConfigVO.convertToVO(postConfig, creatorConfig, hasLike, hasDislike, hasCollect);
    }

    /**
     * 用户获取自己的贴文列表（分页）
     */
    public HashMap<String, Object> getPostList(String userId, Integer currentPage, Integer pageSize) {
        HashMap<String, Object> res = new HashMap<>();
        Integer postAmount = userConfigMapper.getPostSumByUserId(userId);
        List<HelloPostConfig> postList = postConfigMapper.getPostListByUserId(userId, currentPage, pageSize);
        List<UserPostConfigListVO> postListVO = new ArrayList<>();
        for(HelloPostConfig post : postList) {
            String barName = barConfigMapper.getBarNameByBarId(post.getBarId());
            HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, post.getPostId(), UserOperationTypeEnum.LIKE_POST.getCode());
            HelloUserOperationRecord dislikeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, post.getPostId(), UserOperationTypeEnum.DISLIKE_POST.getCode());
            HelloUserOperationRecord collectRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, post.getPostId(), UserOperationTypeEnum.COLLECT_POST.getCode());
            boolean hasLike = (null != likeRecord);
            boolean hasDislike = (null != dislikeRecord);
            boolean hasCollect = (null != collectRecord);
            postListVO.add(UserPostConfigListVO.convertToVO(post, barName, hasLike, hasDislike, hasCollect));
        }
        res.put("postAmount", postAmount);
        res.put("postList", postListVO);
        return res;
    }

    /**
     * 获取贴文Id列表
     */
    public HashMap<String, Object> getPostIdList(String userId, Integer currentPage, Integer pageSize) {
        HashMap<String, Object> res = new HashMap<>();
        Integer postAmount = userConfigMapper.getPostSumByUserId(userId);
        List<String> postIdList = postConfigMapper.getPostIdListByUserId(userId, currentPage, pageSize);
        res.put("postAmount", postAmount);
        res.put("postIdList", postIdList);
        return res;
    }

    /**
     * 用户删除贴文
     */
    public void deletePost(String userId, String postId) {
        HelloPostConfig postConfig = postConfigMapper.getPostDeleteConfig(postId);
        HelloUserConfig userConfig = userConfigMapper.getUserByUserId(userId);
        // 判断用户账号状态，和贴文状态与权限(只有发帖人能删帖，管理员只能封禁)
        if(null == userConfig) {
            throw BusinessExceptionBuilder.userLoginException("用户不存在");
        }
        AccountStatusEnum accountStatusEnum = AccountStatusEnum.getEnum((int) userConfig.getAccountStatus());
        if(accountStatusEnum == AccountStatusEnum.DELETED) {
            throw BusinessExceptionBuilder.userLoginException("用户不存在");
        }
        if (accountStatusEnum == AccountStatusEnum.BANNED) {
            throw BusinessExceptionBuilder.userLoginException("账号状态异常,请联系管理员");
        }
        if(null == postConfig || postConfig.getPostStatus() == 3) {
            throw BusinessExceptionBuilder.createPostException("该贴文不存在");
        }
        if(!StringUtils.equals(userId, postConfig.getCreatorId())) {
            throw BusinessExceptionBuilder.createPostException("您无权删除该贴文");
        }

        HelloUserConfig userConfigUpdate = new HelloUserConfig();
        userConfigUpdate.setId(userConfig.getId());
        userConfigUpdate.setPostSum(userConfig.getPostSum() - 1);

        HelloPostConfig postConfigUpdate = new HelloPostConfig();
        postConfigUpdate.setId(postConfig.getId());
        postConfigUpdate.setPostStatus(PostStatusEnum.DELETED.getCode());
        postConfigUpdate.setLastEditTime(Date.from(Instant.now()));

        HelloBarConfig barConfig = barConfigMapper.getBarConfig(postConfig.getBarId());
        HelloBarConfig barConfigUpdate = new HelloBarConfig();
        barConfigUpdate.setId(barConfig.getId());
        barConfigUpdate.setPostSum(barConfig.getPostSum() - 1);

        // 更新数据库
        deletePostAndUpdateMultiDataTable(userConfigUpdate, postConfigUpdate, barConfigUpdate);
    }


    /**
     * 用户删帖后需要更新用户表、贴文表和版面表的数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePostAndUpdateMultiDataTable(HelloUserConfig userConfigUpdate, HelloPostConfig postConfigUpdate,
                                                  HelloBarConfig barConfigUpdate) {
        userConfigMapper.updateByPrimaryKeySelective(userConfigUpdate);
        postConfigMapper.updateByPrimaryKeySelective(postConfigUpdate);
        barConfigMapper.updateByPrimaryKeySelective(barConfigUpdate);
    }

    /**
     * 上传贴文图片
     */
    public HashMap<String, String> uploadPostPic(String userId, MultipartFile postPic) throws IOException {
        String fileType = postPic.getContentType();
        double size = postPic.getSize() * 1.0 / (1024 * 1024);
        String picName = postPic.getOriginalFilename();
        log.info("PostService. uploadPostPic. picName: {}, fileType:{}, size:{} M", picName, fileType, size);
        if(!StringUtils.equals("image/jpg", fileType) && !StringUtils.equals("image/jpeg", fileType)
                && !StringUtils.equals("image/png", fileType)) {
            throw BusinessExceptionBuilder.createPostPicException("只支持JPG、JPEG或PNG格式图片");
        }
        if (size > 7.0) {
            throw BusinessExceptionBuilder.createPostPicException("图片过大.无法上传");
        }
        if(StringUtils.isBlank(picName)) {
            throw BusinessExceptionBuilder.createPostPicException("图片名称格式错误");
        }

        // 处理图片
        InputStream picStream = HelloPictureUtils.compressPicture(postPic.getInputStream());
        String picKey = BosPathUtils.generatePostPicKey(userId, picName.substring(picName.lastIndexOf(".")));
        // 上传图片到BOS
        String picEtag = bosService.upload(picStream, picKey);

        if(StringUtils.isBlank(picEtag)){
            throw BusinessExceptionBuilder.createPostPicException("图片上传失败");
        }
        String picUrl = bosService.getDownloadUrl(picKey, -1);
        HashMap<String, String> res = new HashMap<>();
        res.put("picUrl", picUrl);
        log.info("PostService. uploadPostPic. success. return: {}", res);
        return res;
    }

    /**
     * 更新贴文：包括增改
     */
    public HashMap<String, String> updatePost(String userId, PostUpdateParam postParam) {
        HelloUserConfig userConfig = commonService.userAccountStatusCheck(userId);
        HelloBarConfig barConfig = commonService.barStatusCheck(postParam.getBarId());
        HelloPostConfig originalPostConfig = null;
        // 判断是更新贴文还是发布新帖
        boolean isAdd = StringUtils.isBlank(postParam.getOriginalPostId());
        // 如果是更新贴文，则先校验原贴文状态是否正常
        if(!isAdd) {
            originalPostConfig = commonService.postStatusCheck(postParam.getOriginalPostId());
        }

        // 新建一个贴文
        HelloPostConfig newPost = new HelloPostConfig();
        String newPostId = HelloStringUtils.generateNewPostId(userId);
        newPost.setPostId(newPostId);
        newPost.setBarId(postParam.getBarId());
        newPost.setPostStatus(PostStatusEnum.NORMAL.getCode());
        newPost.setPostType(isAdd ? PostTypeEnum.NORMAL.getCode() : null);
        newPost.setPostTitle(postParam.getPostTitle());
        newPost.setPostText(postParam.getPostContent());
        newPost.setCreatorId(userId);
        newPost.setCreateTime(isAdd ? Date.from(Instant.now()) : originalPostConfig.getCreateTime());
        newPost.setPopularity(isAdd ? 600 : originalPostConfig.getPopularity());
        newPost.setLastEditTime(Date.from(Instant.now()));
        newPost.setViewSum(isAdd ? 0 : originalPostConfig.getViewSum());
        newPost.setCollectSum(isAdd ? 0 : originalPostConfig.getCollectSum());
        newPost.setDislikeSum(isAdd ? 0 : originalPostConfig.getDislikeSum());
        newPost.setCommentSum(isAdd ? 0 : originalPostConfig.getCommentSum());
        newPost.setReportSum(isAdd ? 0 : originalPostConfig.getReportSum());
        newPost.setVersion(0);

        if(isAdd) {
            // 发布新帖
            HelloUserConfig userConfigUpdate = new HelloUserConfig();
            userConfigUpdate.setId(userConfig.getId());
            userConfigUpdate.setPostSum(userConfig.getPostSum() + 1);

            HelloBarConfig barConfigUpdate = new HelloBarConfig();
            barConfigUpdate.setId(barConfig.getId());
            barConfigUpdate.setPostSum(barConfig.getPostSum() + 1);

            commonService.updateDBForAddPost(newPost, userConfigUpdate, barConfigUpdate);
            // todo 发帖成功，这里可以增加用户活跃度、积分等
            // todo 这里可以增加贴吧的热度等

        } else {
            HelloPostConfig postConfigUpdate = new HelloPostConfig();
            postConfigUpdate.setId(originalPostConfig.getId());
            postConfigUpdate.setPostStatus(PostStatusEnum.DELETED.getCode());
            // 原贴和新帖是否发在同一个版面中
            HelloBarConfig originBarConfigUpdate = null;
            HelloBarConfig newBarConfigUpdate = null;
            if(!StringUtils.equals(originalPostConfig.getBarId(), postParam.getBarId())){
                HelloBarConfig originBarConfig = barConfigMapper.getBarConfig(originalPostConfig.getBarId());
                originBarConfigUpdate = new HelloBarConfig();
                originBarConfigUpdate.setId(originBarConfig.getId());
                originBarConfigUpdate.setPostSum(originBarConfig.getPostSum() - 1);

                newBarConfigUpdate = new HelloBarConfig();
                newBarConfigUpdate.setId(barConfig.getId());
                newBarConfigUpdate.setPostSum(barConfig.getPostSum() + 1);
            }
            commonService.updateDBForUpdatePost(postConfigUpdate, newPost, originBarConfigUpdate, newBarConfigUpdate);
            // todo 更新贴子成功，这里可以修改用户活跃度、积分和贴吧热度等

        }
        HashMap<String, String> res = new HashMap<>();
        res.put("postId", newPostId);
        return res;
    }

    /**
     * 点赞/取消点赞 贴文
     */
    public void doLikePost(String userId, String postId) {
        HelloPostConfig postConfig = commonService.postStatusCheck(postId);

        HelloUserOperationRecord dislikeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, postId, UserOperationTypeEnum.DISLIKE_POST.getCode());
        if(null != dislikeRecord) {
            throw BusinessExceptionBuilder.createUserOperationException("已踩过该贴，请先取消踩帖");
        }

        HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, postId, UserOperationTypeEnum.LIKE_POST.getCode());

        HelloPostConfig updatePostConfig = new HelloPostConfig();
        updatePostConfig.setId(postConfig.getId());
        if(null != likeRecord) {
            // 已点赞，则取消点赞
            updatePostConfig.setLikeSum(postConfig.getLikeSum() - 1);

            HelloUserOperationRecord updateOperationRecord = new HelloUserOperationRecord();
            updateOperationRecord.setId(likeRecord.getId());
            updateOperationRecord.setIsCancel(1);
            updateOperationRecord.setCancelTime(Date.from(Instant.now()));

            commonService.updateDBForCancelPostOperation(updatePostConfig, updateOperationRecord);
            log.info("user(userId:{}) cancel like post(postId:{}) success.", userId, postId);
            // todo 取消点赞成功，这里可以做一些取消点赞之后的操作
        } else {
            // 未点赞，创建点赞record
            updatePostConfig.setLikeSum(postConfig.getLikeSum() + 1);

            HelloUserOperationRecord operationRecord = new HelloUserOperationRecord();
            operationRecord.setRecordId(HelloIdUtils.generateOperationRecordId(userId, UserOperationTypeEnum.LIKE_POST.getCode()));
            operationRecord.setOperatorId(userId);
            operationRecord.setObjectId(postId);
            operationRecord.setOperateTime(Date.from(Instant.now()));
            operationRecord.setOperationType(UserOperationTypeEnum.LIKE_POST.getCode());
            operationRecord.setIsCancel(0);
            operationRecord.setVersion(0);

            commonService.updateDBForPostOperation(updatePostConfig, operationRecord);
            log.info("user(userId:{}) like post(postId:{}) success.", userId, postId);
            // todo 点赞成功，这里可以做一些热度等的操作

        }
    }

    /**
     * 收藏/取消收藏 贴文
     */
    public void doCollectPost(String userId, String postId) {
        HelloPostConfig postConfig = commonService.postStatusCheck(postId);

        HelloUserOperationRecord collectRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, postId, UserOperationTypeEnum.COLLECT_POST.getCode());

        HelloPostConfig updatePostConfig = new HelloPostConfig();
        updatePostConfig.setId(postConfig.getId());

        if(null != collectRecord) {
            // 用户已收藏该贴
            updatePostConfig.setCollectSum(postConfig.getCollectSum() - 1);

            HelloUserOperationRecord updateOperationRecord = new HelloUserOperationRecord();
            updateOperationRecord.setId(collectRecord.getId());
            updateOperationRecord.setIsCancel(1);
            updateOperationRecord.setCancelTime(Date.from(Instant.now()));

            commonService.updateDBForCancelPostOperation(updatePostConfig, updateOperationRecord);
            log.info("user(userId:{}) cancel collect post(postId:{}) success.", userId, postId);
            // todo 取消收藏成功，这里可以做一些热度相关操作
        } else {
            // 用户未收藏该贴
            updatePostConfig.setCollectSum(postConfig.getCollectSum() + 1);

            HelloUserOperationRecord operationRecord = new HelloUserOperationRecord();
            operationRecord.setRecordId(HelloIdUtils.generateOperationRecordId(userId, UserOperationTypeEnum.COLLECT_POST.getCode()));
            operationRecord.setOperatorId(userId);
            operationRecord.setObjectId(postId);
            operationRecord.setOperateTime(Date.from(Instant.now()));
            operationRecord.setOperationType(UserOperationTypeEnum.COLLECT_POST.getCode());
            operationRecord.setIsCancel(0);
            operationRecord.setVersion(0);

            commonService.updateDBForPostOperation(updatePostConfig, operationRecord);
            log.info("user(userId:{}) collect post(postId:{}) success.", userId, postId);
            // todo 收藏成功，这里可以做一些热度相关的操作

        }
    }

    /**
     * 踩/取消踩 贴文
     */
    public void doDislikePost(String userId, String postId) {
        HelloPostConfig postConfig = commonService.postStatusCheck(postId);

        HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, postId, UserOperationTypeEnum.LIKE_POST.getCode());
        if(null != likeRecord) {
            throw BusinessExceptionBuilder.createUserOperationException("已点赞该贴,请先取消点赞");
        }

        HelloUserOperationRecord dislikeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                userId, postId, UserOperationTypeEnum.DISLIKE_POST.getCode());

        HelloPostConfig updatePostConfig = new HelloPostConfig();
        updatePostConfig.setId(postConfig.getId());

        if(null != dislikeRecord) {
            // 如果踩过该贴，则取消踩帖
            updatePostConfig.setDislikeSum(postConfig.getDislikeSum() - 1);

            HelloUserOperationRecord updateOperationRecord = new HelloUserOperationRecord();
            updateOperationRecord.setId(dislikeRecord.getId());
            updateOperationRecord.setIsCancel(1);
            updateOperationRecord.setCancelTime(Date.from(Instant.now()));

            commonService.updateDBForCancelPostOperation(updatePostConfig, updateOperationRecord);
            log.info("user(userId:{}) cancel dislike post(postId:{}) success.", userId, postId);
            // todo 取消踩帖成功，这里可以做一些热度相关操作

        } else {
            // 如果未踩过该贴，则新建一条踩操作记录
            updatePostConfig.setDislikeSum(postConfig.getDislikeSum() + 1);

            HelloUserOperationRecord operationRecord = new HelloUserOperationRecord();
            operationRecord.setRecordId(HelloIdUtils.generateOperationRecordId(userId, UserOperationTypeEnum.DISLIKE_POST.getCode()));
            operationRecord.setOperatorId(userId);
            operationRecord.setObjectId(postId);
            operationRecord.setOperateTime(Date.from(Instant.now()));
            operationRecord.setOperationType(UserOperationTypeEnum.DISLIKE_POST.getCode());
            operationRecord.setIsCancel(0);
            operationRecord.setVersion(0);

            commonService.updateDBForPostOperation(updatePostConfig, operationRecord);
            log.info("user(userId:{}) dislike post(postId:{}) success.", userId, postId);
            // todo 踩帖成功，这里可以做一些热度相关的操作
        }
    }

    public void doReportPost(String userId, PostReportParam reportParam) {
        HelloPostConfig postConfig = commonService.postStatusCheck(reportParam.getPostId());

        HelloUserOperationRecord reportRecord = userOperationMapper.selectReportRecord(userId, reportParam.getPostId());
        if(null != reportRecord) {
            if(reportRecord.getIsCancel() == 0) {
                throw BusinessExceptionBuilder.createUserOperationException("请勿重复举报.你已举报成功.管理员将尽快审核.");
            } else {
                // todo 这里的论坛管理小组可以换成详细邮箱地址等
                throw BusinessExceptionBuilder.createUserOperationException("请勿重复举报.如对处理结果有异议,请联系论坛管理小组");
            }
        }

        HelloPostConfig updatePostConfig = new HelloPostConfig();
        updatePostConfig.setId(postConfig.getId());
        updatePostConfig.setReportSum(postConfig.getReportSum() + 1);

        HelloUserOperationRecord operationRecord = new HelloUserOperationRecord();
        operationRecord.setRecordId(HelloIdUtils.generateOperationRecordId(userId, UserOperationTypeEnum.REPORT_POST.getCode()));
        operationRecord.setOperatorId(userId);
        operationRecord.setObjectId(reportParam.getPostId());
        operationRecord.setOperationType(UserOperationTypeEnum.REPORT_POST.getCode());
        operationRecord.setOperateTime(Date.from(Instant.now()));
        operationRecord.setRemark(reportParam.getRemark());
        operationRecord.setIsCancel(0);
        operationRecord.setVersion(0);

        commonService.updateDBForPostOperation(updatePostConfig, operationRecord);
        // todo 举报成功，这里可以做一些其他操作
    }

    /**
     * recommand post to user
     * @return
     */
    public HashMap<String, Object> doRecommendPost(String userId, Integer currentPage, Integer pageSize) {
        List<HelloPostConfig> postList = postConfigMapper.getPostListForRecommand(currentPage, pageSize);
        List<PostRecommendVO> postRecommendVOList = new ArrayList<>();
        for (HelloPostConfig postConfig : postList) {
            HelloUserConfig userConfig = userConfigMapper.getUserByUserId(postConfig.getCreatorId());
            HelloBarConfig barConfig = barConfigMapper.getBarConfig(postConfig.getBarId());

            HelloUserOperationRecord likeRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, postConfig.getPostId(), UserOperationTypeEnum.LIKE_POST.getCode());
            HelloUserOperationRecord collectRecord = userOperationMapper.selectRecordByUserIdAndOperationType(
                    userId, postConfig.getPostId(), UserOperationTypeEnum.COLLECT_POST.getCode());
            boolean hasLike = (likeRecord != null);
            boolean hasDislike = !hasLike;
            boolean hasCollect = (collectRecord != null);

            postRecommendVOList.add(PostRecommendVO.convertToVO(postConfig, barConfig.getBarName(),
                    userConfig, hasLike, hasDislike, hasCollect));
        }

        HashMap<String, Object> res = new HashMap<>();
        res.put("postList", postRecommendVOList);
        return res;
    }
}
