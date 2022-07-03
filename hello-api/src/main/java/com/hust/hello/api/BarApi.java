package com.hust.hello.api;

import com.hust.hello.common.bean.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/1/22 16:46
 * @description: 论坛模块接口
 */
@RequestMapping(value = {"/bar"})
public interface BarApi {

    @RequestMapping(value = {"/getHomePageBars"}, method = RequestMethod.GET)
    Response getHomePageBarConfig(@RequestParam("barAmount") Integer barAmount,
                                  @RequestParam("postAmount") Integer postAmount);

    @RequestMapping(value = {"/getBarsWithPage"}, method = RequestMethod.GET)
    Response getBarConfigList(@RequestParam("currentPage") Integer currentPage,
                              @RequestParam("pageSize") Integer pageSize,
                              @RequestParam("postAmount") Integer postAmount);

    @RequestMapping(value = {"/getBarConfig"}, method = RequestMethod.GET)
    Response getBarConfig(@RequestParam("barId") String barId);

    @RequestMapping(value = {"/getHotBars"}, method = RequestMethod.GET)
    Response getHotBarList(@RequestParam("barAmount") Integer barAmount);

    @RequestMapping(value = {"/getPostList"}, method = RequestMethod.GET)
    Response getPostListByBarId(@RequestParam("barId") String barId,
                                @RequestParam("currentPage") Integer currentPage,
                                @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = {"/getAvatarUrl"}, method = RequestMethod.GET)
    Response getAvatar(@RequestParam("barId") String barId);

    @RequestMapping(value = {"/getNameList"}, method = RequestMethod.GET)
    Response getBarNameList();

    @RequestMapping(value = {"/applyNew"}, method = RequestMethod.GET)
    Response applyNewBar(@RequestParam("barName") String barName,
                         @RequestParam("remark") String remark);
}
