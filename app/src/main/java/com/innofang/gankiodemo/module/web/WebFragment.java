package com.innofang.gankiodemo.module.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.base.BaseFragment;

/**
 * Author: Inno Fang
 * Time: 2017/2/6 19:26
 * Description:
 */

public class WebFragment extends BaseFragment {
    private static final String TAG = "WebFragment";
    private static final String ARGS_URL = "com.innofang.gankiodemo.ui.fragment.url";
    private static final String ARGS_DESC = "com.innofang.gankiodemo.ui.fragment.desc";

    private String mUrl;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String mTitle;

    public static Fragment newInstance(String uri, String desc) {
        Bundle args = new Bundle();
        args.putString(ARGS_URL, uri);
        args.putString(ARGS_DESC, desc);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(ARGS_URL);
        mTitle = getArguments().getString(ARGS_DESC);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void createView(View view, @Nullable Bundle savedInstanceState) {
        mProgressBar = (ProgressBar) find(R.id.progress_bar);
        mProgressBar.setMax(100);
        mWebView = (WebView) find(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            public void onReceivedTitle(WebView webView, String title) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (null != activity) {
                    ActionBar actionBar = activity.getSupportActionBar();
                    if (null != actionBar) {
                        actionBar.setSubtitle(title);
                        actionBar.setTitle(mTitle);
                    }
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                return false;
            }
        });
        Log.i(TAG, "url = " + mUrl);
        mWebView.loadUrl(mUrl);
    }

    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }

    public String getCurrentUrl() {
        return mWebView.getUrl();
    }

}
