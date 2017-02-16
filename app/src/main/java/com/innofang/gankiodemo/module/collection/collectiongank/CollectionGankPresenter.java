package com.innofang.gankiodemo.module.collection.collectiongank;

import android.util.Log;

import com.innofang.gankiodemo.App;
import com.innofang.gankiodemo.bean.Collection;
import com.innofang.gankiodemo.database.CollectionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/11 01:20
 * Description:
 */

public class CollectionGankPresenter implements CollectionGankContract.Presenter {
    private static final String TAG = "CollectionGankPresenter";

    private CollectionGankContract.View mView;

    public CollectionGankPresenter(CollectionGankContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void queryCollections(String type) {
        CollectionManager manager = CollectionManager.getInstance(App.getContext());
        List<Collection> collections = manager.getCollections();
        List<Collection> types = new ArrayList<>();
        for (Collection collection : collections) {
            Log.i(TAG, "queryCollections: " + collection.toString());
            if (collection.getType().equals(type)) {
                types.add(collection);
            }
        }
        if (types.isEmpty()){
            mView.setEmptyViewState(true);
            mView.showCollectionGank(types);
        } else {
            mView.setEmptyViewState(false);
            mView.showCollectionGank(types);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
