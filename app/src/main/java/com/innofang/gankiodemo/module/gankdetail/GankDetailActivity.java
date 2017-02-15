package com.innofang.gankiodemo.module.gankdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.SingleFragmentActivity;

public class GankDetailActivity extends SingleFragmentActivity {

    private static final String EXTRA_GANK_URL =
            "com.innofang.gankiodemo.ui.activity.gank_url";

    @Override
    protected Fragment createFragment() {
        String url = getIntent().getStringExtra(EXTRA_GANK_URL);
        return GankDetailFragment.newInstance(url);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(EXTRA_GANK_URL, url);
        return intent;
    }

    @Override
    public void init() {
        super.init();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
