package com.innofang.gankiodemo.module.main.category;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.innofang.gankiodemo.App;
import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.http.Api;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Inno Fang
 * Time: 2017/2/7 17:34
 * Description:
 */

public class CategoryPresenter implements CategoryContract.Presenter {
    private static final String TAG = "CategoryPresenter";

    private CategoryContract.View mView;

    public CategoryPresenter(CategoryContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void requestRandomMeizhi(final ImageView imageView) {
        Api.getGankService().getRadomLuck(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Luck>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Luck value) {
                        Log.i(TAG, "onNext: " + value.getResults().toString());
                        if (null != value.getResults()) {
                            String imgUrl = value.getResults().get(0).getUrl() + URL.REQUEST_IMAGE_POSTFIX_FOR_SPANNER;
                            mView.setImageUrl(imgUrl);
                            Log.i(TAG, "onNext: imgUrl = " + imgUrl);
                            Glide.with(App.getContext())
                                    .load(imgUrl)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.default_nav_img)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                                    .transition(new DrawableTransitionOptions().transition(R.anim.anim_scale))
                                    .into(imageView);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErrorOrEmptyInfo("图片获取失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void start() {
    }

    @Override
    public void destroy() {

    }
}
