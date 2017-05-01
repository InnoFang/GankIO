package com.innofang.gankiodemo.module.main.category.gank;

import com.innofang.gankiodemo.bean.Gank;
import com.innofang.gankiodemo.module.base.BasePresenter;
import com.innofang.gankiodemo.module.base.BaseView;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/7 18:24
 * Description:
 */

public class GankContract {

    interface Presenter extends BasePresenter{


        void requestGank(String category);
    }

    interface View extends BaseView<Presenter>{
        // 显示加载指示器
        void setLoadingIndicator(boolean active);

        void showGank(List<Gank.ResultsBean> list);

        void showEmptyOrError(String msg);

        void setPullUpLoadingState(boolean state);

        // 请求数据分类
        String getCategory();
    }
}
