package com.innofang.gankiodemo.module.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innofang.gankiodemo.R;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 10:50
 * Description: 分类浏览
 */

public class CategoryFragment extends Fragment {
    private static final String TAG = "CategoryFragment";

    public static Fragment newInstance(){
        return new CategoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
}
