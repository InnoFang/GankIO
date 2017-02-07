package com.innofang.gankiodemo.module.gankdetail;

import android.widget.ImageView;

import com.innofang.gankiodemo.bean.GankDetail;
import com.innofang.gankiodemo.event.LoadingGankCallback;
import com.innofang.gankiodemo.model.ILoadingGank;
import com.innofang.gankiodemo.model.impl.GankDetailModel;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/6 15:19
 * Description:
 */

public class GankDetailPresenter implements GankDetailContract.Presenter, LoadingGankCallback {
    private static final String TAG = "GankDetailPresenter";

    private GankDetailContract.View mView;
    private ILoadingGank mModel;

    public GankDetailPresenter(GankDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mModel = new GankDetailModel();
    }

    @Override
    public void loadingGank(String url, ImageView imageView) {
        mModel.loadingGank(url, imageView, this);
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

    @Override
    public void call(List<GankDetail.ResultsBean.AndroidBean> androids, List<GankDetail.ResultsBean.IOSBean> ioss, List<GankDetail.ResultsBean.前端Bean> webs, List<GankDetail.ResultsBean.拓展资源Bean> ers, List<GankDetail.ResultsBean.瞎推荐Bean> recommends, List<GankDetail.ResultsBean.AppBean> apps, List<GankDetail.ResultsBean.休息视频Bean> videos) {
        showGankOfAndroid(androids);
        showGankOfIOS(ioss);
        showGankOfWeb(webs);
        showGankOfExpand(ers);
        showGankOfRecommend(recommends);
        showGankOfApp(apps);
        showGankOfVideo(videos);
        mView.setLoadingIndicator(false);
    }
}
