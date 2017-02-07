package com.innofang.gankiodemo.module.dailygank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.module.gankdetail.GankDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 00:15
 * Description:
 * 展示每日干货，默认主界面
 */

public class DailyGankFragment extends Fragment implements DailyGankContract.View{

    private static final String TAG = "DailyGankFragment";

    private DailyGankContract.Presenter mPresenter;
    private DailyGankAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mDailyGankRecyclerView;
    private FloatingActionButton mShowCalendarButton;

    public static Fragment newInstance(){
        return new DailyGankFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DailyGankAdapter(getActivity(), new ArrayList<Luck.ResultsBean>(0));
        mPresenter = new DailyGankPresenter(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_gank, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mShowCalendarButton = (FloatingActionButton) view.findViewById(R.id.show_calendar_fab);
        mDailyGankRecyclerView = (RecyclerView) view.findViewById(R.id.daily_gank_recycler_view);

        mDailyGankRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mDailyGankRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadingDailyGank();
            }
        });

        mAdapter.setOnShowDailyGankClickListener(new DailyGankAdapter.OnShowDailyGankClickListener() {
            @Override
            public void onClick(String url) {
                startActivity(GankDetailActivity.newIntent(getActivity(), url));
            }
        });
        setLoadingIndicator(true);
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
    public void showDailyGank(List<Luck.ResultsBean> list) {
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyOrError(String error) {
        if (null != error){
            Log.i(TAG, error);
            Snackbar.make(mShowCalendarButton, error, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void setPresenter(DailyGankContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destroy();
        }
        super.onDestroy();
    }
}
