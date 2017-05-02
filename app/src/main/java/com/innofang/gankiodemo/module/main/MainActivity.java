package com.innofang.gankiodemo.module.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.base.FragmentContainerActivity;
import com.innofang.gankiodemo.module.main.category.CategoryFragment;
import com.innofang.gankiodemo.module.main.collection.CollectionsFragment;
import com.innofang.gankiodemo.module.main.dailygank.DailyGankFragment;
import com.innofang.gankiodemo.module.main.setting.SettingFragment;
import com.innofang.gankiodemo.utils.BottomNavigationViewHelper;
import com.innofang.gankiodemo.utils.ToastUtil;
import com.konifar.fab_transformation.FabTransformation;

import java.util.HashMap;

public class MainActivity extends FragmentContainerActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String KEY_DAILY_GANK = "DailyGankFragment";
    private static final String KEY_COLLECTIONS = "CollectionFragment";
    private static final String KEY_CATEGORY = "CategoryFragment";
    private static final String KEY_SETTING = "SettingFragment";


    public FloatingActionButton showNavigationFab;
    public BottomNavigationView navigationView;
    private HashMap<String, Fragment> mFragmentCache = new HashMap<>();

    @Override
    protected Fragment createFragment() {
        return getFragmentFromCache(KEY_DAILY_GANK);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNavigationFab = (FloatingActionButton) findViewById(R.id.show_navigation_fab);
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);
        showNavigationFab.setOnClickListener(this);
//        setTransition();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mHandler.removeCallbacks(mRunnable);
        switch (item.getItemId()) {
            case R.id.nav_daily_gank:
                switchFragment(getFragmentFromCache(KEY_DAILY_GANK));
//                setTransition();
                break;
            case R.id.nav_category:
                switchFragment(getFragmentFromCache(KEY_CATEGORY));
                break;
            case R.id.nav_collections:
                switchFragment(getFragmentFromCache(KEY_COLLECTIONS));
                break;
            case R.id.nav_setting:
                switchFragment(getFragmentFromCache(KEY_SETTING));
                break;
        }
        mHandler.postDelayed(mRunnable, 5000);
        return true;
    }

    public Fragment getFragmentFromCache(String key) {
        if (!mFragmentCache.containsKey((key))) {
            Fragment fragment;
            if (key.equals(KEY_DAILY_GANK))
                fragment = DailyGankFragment.newInstance();
            else if (key.equals(KEY_CATEGORY))
                fragment = CategoryFragment.newInstance();
            else if (key.equals(KEY_COLLECTIONS))
                fragment = CollectionsFragment.newInstance();
            else
                fragment = SettingFragment.newInstance();
            mFragmentCache.put(key, fragment);
        }
        return mFragmentCache.get(key);
    }

    private void setTransition() {
        Transition transition = TransitionInflater
                .from(this)
//                .inflateTransition(R.transition.explode); /* 爆炸效果 */
                .inflateTransition(R.transition.slide); /* 滑动效果 */
//                .inflateTransition(R.transition.fade); /* 淡化效果 */

        /* 退出时使用 */
        getWindow().setExitTransition(transition);
        /* 再次进入时使用 */
        getWindow().setReenterTransition(transition);
        /* 第一次进入时使用 */
        getWindow().setEnterTransition(transition);
    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        // 用于DailyGankFragment的DrawerLayout的关闭
        DailyGankFragment fragment = (DailyGankFragment) getFragmentFromCache(KEY_DAILY_GANK);
        if (fragment != null && fragment.isVisible() && fragment.onBackPressed()) {
            return;
        }
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            // 清空 Fragment 缓存，防止泄露
            mFragmentCache.clear();
            super.onBackPressed();
            return;
        } else {
            ToastUtil.showToast("再点击一次退出");
        }
        mBackPressed = System.currentTimeMillis();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_navigation_fab:
                FabTransformation.with(v)
                        .duration(500)
                        .setListener(new FabTransformation.OnTransformListener() {
                            @Override
                            public void onStartTransform() {

                            }

                            @Override
                            public void onEndTransform() {
                                mHandler.postDelayed(mRunnable, 5000);
                            }
                        })
                        .transformTo(navigationView);
                break;
        }
    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            FabTransformation.with(showNavigationFab)
                    .transformFrom(navigationView);
        }
    };
}
