package com.innofang.gankiodemo.module.gankdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.base.FragmentContainerActivity;

public class GankDetailActivity extends FragmentContainerActivity {

    private static final String EXTRA_GANK_DATE =
            "com.innofang.gankiodemo.ui.activity.gank_date";

    @Override
    protected Fragment createFragment() {
        String date = getIntent().getStringExtra(EXTRA_GANK_DATE);
        return GankDetailFragment.newInstance(date);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    public static Intent newIntent(Context context, String date) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(EXTRA_GANK_DATE, date);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

}
