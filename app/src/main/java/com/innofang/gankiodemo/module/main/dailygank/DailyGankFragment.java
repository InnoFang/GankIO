package com.innofang.gankiodemo.module.main.dailygank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Luck;
import com.innofang.gankiodemo.module.base.BaseFragment;
import com.innofang.gankiodemo.module.gankdetail.GankDetailActivity;
import com.innofang.gankiodemo.module.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 00:15
 * Description:
 * 展示每日干货，默认主界面
 */

public class DailyGankFragment extends BaseFragment implements DailyGankContract.View {

    private static final String TAG = "DailyGankFragment";

    private DailyGankContract.Presenter mPresenter;
    private DailyGankAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mDailyGankRecyclerView;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    public static Fragment newInstance() {
        return new DailyGankFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DailyGankAdapter(getActivity(), new ArrayList<Luck.ResultsBean>(0));
        mPresenter = new DailyGankPresenter(this);
        setHasOptionsMenu(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily_gank;
    }

    @Override
    protected void createView(View view, @Nullable Bundle savedInstanceState) {
        initView();
        mPresenter.loadingDailyGank();
    }
    
    private boolean mIsLoadingMore = false;

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) find(R.id.swipe_refresh_layout);
        mDailyGankRecyclerView = (RecyclerView) find(R.id.daily_gank_recycler_view);
        final GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity(), 2);
        mDailyGankRecyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                int type = mDailyGankRecyclerView.getAdapter().getItemViewType(position);
                if (type == DailyGankAdapter.TYPE_FOOTER){
                    return gridLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
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

        mAdapter.setOnShowDailyGankClickListener(
                new DailyGankAdapter.OnShowDailyGankClickListener() {
            @Override
            public void onClick(String url, ActivityOptionsCompat options) {
                startActivity(GankDetailActivity.newIntent(getActivity(), url)
                        , options.toBundle());
            }
        });

        mToolbar = (Toolbar) find(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) find(R.id.drawer_layout);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_calendar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        final TextView searchTextView = (TextView) find(R.id.search_text_view);
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.setTransitionName(searchTextView, "search");
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(), searchTextView, "search");
                startActivity(SearchActivity.newIntent(getActivity()), options.toBundle());
            }
        });
        setLoadingIndicator(true);
    }

    public boolean onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    // 用于判断是否需要上拉加载更多
    @Override
    public void setPullUpLoadingState(boolean state) {
        mIsLoadingMore = state;
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

    @SuppressWarnings("deprecation")
    @Override
    public void showDailyGank(List<Luck.ResultsBean> list) {
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
        // 监听滑动，滑倒底部更新数据
        mDailyGankRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!mIsLoadingMore && visibleItemCount > 0
                        && lastVisibleItem >= totalItemCount - 1) {
                    mPresenter.loadingDailyGank();
                    mIsLoadingMore = true;
                }
            }
        });
    }

    @Override
    public void showEmptyOrError(String error) {
        if (null != error) {
            Snackbar.make(mToolbar, error, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void setPresenter(DailyGankContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /*******Fragment Life Cycle Test********/
    @Override
    public void onResume() {
        super.onResume();
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
