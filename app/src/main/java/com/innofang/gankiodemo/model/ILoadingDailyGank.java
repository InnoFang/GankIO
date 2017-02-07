package com.innofang.gankiodemo.model;

import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.event.LoadingStateCallback;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 15:43
 * Description:
 */


public interface ILoadingDailyGank {

    void loadingDailyGank(LoadingStateCallback<Luck.ResultsBean> listener);

}
