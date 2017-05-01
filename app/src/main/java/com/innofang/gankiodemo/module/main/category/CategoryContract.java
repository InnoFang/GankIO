package com.innofang.gankiodemo.module.main.category;

import android.widget.ImageView;

import com.innofang.gankiodemo.module.base.BasePresenter;
import com.innofang.gankiodemo.module.base.BaseView;

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
