package com.innofang.gankiodemo.module.imageshower;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.FragmentContainerActivity;

public class ImageShowerActivity extends FragmentContainerActivity {

    private static final String EXTRA_URL = "com.innofang.gankiodemo.module.showpicture.url";

    public static Intent newIntent(Context context, String url){
        Intent intent = new Intent(context, ImageShowerActivity.class);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String url = getIntent().getStringExtra(EXTRA_URL);
        return ImageShowerFragment.newInstance(url);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

}
