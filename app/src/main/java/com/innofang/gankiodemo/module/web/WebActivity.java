package com.innofang.gankiodemo.module.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.base.SingleFragmentActivity;

public class WebActivity extends SingleFragmentActivity {

    private static final String EXTRA_URL = "com.innofang.gankiodemo.ui.activity.url";
    private static final String EXTRA_DESC = "com.innofang.gankiodemo.ui.activity.desc";
    private Toolbar mToolbar;
    private WebFragment mWebFragment;
    private String mUrl;

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_DESC, desc);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        String desc = getIntent().getStringExtra(EXTRA_DESC);
        mWebFragment = (WebFragment) WebFragment.newInstance(mUrl, desc);
        return mWebFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void init() {
        super.init();
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
    }


    @Override
    public void onBackPressed() {
        if (mWebFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_browser:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = mWebFragment.getCurrentUrl();
                Uri uri;
                if (null != url) {
                    uri = Uri.parse(url);
                } else {
                    uri = Uri.parse(mUrl);
                }
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
