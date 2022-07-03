package com.hust.hello.api;

import com.hust.hello.common.bean.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2021/10/2 22:31
 * @description:
 */
@RequestMapping(value = {"/user"})
public interface UserApi {

    @RequestMapping(value = {"/getBasicInfo"}, method = RequestMethod.GET)
    Response getBasicInfo();

    @RequestMapping(value = {"/getAvatarUrl"}, method = RequestMethod.GET)
    Response getUserAvatar();

    @RequestMapping(value = {"/getPostList"}, method = RequestMethod.GET)
    Response getUserPostList(@RequestParam(value = "currentPage") Integer currentPage,
                             @RequestParam(value = "pageSize") Integer pageSize);

    @RequestMapping(value = {"/getPostIDList"}, method = RequestMethod.GET)
    Response getUserPostIDList(@RequestParam(value = "currentPage", required = false) Integer currentPage,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = {"/displayedInfo"}, method = RequestMethod.GET)
    Response getDisplayedInfo(@RequestParam(value = "userId") String userId);

    @RequestMapping(value = {"/followUser"}, method = RequestMethod.GET)
    Response followOtherUser(@RequestParam(value = "userId") String otherUserId);

    @RequestMapping(value = {"/cancelFollowUser"}, method = RequestMethod.GET)
    Response cancelFollowOtherUser(@RequestParam(value = "userId") String otherUserId);

    @RequestMapping(value = {"/uploadAvatar"}, method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response uploadUserAvatar(@RequestPart("avatarPic")MultipartFile avatarFile);

    @RequestMapping(value = {"/updateConfig"}, method = RequestMethod.GET)
    Response updateUserConfig(@RequestParam(value = "userName", required = false) String userName,
                              @RequestParam(value = "showSex", required = false) Integer showSex,
                              @RequestParam(value = "showConstellation", required = false) Integer showConstellation);

    @RequestMapping(value = {"/getAvatarList"}, method = RequestMethod.GET)
    Response getDefaultAvatarList();

    @RequestMapping(value = {"/selectAvatar"}, method = RequestMethod.GET)
    Response selectAvatar(@RequestParam("avatarUrl") String avatarUrl);

    @RequestMapping(value = {"/collectPostList"}, method = RequestMethod.GET)
    Response getCollectPostList(@RequestParam("currentPage") Integer currentPage,
                                @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = {"/followingUserList"}, method = RequestMethod.GET)
    Response getFollowingUserList(@RequestParam("currentPage") Integer currentPage,
                                  @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = {"/followedUserList"}, method = RequestMethod.GET)
    Response getFollowedUserList(@RequestParam("currentPage") Integer currentPage,
                                 @RequestParam("pageSize") Integer pageSize);
}
