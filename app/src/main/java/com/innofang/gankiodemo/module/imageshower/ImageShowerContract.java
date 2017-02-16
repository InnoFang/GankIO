package com.innofang.gankiodemo.module.imageshower;

import android.content.Context;

import com.innofang.gankiodemo.module.BasePresenter;
import com.innofang.gankiodemo.module.BaseView;
import com.innofang.gankiodemo.widget.DragImageView;

/**
 * Author: Inno Fang
 * Time: 2017/2/15 21:36
 * Description:
 */

public class ImageShowerContract {

    interface Presenter extends BasePresenter {

        void loadingIamge(Context context, DragImageView imageView, String url);

        void shareImage();

        void downloadImage();

    }

    interface View extends BaseView<Presenter> {

        void showMessage(String msg);

        void setLoadingIndicator(boolean active);

    }

}