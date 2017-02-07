package com.innofang.gankiodemo.module.category;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/7 15:36
 * Description:
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "ViewPagerAdapter";

    private String[] titles;
    private List<Fragment> mFragmentList;

    public ViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
        mFragmentList = new ArrayList<>();

    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
