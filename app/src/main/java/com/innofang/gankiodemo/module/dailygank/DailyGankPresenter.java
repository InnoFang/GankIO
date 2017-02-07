package com.innofang.gankiodemo.module.dailygank;

import android.util.Log;

import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.http.LoadingCallback;
import com.innofang.gankiodemo.http.RemoteManager;
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
 * Time: 2017/2/5 15:41
 * Description:
 */

public class DailyGankPresenter implements DailyGankContract.Presenter {
    private static final String TAG = "DailyGankPresenter";

    private DailyGankContract.View mView;
    public DailyGankPresenter(DailyGankContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadingDailyGank() {
        Observable.create(new ObservableOnSubscribe<Luck>() {
            @Override
            public void subscribe(final ObservableEmitter<Luck> e) throws Exception {
                RemoteManager.getInstance().asyncRequest(URL.DATA_LUCK, new LoadingCallback() {
                    @Override
                    public void onUnavailable() {
                        mView.setLoadingIndicator(false);
                        mView.showEmptyOrError("加载失败");
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
                            showDailyGank(value.getResults());
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
    public void showDailyGank(List<Luck.ResultsBean> list) {
        mView.showDailyGank(list);
        mView.setLoadingIndicator(false);
    }

    @Override
    public void start() {
        loadingDailyGank();
    }

    @Override
    public void destroy() {

    }

}
