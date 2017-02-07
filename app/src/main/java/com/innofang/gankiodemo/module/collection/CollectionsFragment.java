package com.innofang.gankiodemo.module.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innofang.gankiodemo.R;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 10:51
 * Description:
 * 个人收藏
 */

public class CollectionsFragment extends Fragment{
    private static final String TAG = "CollectionsFragment";

    public static Fragment newInstance(){
        return new CollectionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collections, container, false);
    }
}
