package com.innofang.gankiodemo.module.category.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Gank;
import com.innofang.gankiodemo.module.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/7 16:31
 * Description: ViewPager 展示每项Gank列表
 */

public class GankFragment extends Fragment implements GankContract.View{

    private static final String TAG = "GankFragment";
    private static final String ARGS_TITLE = "com.innofang.gankiodemo.module.category.title";

    private String mCategory;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mGankRecyclerView;
    private GankAdapter mGankAdapter;
    private GankContract.Presenter mPresenter;
    private boolean mIsLoadingMore = false;

    public static GankFragment newInstance(String title){
        GankFragment gankFragment = new GankFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_TITLE, title);
        gankFragment.setArguments(args);
        return gankFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GankPresenter(this);
        mGankAdapter = new GankAdapter(getContext(), new ArrayList<Gank.ResultsBean>(0));
        mCategory = getArguments().getString(ARGS_TITLE);
    }


    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mGankRecyclerView = (RecyclerView) view.findViewById(R.id.gank_recycler_view);
        mGankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mGankRecyclerView.setAdapter(mGankAdapter);
        // 监听滑动，滑倒底部更新数据
        mGankRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i(TAG, "onScrolled: is called");
                LinearLayoutManager layoutManager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!mIsLoadingMore && visibleItemCount > 0
                        && lastVisibleItem >= totalItemCount - 1) {
                    Log.i(TAG, "onScrollStateChanged: bottom");
                    mPresenter.requestGank(getCategory());
                    mIsLoadingMore = true;
                }
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestGank(mCategory);
            }
        });
        setLoadingIndicator(true);
        mPresenter.requestGank(getCategory());
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mGankAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showGank(List<Gank.ResultsBean> list) {
        Log.i(TAG, "showGank: " + list.toString());
        mGankAdapter.setList(list);
        mGankAdapter.notifyDataSetChanged();
        mGankAdapter.setOnClickGankItemListener(new GankAdapter.OnClickGankItemListener() {
            @Override
            public void onClick(String url, String desc, String who, String type, String publishAt) {
                startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
            }
        });
    }

    @Override
    public void showEmptyOrError(String msg) {
        if (null != msg){
            Log.i(TAG, msg);
            Snackbar.make(mSwipeRefreshLayout, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void setPullUpLoadingState(boolean state) {
        mIsLoadingMore = state;
    }

    @Override
    public String getCategory() {
        return mCategory;
    }


}
