package com.innofang.gankiodemo.module;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.category.CategoryFragment;
import com.innofang.gankiodemo.module.collection.CollectionsFragment;
import com.innofang.gankiodemo.module.dailygank.DailyGankFragment;
import com.innofang.gankiodemo.module.setting.SettingFragment;
import com.innofang.gankiodemo.utils.ToastUtil;
import com.konifar.fab_transformation.FabTransformation;

public class MainActivity extends SingleFragmentActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private FloatingActionButton mShowNavigationFab;
    private BottomNavigationView mNavigationView;

    @Override
    protected Fragment createFragment() {
        return DailyGankFragment.newInstance();
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
    public void init() {
        super.init();
        mShowNavigationFab = (FloatingActionButton) findViewById(R.id.show_navigation_fab);
        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        mNavigationView.setOnNavigationItemSelectedListener(this);
        mShowNavigationFab.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mHandler.removeCallbacks(mRunnable);
        switch (item.getItemId()) {
            case R.id.nav_daily_gank:
                switchFragment(DailyGankFragment.newInstance());
                break;
            case R.id.nav_category:
                switchFragment(CategoryFragment.newInstance());
                break;
//            case R.id.nav_luck:
//                switchFragment(LuckFragment.newInstance());
//                break;
            case R.id.nav_collections:
                switchFragment(CollectionsFragment.newInstance());
                break;
            case R.id.nav_setting:
                switchFragment(SettingFragment.newInstance());
                break;
        }
        mHandler.postDelayed(mRunnable, 5000);
        return true;
    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        DailyGankFragment fragment = (DailyGankFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null && fragment.isVisible() && fragment.onBackPressed()) {
            return;
        } else if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
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
                        .transformTo(mNavigationView);
                break;
        }
    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            FabTransformation.with(mShowNavigationFab)
                    .transformFrom(mNavigationView);
        }
    };
}
