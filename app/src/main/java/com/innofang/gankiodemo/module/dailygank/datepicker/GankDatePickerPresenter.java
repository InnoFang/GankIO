package com.innofang.gankiodemo.module.dailygank.datepicker;

import android.util.Log;

import com.innofang.gankiodemo.bean.GankDate;
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
 * Time: 2017/2/8 12:51
 * Description:
 */

public class GankDatePickerPresenter implements GankDatePickerContract.Presenter {
    private static final String TAG = "GankDatePickerPresenter";

    private GankDatePickerContract.View mView;

    public GankDatePickerPresenter(GankDatePickerContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadingGankDate() {
        Observable.create(new ObservableOnSubscribe<GankDate>() {
            @Override
            public void subscribe(final ObservableEmitter<GankDate> e) throws Exception {
                RemoteManager.getInstance().asyncRequest(URL.HISTORY, new LoadingCallback() {
                    @Override
                    public void onUnavailable() {

                    }

                    @Override
                    public void onLoad(String json) {
                        GankDate gankDate = JSONParser.parseJson(json, GankDate.class);
                        if (null != gankDate){
                            Log.i(TAG, "onLoad: " + gankDate);
                            e.onNext(gankDate);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankDate>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankDate value) {
                        if (null != value.getResults()){
                            Log.i(TAG, "onNext: " + value.getResults().toString());
                            mView.showGankDate(value.getResults());
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

    @Override
    public void start() {
        loadingGankDate();
    }

    @Override
    public void destroy() {

    }
}
