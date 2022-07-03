package com.hust.hello.common.utils;

import com.hust.hello.common.enums.ConstellationEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: zhuganjun ©
 * @data: 2021/9/19 22:22
 * @description: 时间处理工具类
 */
@Slf4j
public class HelloTimeUtils {

    /**
     * 获取当前时间的字符串格式
     */
    public static String getTimeString(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    /**
     * 获取用户星座
     */
    public static String getConstellation(Date birthDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDay);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String res = "";
        switch (month) {
            case 1:
                res = day < 21 ? ConstellationEnum.CAPRICORN.getName() : ConstellationEnum.AQUARIUS.getName();
                break;
            case 2:
                res = day < 20 ? ConstellationEnum.AQUARIUS.getName() : ConstellationEnum.PISCES.getName();
                break;
            case 3:
                res = day < 21 ? ConstellationEnum.PISCES.getName() : ConstellationEnum.ARIES.getName();
                break;
            case 4:
                res = day < 21 ? ConstellationEnum.ARIES.getName() : ConstellationEnum.TAURUS.getName();
                break;
            case 5:
                res = day < 22 ? ConstellationEnum.TAURUS.getName() : ConstellationEnum.GEMINI.getName();
                break;
            case 6:
                res = day < 22 ? ConstellationEnum.GEMINI.getName() : ConstellationEnum.CANCER.getName();
                break;
            case 7:
                res = day < 23 ? ConstellationEnum.CANCER.getName() : ConstellationEnum.LEO.getName();
                break;
            case 8:
                res = day < 24 ? ConstellationEnum.LEO.getName() : ConstellationEnum.VIRGO.getName();
                break;
            case 9:
                res = day < 24 ? ConstellationEnum.VIRGO.getName() : ConstellationEnum.LIBRA.getName();
                break;
            case 10:
                res = day < 24 ? ConstellationEnum.LIBRA.getName() : ConstellationEnum.SCORPIO.getName();
                break;
            case 11:
                res = day < 23 ? ConstellationEnum.SCORPIO.getName() : ConstellationEnum.SAGITTARIUS.getName();
                break;
            case 12:
                res = day < 22 ? ConstellationEnum.SAGITTARIUS.getName() : ConstellationEnum.CAPRICORN.getName();
                break;
        }
        return res;
    }
}
