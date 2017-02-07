package com.innofang.gankiodemo.module.gankdetail;

import android.widget.ImageView;

import com.innofang.gankiodemo.bean.GankDetail;
import com.innofang.gankiodemo.module.base.BasePresenter;
import com.innofang.gankiodemo.module.base.BaseView;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 23:55
 * Description:
 */

public class GankDetailContract {

    public interface Presenter extends BasePresenter{
        void loadingGank(String url, ImageView imageView);
        void showGankOfAndroid(List<GankDetail.ResultsBean.AndroidBean> list);
        void showGankOfIOS(List<GankDetail.ResultsBean.IOSBean> list);
        void showGankOfApp(List<GankDetail.ResultsBean.AppBean> list);
        void showGankOfVideo(List<GankDetail.ResultsBean.休息视频Bean> list);
        void showGankOfWeb(List<GankDetail.ResultsBean.前端Bean> list);
        void showGankOfExpand(List<GankDetail.ResultsBean.拓展资源Bean> list);
        void showGankOfRecommend(List<GankDetail.ResultsBean.瞎推荐Bean> list);
    }

    public interface View extends BaseView<Presenter>{
        // 显示加载指示器
        void setLoadingIndicator(boolean active);
        void showGankOfAndroid(List<GankDetail.ResultsBean.AndroidBean> list);
        void showGankOfIOS(List<GankDetail.ResultsBean.IOSBean> list);
        void showGankOfApp(List<GankDetail.ResultsBean.AppBean> list);
        void showGankOfVideo(List<GankDetail.ResultsBean.休息视频Bean> list);
        void showGankOfWeb(List<GankDetail.ResultsBean.前端Bean> list);
        void showGankOfExpand(List<GankDetail.ResultsBean.拓展资源Bean> list);
        void showGankOfRecommend(List<GankDetail.ResultsBean.瞎推荐Bean> list);
    }
}
