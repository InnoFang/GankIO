package com.innofang.gankiodemo.http;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 17:37
 * Description:
 */


public interface LoadingCallback {
    void onUnavailable();

    void onLoad(String json);
}
