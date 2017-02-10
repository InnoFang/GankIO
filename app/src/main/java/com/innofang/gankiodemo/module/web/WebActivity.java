package com.innofang.gankiodemo.module.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Collection;
import com.innofang.gankiodemo.module.SingleFragmentActivity;
import com.innofang.gankiodemo.utils.CollectionManager;

import java.util.List;

public class WebActivity extends SingleFragmentActivity {

    private static final String TAG = "WebActivity";
    private static final String EXTRA_URL = "com.innofang.gankiodemo.ui.activity.url";
    private static final String EXTRA_DESC = "com.innofang.gankiodemo.ui.activity.desc";
    private static final String EXTRA_WHO = "com.innofang.gankiodemo.ui.activity.who";
    private static final String EXTRA_TYPE = "com.innofang.gankiodemo.ui.activity.type";
    private static final String EXTRA_PUBLISH_AT = "com.innofang.gankiodemo.ui.activity.publish_at";
    private Toolbar mToolbar;
    private WebFragment mWebFragment;
    private String mUrl;
    private String mDesc;
    private String mWho;
    private String mType;
    private String mPublishAt;

    public static Intent newIntent(Context context, String url,
                                   String desc, String who, String type, String publishAt) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_DESC, desc);
        intent.putExtra(EXTRA_WHO, who);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_PUBLISH_AT, publishAt);
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
        mToolbar.setNavigationIcon(R.drawable.ic_nav_close);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        List<Collection> collections = CollectionManager.getInstance(this).getCollections();
        for (Collection collection : collections) {
            // 如果收藏里面存在含有当前url的item，那么视为当前item已添加至收藏
            if (mUrl.equals(collection.getUrl())){
                MenuItem item = menu.getItem(0);
                item.setIcon(R.drawable.ic_menu_like);
                item.setTitle(R.string.action_like);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        String url = mWebFragment.getCurrentUrl();
        Uri uri = Uri.parse(url);
        Log.i(TAG, "url = " + url);
        CollectionManager manager = CollectionManager.getInstance(this);
        switch (item.getItemId()) {
            case R.id.action_collection:
                if (item.getTitle().toString().equals(getString(R.string.action_dislike))){
                    item.setIcon(R.drawable.ic_menu_like);
                    item.setTitle(R.string.action_like);
                    Collection collection = new Collection(mDesc, mType, mWho, mPublishAt, mUrl);
                    manager.addCollection(collection);
                } else {
                    item.setIcon(R.drawable.ic_menu_dislike);
                    item.setTitle(R.string.action_dislike);
                    Collection collection = manager.getCollection(mUrl);
                    manager.deleteCollection(collection);
                }
                break;
            case R.id.action_share:
                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, uri);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到"));
                break;
            case R.id.action_open_browser:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
