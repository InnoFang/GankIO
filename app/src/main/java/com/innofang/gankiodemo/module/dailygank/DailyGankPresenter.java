package com.innofang.gankiodemo.module.dailygank;

import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.event.LoadingStateCallback;
import com.innofang.gankiodemo.model.impl.DailyGankModel;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 15:41
 * Description:
 */

public class DailyGankPresenter implements DailyGankContract.Presenter, LoadingStateCallback<Luck.ResultsBean> {
    private static final String TAG = "DailyGankPresenter";

    private DailyGankContract.View mView;
    private DailyGankModel mDailyGankModel;
    public DailyGankPresenter(DailyGankContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mDailyGankModel = new DailyGankModel();
    }

    @Override
    public void loadingDailyGank() {
        mDailyGankModel.loadingDailyGank(this);
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

    @Override
    public void onSuccess(List<Luck.ResultsBean> list) {
        showDailyGank(list);
    }

    @Override
    public void onFailure(String error) {
        mView.setLoadingIndicator(false);
        mView.showEmptyOrError(error);
    }
}
