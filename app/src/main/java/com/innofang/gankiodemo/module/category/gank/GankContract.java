package com.innofang.gankiodemo.module.category.gank;

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


        void requestGank(String category, int number);
    }

    interface View extends BaseView<Presenter>{
        // 显示加载指示器
        void setLoadingIndicator(boolean active);

        void showGank(List<Gank.ResultsBean> list);

        void showEmptyOrError(String msg);

        // 请求数据分类
        String getCategory();

        // 请求数据个数
        int getNumber();
    }
}
