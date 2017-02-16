package com.innofang.gankiodemo.utils;

/**
 * Author: Inno Fang
 * Time: 2017/2/4 11:45
 * Description:
 */

public class StringFormatUtil {

    // 只用于从Gank.io中提取干货上传时间
    public static String formatPublishAt(String publishAt) {
        String date = publishAt.substring(0, 10);
        return date.replace('-', '/');
    }

    // 只用于从Gank.io妹子图的url中提取图片文件名
    public static String formatIamgeFileName(String url) {
        String[] element = url.split("/");
        String imgName = null;
        for (String string : element) {
            if (string.contains(".jpg")) {
                if (string.contains("?")) {
                    imgName = string.substring(0, string.lastIndexOf("?"));
                } else {
                    imgName = string.substring(0, string.lastIndexOf(".")) + ".jpg";
                }
            }
        }
        return imgName;
    }
}
