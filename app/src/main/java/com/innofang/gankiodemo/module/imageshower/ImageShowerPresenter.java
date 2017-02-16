package com.innofang.gankiodemo.module.imageshower;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.innofang.gankiodemo.widget.DragImageView;

/**
 * Author: Inno Fang
 * Time: 2017/2/15 21:39
 * Description:
 */

public class ImageShowerPresenter implements ImageShowerContract.Presenter {
    private static final String TAG = "ImageShowerPresenter";

    private ImageShowerContract.View mView;

    public ImageShowerPresenter(ImageShowerContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void loadingIamge(Context context, DragImageView imageView, String url) {
        Glide.with(context)
                .load(url)
//                .placeholder(R.drawable.image_default)
                .into(imageView);
        mView.setLoadingIndicator(false);
    }

    @Override
    public void shareImage() {

    }

    @Override
    public void downloadImage() {

    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
