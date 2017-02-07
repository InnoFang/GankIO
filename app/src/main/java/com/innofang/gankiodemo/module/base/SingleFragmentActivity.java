package com.innofang.gankiodemo.module.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 00:05
 * Description:
 */

public abstract class SingleFragmentActivity extends BaseActivity {

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    protected abstract Fragment createFragment();

    @LayoutRes
    protected abstract int getLayoutResId();

    @IdRes
    protected abstract int getFragmentContainerId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(getFragmentContainerId());

        if (null == mFragment) {
            mFragment = createFragment();
            mFragmentManager.beginTransaction()
                    .add(getFragmentContainerId(), mFragment)
                    .commit();
        }

        init();
    }

    public void init() {}


    /**
     * 转换Fragment
     *  首先当前存在Fragment，并且当前Fragment不是要转换的Fragment，避免重复操作
     * @param fragment 需要转换的Fargment
     */
    public void switchFragment(Fragment fragment) {
        if (mFragment == null
                || !fragment.getClass().getName().equals(mFragment.getClass().getName())) {
            mFragmentManager.beginTransaction()
                    .replace(getFragmentContainerId(), fragment)
                    .commit();
            mFragment = fragment;
        }
    }
}
