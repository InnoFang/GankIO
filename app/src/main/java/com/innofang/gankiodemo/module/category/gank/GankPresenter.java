package com.innofang.gankiodemo.module.category.gank;

import android.util.Log;

import com.innofang.gankiodemo.bean.Gank;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.http.LoadingCallback;
import com.innofang.gankiodemo.http.RemoteManager;
import com.innofang.gankiodemo.utils.JSONParser;

import java.util.ArrayList;
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
 * Time: 2017/2/7 20:22
 * Description:
 */

public class GankPresenter implements GankContract.Presenter {
    private static final String TAG = "GankPresenter";

    private static int sPage = 0;
    private List<Gank.ResultsBean> mList;
    private GankContract.View mView;

    public GankPresenter(GankContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mList = new ArrayList<>();
        sPage = 0;
    }

    @Override
    public void requestGank(final String category) {
        sPage++;
        Observable.create(new ObservableOnSubscribe<Gank>() {
            @Override
            public void subscribe(final ObservableEmitter<Gank> e) throws Exception {
                RemoteManager.getInstance().asyncRequest(
                        URL.getCategoryData(category, sPage), new LoadingCallback() {
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
                        if (null != value.getResults()) {
                            if (mList.size() == 0){
                                mList.addAll(value.getResults());
                            } else {
                                // 删除footer
                                mList.remove(mList.size() - 1);
                                mList.addAll(value.getResults());
                            }
                            // 用于添加footer
                            mList.add(new Gank.ResultsBean());
                            mView.showGank(mList);
                            mView.setLoadingIndicator(false);
                            mView.setPullUpLoadingState(false);
                        }

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

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
