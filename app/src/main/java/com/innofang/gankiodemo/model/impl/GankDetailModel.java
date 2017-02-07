package com.innofang.gankiodemo.model.impl;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.innofang.gankiodemo.App;
import com.innofang.gankiodemo.bean.GankDetail;
import com.innofang.gankiodemo.event.LoadingGankCallback;
import com.innofang.gankiodemo.http.LoadingCallback;
import com.innofang.gankiodemo.http.RemoteManager;
import com.innofang.gankiodemo.model.ILoadingGank;
import com.innofang.gankiodemo.utils.JSONParser;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Inno Fang
 * Time: 2017/2/6 15:21
 * Description:
 */

public class GankDetailModel implements ILoadingGank {
    private static final String TAG = "GankDetailModel";

    @Override
    public void loadingGank(final String url, final ImageView imageView, final LoadingGankCallback callback) {
        Observable.create(new ObservableOnSubscribe<GankDetail>() {
            @Override
            public void subscribe(final ObservableEmitter<GankDetail> e) throws Exception {
                RemoteManager.getInstance().asyncRequest(url, new LoadingCallback() {
                    @Override
                    public void onUnavailable() {

                    }

                    @Override
                    public void onLoad(String json) {
                        GankDetail gankDetail = JSONParser.parseJson(json, GankDetail.class);
                        if (null != gankDetail) {
                            Log.i(TAG, gankDetail.toString());
                            e.onNext(gankDetail);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankDetail value) {
                        Log.i(TAG, "onNext: " + value.getResults().toString());
                        callback.call(value.getResults().getAndroid(),
                                value.getResults().getIOS(),
                                value.getResults().get前端(),
                                value.getResults().get拓展资源(),
                                value.getResults().get瞎推荐(),
                                value.getResults().getApp(),
                                value.getResults().get休息视频());
                        List<GankDetail.ResultsBean.福利Bean> luck = value.getResults().get福利();
                        if (null != luck) {
                            String imgUrl = luck.get(0).getUrl();
                            Glide.with(App.getContext())
                                    .load(imgUrl)
                                    .into(imageView);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
