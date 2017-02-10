package com.innofang.gankiodemo.module.dailygank.datepicker;

import com.innofang.gankiodemo.module.BasePresenter;
import com.innofang.gankiodemo.module.BaseView;

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
