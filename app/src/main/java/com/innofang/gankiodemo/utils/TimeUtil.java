package com.innofang.gankiodemo.utils;

/**
 * Author: Inno Fang
 * Time: 2017/2/4 11:45
 * Description:
 */

public class TimeUtil {

    public static String formatPublishAt(String publishAt){
        String date = publishAt.substring(0, 10);
        return date.replace('-', '/');
    }

}
