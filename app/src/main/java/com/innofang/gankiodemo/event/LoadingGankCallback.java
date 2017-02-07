package com.innofang.gankiodemo.event;

import com.innofang.gankiodemo.bean.GankDetail;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/6 15:50
 * Description:
 */


public interface LoadingGankCallback {
    void call(List<GankDetail.ResultsBean.AndroidBean> androids,
              List<GankDetail.ResultsBean.IOSBean> ioss,
              List<GankDetail.ResultsBean.前端Bean> webs,
              List<GankDetail.ResultsBean.拓展资源Bean> ers,
              List<GankDetail.ResultsBean.瞎推荐Bean> recommends,
              List<GankDetail.ResultsBean.AppBean> apps,
              List<GankDetail.ResultsBean.休息视频Bean> videos
              );
}
