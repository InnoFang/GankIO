package com.innofang.gankiodemo.module.gankdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Transition transition = TransitionInflater
                .from(this)
                .inflateTransition(R.transition.slide); /* 滑动效果 */

        /* 退出时使用 */
        getWindow().setExitTransition(transition);
        /* 第一次进入时使用 */
        getWindow().setEnterTransition(transition);
    }

}
