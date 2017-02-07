package com.innofang.gankiodemo.utils;

import com.google.gson.Gson;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 16:58
 * Description:
 */

public class JSONParser{
    private static final String TAG = "JSONParser";

    public static <T extends Object> T parseJson(String jsonData, Class<T> clz){
        Gson gson = new Gson();
        return gson.fromJson(jsonData, clz);
    }
}
