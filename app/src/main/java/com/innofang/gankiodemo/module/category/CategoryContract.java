package com.innofang.gankiodemo.module.category;

import android.widget.ImageView;

import com.innofang.gankiodemo.module.BasePresenter;
import com.innofang.gankiodemo.module.BaseView;

/**
 * Author: Inno Fang
 * Time: 2017/2/7 16:35
 * Description:
 */

public class CategoryContract {

    interface Presenter extends BasePresenter {
        void requestRandomMeizhi(ImageView imageView);
    }

    interface View extends BaseView<Presenter> {

        void showMeizhi();

        void showErrorOrEmptyInfo(String info);

        void setImageUrl(String url);

        String getImageUrl();
    }
}
