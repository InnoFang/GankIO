package com.innofang.gankiodemo.module.dailygank.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.gankdetail.GankDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/8 11:57
 * Description:
 * 直接选择干货发布日期并跳转至干活详情页
 */

public class DatePickerFragment extends DialogFragment implements GankDatePickerContract.View {
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
/*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_date_picker, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.gank_date_picker_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_date_picker, null, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.gank_date_picker_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        dialog.setCancelable(true);
        dialog.setTitle(R.string.date_picker_title);
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        return dialog.show();
    }

    @Override
    public void showGankDate(List<String> dates) {
        Log.i(TAG, "showGankDate: " + dates.toString());
        mAdapter.setDateList(dates);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnClickItemListener(new GankDatePickerAdapter.OnClickItemListener() {
            @Override
            public void onClick(String url) {
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
