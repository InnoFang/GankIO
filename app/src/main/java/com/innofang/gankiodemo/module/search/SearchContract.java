package com.innofang.gankiodemo.module.search;

import com.innofang.gankiodemo.bean.GankSearch;
import com.innofang.gankiodemo.module.base.BasePresenter;
import com.innofang.gankiodemo.module.base.BaseView;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 17:09
 * Description:
 */

public class SearchContract {

    public interface Presenter extends BasePresenter{

        void requestSearchResult(String query);

    }

    public interface View extends BaseView<Presenter>{

        void showSearchResult(List<GankSearch.ResultsBean> list);

        void setLoadingState(boolean active);

        void showEmptyOrError(String msg);

    }
}
