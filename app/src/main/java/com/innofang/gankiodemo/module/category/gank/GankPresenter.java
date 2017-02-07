package com.innofang.gankiodemo.module.category.gank;

import android.util.Log;

import com.innofang.gankiodemo.bean.Gank;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.http.LoadingCallback;
import com.innofang.gankiodemo.http.RemoteManager;
import com.innofang.gankiodemo.utils.JSONParser;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Inno Fang
 * Time: 2017/2/7 20:22
 * Description:
 */

public class GankPresenter implements GankContract.Presenter {
    private static final String TAG = "GankPresenter";

    private GankContract.View mView;

    public GankPresenter(GankContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        requestGank(mView.getCategory(), mView.getNumber());
    }

    @Override
    public void destroy() {

    }

    @Override
    public void requestGank(final String category, final int number) {
        Observable.create(new ObservableOnSubscribe<Gank>() {
            @Override
            public void subscribe(final ObservableEmitter<Gank> e) throws Exception {
                RemoteManager.getInstance().asyncRequest(
                        URL.getCategoryData(category, number), new LoadingCallback() {
                            @Override
                            public void onUnavailable() {
                                mView.showEmptyOrError("加载失败");
                                mView.setLoadingIndicator(false);
                            }

                            @Override
                            public void onLoad(String json) {
                                Gank gank = JSONParser.parseJson(json, Gank.class);
                                if (null != gank) {
                                    Log.i(TAG, "gank = " + gank);
                                    e.onNext(gank);
                                }
                            }
                        });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Gank>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Gank value) {
                        Log.i(TAG, "onNext: " + value.getResults().toString());
                        if (null != value.getResults()){
                            mView.showGank(value.getResults());
                        } else {
                            Log.i(TAG, "value.getResult() == null");
                        }
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showEmptyOrError("加载失败");
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
