package com.innofang.gankiodemo.module.search;

import android.text.TextUtils;
import android.util.Log;

import com.innofang.gankiodemo.bean.GankSearch;
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
        if (TextUtils.isEmpty(query)){
            mView.setLoadingState(false);
            return;
        }
        mView.setLoadingState(true);
        Observable.create(new ObservableOnSubscribe<GankSearch>() {
            @Override
            public void subscribe(final ObservableEmitter<GankSearch> e) throws Exception {
                RemoteManager.getInstance().asyncRequest(URL.getSearchData(query), new LoadingCallback() {
                    @Override
                    public void onUnavailable() {
                        mView.setLoadingState(false);
                        mView.showEmptyOrError("干货查询失败");
                    }

                    @Override
                    public void onLoad(String json) {
                        GankSearch  gankSearch = JSONParser.parseJson(json, GankSearch.class);
                        if (null != gankSearch){
                            Log.i(TAG, "onLoad: ganksearch " + gankSearch.toString());
                            e.onNext(gankSearch);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankSearch>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankSearch value) {
                        if (null != value.getResults()){
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
