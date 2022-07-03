package com.hust.hello.api;

import com.hust.hello.api.param.PostCreateParam;
import com.hust.hello.api.param.PostReportParam;
import com.hust.hello.api.param.PostUpdateParam;
import com.hust.hello.common.bean.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/26 20:59
 * @description:
 */
@RequestMapping(value = {"/post"})
public interface PostApi {
    @RequestMapping(value = {"/getHotPosts"}, method = RequestMethod.GET)
    Response getHotPostList(@RequestParam("postAmount") Integer postAmount);

    @RequestMapping(value = {"/getPost"}, method = RequestMethod.GET)
    Response getPostContent(@RequestParam("postId") String postId);

    @RequestMapping(value = {"/uploadPic"}, method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response uploadPostPic(@RequestPart("postPic") MultipartFile postPic);

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    Response deletePost(@RequestParam("postId") String postId);

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    Response createPost(@RequestBody PostCreateParam postCreateParam);

    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    Response updatePost(@RequestBody PostUpdateParam postUpdateParam);

    @RequestMapping(value = {"/likePost"}, method = RequestMethod.GET)
    Response likePost(@RequestParam("postId") String postId);

    @RequestMapping(value = {"/collectPost"}, method = RequestMethod.GET)
    Response collectPost(@RequestParam("postId") String postId);

    @RequestMapping(value = {"/dislikePost"}, method = RequestMethod.GET)
    Response dislikePost(@RequestParam("postId") String postId);

    @RequestMapping(value = {"/reportPost"}, method = RequestMethod.POST)
    Response reportPost(@RequestBody PostReportParam postReportParam);

    @RequestMapping(value = {"/recommend"}, method = RequestMethod.GET)
    Response recommendPost(@RequestParam("currentPage") Integer currentPage,
                           @RequestParam("pageSize") Integer pageSize);
}
