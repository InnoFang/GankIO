package com.innofang.gankiodemo.module.main.dailygank.datepicker;

import android.util.Log;

import com.innofang.gankiodemo.bean.GankDate;
import com.innofang.gankiodemo.http.Api;

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
        Api.getGankService().getHistory()
                .subscribeOn(Schedulers.io())
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
