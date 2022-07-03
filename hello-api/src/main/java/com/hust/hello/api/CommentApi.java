package com.hust.hello.api;

import com.hust.hello.api.param.CommentCreateParam;
import com.hust.hello.common.bean.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/14 21:05
 * @description:
 */
@RequestMapping(value = {"/comment"})
public interface CommentApi {

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    Response addComment(@RequestBody CommentCreateParam param);

    @RequestMapping(value = {"/getList"}, method = RequestMethod.GET)
    Response getCommentList(@RequestParam("postId") String postId,
                            @RequestParam(value = "currentPage", required = false) Integer currentPage,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                            @RequestParam(value = "sortType", required = false) Integer sortType);

    @RequestMapping(value = {"/get"}, method = RequestMethod.GET)
    Response getComment(@RequestParam("commentId") String commentId);

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    Response deleteComment(@RequestParam("commentId") String commentId);

    @RequestMapping(value = {"/like"}, method = RequestMethod.GET)
    Response likeComment(@RequestParam("commentId") String commentId);

    @RequestMapping(value = {"/dislike"}, method = RequestMethod.GET)
    Response dislikeComment(@RequestParam("commentId") String commentId);
}
