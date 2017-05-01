package com.innofang.gankiodemo.module.main.dailygank.datepicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.base.BaseFragment;
import com.innofang.gankiodemo.module.gankdetail.GankDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/8 11:57
 * Description:
 * 直接选择干货发布日期并跳转至干活详情页
 */

public class DatePickerFragment extends BaseFragment implements GankDatePickerContract.View {
    private static final String TAG = "DatePickerFragment";

    private GankDatePickerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private GankDatePickerContract.Presenter mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new GankDatePickerAdapter(getActivity(), new ArrayList<String>(0));
        mPresenter = new GankDatePickerPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_date_picker;
    }

    @Override
    protected void createView(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.gank_date_picker_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showGankDate(List<String> dates) {
        Log.i(TAG, "showGankDate: " + dates.toString());
        mAdapter.setDateList(dates);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnClickItemListener(new GankDatePickerAdapter.OnClickItemListener() {
            @Override
            public void onClick(String url) {
                getActivity().onBackPressed();
                startActivity(GankDetailActivity.newIntent(getActivity(), url));
            }
        });
    }

    @Override
    public void setPresenter(GankDatePickerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != mPresenter){
            mPresenter.start();
        }
    }
}
