package com.innofang.gankiodemo.module.main.collection.collectiongank;

import com.innofang.gankiodemo.bean.Collection;
import com.innofang.gankiodemo.module.base.BasePresenter;
import com.innofang.gankiodemo.module.base.BaseView;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 22:56
 * Description:
 */

public class CollectionGankContract {

    interface Presenter extends BasePresenter {

        void queryCollections(String type);

    }

    interface View extends BaseView<Presenter>{

        void setEmptyViewState(boolean state);

        String getType();

        void showCollectionGank(List<Collection> collections);

    }

}
