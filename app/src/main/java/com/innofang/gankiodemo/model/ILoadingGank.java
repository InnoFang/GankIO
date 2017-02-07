package com.innofang.gankiodemo.model;

import android.widget.ImageView;

import com.innofang.gankiodemo.event.LoadingGankCallback;

/**
 * Author: Inno Fang
 * Time: 2017/2/6 15:22
 * Description:
 */


public interface ILoadingGank {
    void loadingGank(String url, ImageView imageView, LoadingGankCallback callback);
}
