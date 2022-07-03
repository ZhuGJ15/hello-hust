package com.hust.hello.service;

import com.hust.hello.common.model.dto.BarPostListDTO;
import com.hust.hello.common.model.entity.HelloPostConfig;
import com.hust.hello.dao.mapper.customer.HelloPostConfigCustomMapper;
import com.hust.hello.service.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/3/28 10:05
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    HelloPostConfigCustomMapper postConfigCustomMapper;

    @Test
    public void test() {
        try{
            System.out.println("test: start--------------");
//            System.out.println(postService.getPostByPostId("post_test_002"));
//            postService.getPostList("zhuganjun", 1, 6);
//            System.out.println(postConfigCustomMapper.getPostAmountByUserId("zhuganjun"));
//            List<BarPostListDTO> barPostList = postConfigCustomMapper.getPostListByBarId("bar_admin_001", 1, 3);
//            for(BarPostListDTO post : barPostList){
//                System.out.println(post);
//            }
            HelloPostConfig post = postConfigCustomMapper.getPostDeleteConfig("post_test_001");
            System.out.println(post.getPostId());
            System.out.println(post.getPostText());
            System.out.println("test: end ---------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
