package com.innofang.gankiodemo.module.main.dailygank.datepicker;

import com.innofang.gankiodemo.module.base.BasePresenter;
import com.innofang.gankiodemo.module.base.BaseView;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/8 12:49
 * Description:
 */

public class GankDatePickerContract {

    interface Presenter extends BasePresenter{
        void loadingGankDate();
    }

    interface View extends BaseView<Presenter>{
        void showGankDate(List<String> dates);
    }
}
