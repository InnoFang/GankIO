package com.innofang.gankiodemo.module.main.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.base.BaseFragment;
import com.innofang.gankiodemo.module.main.category.ViewPagerAdapter;
import com.innofang.gankiodemo.module.main.collection.collectiongank.CollectionGankFragment;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 10:51
 * Description:
 * 个人收藏
 */

public class CollectionsFragment extends BaseFragment{
    private static final String TAG = "CollectionsFragment";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static Fragment newInstance(){
        return new CollectionsFragment();
    }
    

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collections;
    }

    @Override
    protected void createView(View view, @Nullable Bundle savedInstanceState) {
        mViewPager = (ViewPager) find(R.id.view_pager);
        mTabLayout = (TabLayout) find(R.id.tab_layout);
        String[] titles = {"Android", "iOS", "前端", "拓展资源", "瞎推荐", "App", "休息视频"};
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), titles);

        CollectionGankFragment androidFragment = CollectionGankFragment.newInstance("Android");
        CollectionGankFragment iOSFragment = CollectionGankFragment.newInstance("iOS");
        CollectionGankFragment webFragment = CollectionGankFragment.newInstance("前端");
        CollectionGankFragment expandResourceFragment = CollectionGankFragment.newInstance("拓展资源");
        CollectionGankFragment recommendFragment = CollectionGankFragment.newInstance("瞎推荐");
        CollectionGankFragment appFragment = CollectionGankFragment.newInstance("App");
        CollectionGankFragment videoFragment = CollectionGankFragment.newInstance("休息视频");

        adapter.addFragment(androidFragment);
        adapter.addFragment(iOSFragment);
        adapter.addFragment(webFragment);
        adapter.addFragment(expandResourceFragment);
        adapter.addFragment(recommendFragment);
        adapter.addFragment(appFragment);
        adapter.addFragment(videoFragment);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager.setCurrentItem(0);
    }
}
