package com.innofang.gankiodemo.module;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 00:05
 * Description:
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    protected abstract Fragment createFragment();

    @LayoutRes
    protected abstract int getLayoutResId();

    @IdRes
    protected abstract int getFragmentContainerId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View .SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
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
