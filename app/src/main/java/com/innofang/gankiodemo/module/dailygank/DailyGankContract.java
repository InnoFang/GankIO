package com.innofang.gankiodemo.module.dailygank;

import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.module.base.BasePresenter;
import com.innofang.gankiodemo.module.base.BaseView;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 12:32
 * Description:
 */

public class DailyGankContract {

    public interface Presenter extends BasePresenter {
        void loadingDailyGank();

        void showDailyGank(List<Luck.ResultsBean> list);
    }

    public interface View extends BaseView<Presenter>{

        // 显示加载指示器
        void setLoadingIndicator(boolean active);

        void showDailyGank(List<Luck.ResultsBean> list);

        void showEmptyOrError(String error);

    }
}
