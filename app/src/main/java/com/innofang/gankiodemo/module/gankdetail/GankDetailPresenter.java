package com.innofang.gankiodemo.module.gankdetail;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.innofang.gankiodemo.App;
import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.GankDetail;
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
 * Time: 2017/2/6 15:19
 * Description:
 */

public class GankDetailPresenter implements GankDetailContract.Presenter{
    private static final String TAG = "GankDetailPresenter";

    private GankDetailContract.View mView;

    public GankDetailPresenter(GankDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadingGank(final String url, final ImageView imageView) {
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
                        List<GankDetail.ResultsBean.福利Bean> luck = value.getResults().get福利();
                        if (null != luck) {
                            String imgUrl = luck.get(0).getUrl();
                            Glide.with(App.getContext())
                                    .load(imgUrl)
                                    .placeholder(R.drawable.default_nav_img)
                                    .into(imageView);
                        }
                        showGankOfAndroid(value.getResults().getAndroid());
                        showGankOfIOS(value.getResults().getIOS());
                        showGankOfWeb(value.getResults().get前端());
                        showGankOfExpand(value.getResults().get拓展资源());
                        showGankOfRecommend(value.getResults().get瞎推荐());
                        showGankOfApp(value.getResults().getApp());
                        showGankOfVideo(value.getResults().get休息视频());
                        mView.setLoadingIndicator(false);

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
    public void showGankOfAndroid(List<GankDetail.ResultsBean.AndroidBean> list) {
        mView.showGankOfAndroid(list);
    }

    @Override
    public void showGankOfIOS(List<GankDetail.ResultsBean.IOSBean> list) {
        mView.showGankOfIOS(list);
    }

    @Override
    public void showGankOfApp(List<GankDetail.ResultsBean.AppBean> list) {
        mView.showGankOfApp(list);
    }

    @Override
    public void showGankOfVideo(List<GankDetail.ResultsBean.休息视频Bean> list) {
        mView.showGankOfVideo(list);
    }

    @Override
    public void showGankOfWeb(List<GankDetail.ResultsBean.前端Bean> list) {
        mView.showGankOfWeb(list);
    }

    @Override
    public void showGankOfExpand(List<GankDetail.ResultsBean.拓展资源Bean> list) {
        mView.showGankOfExpand(list);
    }

    @Override
    public void showGankOfRecommend(List<GankDetail.ResultsBean.瞎推荐Bean> list) {
        mView.showGankOfRecommend(list);
    }


    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }

}
