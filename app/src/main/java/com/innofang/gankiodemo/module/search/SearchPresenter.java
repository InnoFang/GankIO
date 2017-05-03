package com.innofang.gankiodemo.module.search;

import android.text.TextUtils;
import android.util.Log;

import com.innofang.gankiodemo.bean.GankSearch;
import com.innofang.gankiodemo.http.Api;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 17:13
 * Description:
 */

public class SearchPresenter implements SearchContract.Presenter {
    private static final String TAG = "SearchPresenter";

    private SearchContract.View mView;

    public SearchPresenter(SearchContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void requestSearchResult(final String query) {
        if (TextUtils.isEmpty(query)) {
            mView.setLoadingState(false);
            return;
        }
        mView.setLoadingState(true);
        Api.getGankService().getGankSearch(query, 50, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankSearch>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankSearch value) {
                        if (null != value.getResults()) {
                            Log.i(TAG, "onNext: gankResult = " + value.getResults().toString());
                            mView.showSearchResult(value.getResults());
                        }
                        mView.setLoadingState(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setLoadingState(false);
                        mView.showEmptyOrError(e.getMessage());
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
