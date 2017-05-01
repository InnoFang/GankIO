package com.innofang.gankiodemo.module.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.GankSearch;
import com.innofang.gankiodemo.module.base.BaseFragment;
import com.innofang.gankiodemo.module.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 16:22
 * Description:
 */

public class SearchFragment extends BaseFragment implements SearchContract.View {
    private static final String TAG = "SearchFragment";

    private SearchAdapter mAdapter;
    private SearchContract.Presenter mPresenter;

    private EditText mSearchEditeText;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchPresenter(this);
        mAdapter = new SearchAdapter(getActivity(), new ArrayList<GankSearch.ResultsBean>(0));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_gank;
    }

    @Override
    protected void createView(View view, @Nullable Bundle savedInstanceState) {
        AppCompatImageView goBack = (AppCompatImageView) find(R.id.go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mSearchEditeText = (EditText) find(R.id.search_gank_edit_text);
        mProgressBar = (ProgressBar) find(R.id.progress_bar);
        mRecyclerView = (RecyclerView) find(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mSearchEditeText.addTextChangedListener(new TextWatcher() {
            long startTime = System.currentTimeMillis();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            long endTime;
            int times = 0;
            @Override
            public void afterTextChanged(Editable s) {
                endTime = System.currentTimeMillis();
                Log.i(TAG, "onTextChanged: endTime " + endTime);
                if (s.toString().trim().length() > 0 && endTime - startTime > 1000) {
                    Log.i(TAG, "afterTextChanged: is called");
                    startTime = System.currentTimeMillis();
                    mPresenter.requestSearchResult(s.toString());
                    Log.i("TAG", "afterTextChanged: times " + times++);
                }
            }
        });
    }

    @Override
    public void showSearchResult(List<GankSearch.ResultsBean> list) {
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnClickItemListener(new SearchAdapter.OnClickItemListener() {

            @Override
            public void onClick(String url, String desc, String who, String type, String publishAt) {
                startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
            }
        });
    }

    @Override
    public void setLoadingState(boolean active) {
        if (active) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmptyOrError(String msg) {
        if (null != msg) {
            Snackbar.make(mSearchEditeText, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
