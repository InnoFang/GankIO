package com.innofang.gankiodemo.model.impl;

import android.util.Log;

import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.http.LoadingCallback;
import com.innofang.gankiodemo.http.RemoteManager;
import com.innofang.gankiodemo.model.ILoadingDailyGank;
import com.innofang.gankiodemo.utils.JSONParser;
import com.innofang.gankiodemo.event.LoadingStateCallback;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 15:42
 * Description:
 */

public class DailyGankModel implements ILoadingDailyGank {
    private static final String TAG = "DailyGankModel";

    @Override
    public void loadingDailyGank(final LoadingStateCallback<Luck.ResultsBean> listener) {
        Observable.create(new ObservableOnSubscribe<Luck>() {
            @Override
            public void subscribe(final ObservableEmitter<Luck> e) throws Exception {
                RemoteManager.getInstance().asyncRequest(URL.DATA_LUCK, new LoadingCallback() {
                    @Override
                    public void onUnavailable() {
                        listener.onFailure(null);
                    }

                    @Override
                    public void onLoad(String json) {
                        Luck luck = JSONParser.parseJson(json, Luck.class);
                        if (null != luck) {
                            Log.i(TAG, luck.toString());
                            e.onNext(luck);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Luck>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Luck value) {
                        Log.i(TAG, "onNext: " + value.getResults().toString());
                        if (null != value.getResults()) {
                            listener.onSuccess(value.getResults());
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
