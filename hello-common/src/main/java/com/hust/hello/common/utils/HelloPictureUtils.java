package com.hust.hello.common.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright zhuganjun ©. All Rights Reserved.
 *
 * @author: zhuganjun
 * @date: 2022/4/5 17:36
 * @description:
 */
@Slf4j
public class HelloPictureUtils {
    private static Integer PIC_WIDTH = 120;
    private static Integer PIC_HEIGHT = 120;

    public static InputStream compressPicture(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // todo 这里要做一下判断，如果压缩失败？
        Thumbnails.of(inputStream)
                .size(PIC_WIDTH, PIC_HEIGHT)
                .toOutputStream(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
