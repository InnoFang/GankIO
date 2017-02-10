package com.innofang.gankiodemo.module;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.category.CategoryFragment;
import com.innofang.gankiodemo.module.collection.CollectionsFragment;
import com.innofang.gankiodemo.module.dailygank.DailyGankFragment;
import com.innofang.gankiodemo.module.luck.LuckFragment;
import com.innofang.gankiodemo.module.search.SearchFragment;
import com.innofang.gankiodemo.module.setting.SettingFragment;

public class MainActivity extends SingleFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

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


    @SuppressWarnings("deprecation")
    @Override
    public void init() {
        super.init();

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_daily_gank:
                setActionBarState(true);
                switchFragment(DailyGankFragment.newInstance());
                break;
            case R.id.nav_category:
                setActionBarState(false);
                switchFragment(CategoryFragment.newInstance());
                break;
            case R.id.nav_luck:
                setActionBarState(true);
                switchFragment(LuckFragment.newInstance());
                break;
            case R.id.nav_search_gank:
                setActionBarState(true);
                switchFragment(SearchFragment.newInstance());
                break;
            case R.id.nav_collections:
                setActionBarState(false);
                switchFragment(CollectionsFragment.newInstance());
                break;
            case R.id.nav_setting:
                setActionBarState(true);
                switchFragment(SettingFragment.newInstance());
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setActionBarState(boolean state) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null ){
            if(!state) {
                actionBar.hide();
            } else {
                actionBar.show();
            }
        }
    }
}
