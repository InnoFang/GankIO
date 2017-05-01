package com.innofang.gankiodemo.module.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: Inno Fang
 * Time: 2017/5/1 10:08
 * Description:
 */


public abstract class BaseFragment extends Fragment {

    protected View mView;

    @LayoutRes
    public abstract int getLayoutId();

    protected abstract void createView(View view, @Nullable Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        createView(mView, savedInstanceState);
        return mView;
    }

    public View find(@IdRes int id) {
        return mView.findViewById(id);
    }
}
