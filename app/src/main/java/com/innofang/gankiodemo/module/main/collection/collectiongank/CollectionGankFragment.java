package com.innofang.gankiodemo.module.main.collection.collectiongank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.Collection;
import com.innofang.gankiodemo.module.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 22:44
 * Description:
 */

public class CollectionGankFragment extends Fragment implements CollectionGankContract.View {
    private static final String TAG = "CollectionGankFragment";
    private static final String ARGS_TYPE = "com.innofang.gankiodemo.module.collection.collectiongank.type";

    private LinearLayout mEmptyView;
    private RecyclerView mRecyclerView;

    private CollectionGankContract.Presenter mPresenter;
    private CollectionGankAdapter mAdapter;
    private String mType;

    public static CollectionGankFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(ARGS_TYPE, type);
        CollectionGankFragment fragment = new CollectionGankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(ARGS_TYPE);
        mPresenter = new CollectionGankPresenter(this);
        mAdapter = new CollectionGankAdapter(getContext(), new ArrayList<Collection>(0));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection_gank, container, false);
        mEmptyView = (LinearLayout) view.findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.collection_gank_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.queryCollections(mType);
        return view;
    }

    // 刷新列表视图
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryCollections(mType);
    }

    @Override
    public void setEmptyViewState(boolean state) {
        if (state) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public void showCollectionGank(List<Collection> collections) {
        mAdapter.setList(collections);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnClickGankItemListener(new CollectionGankAdapter.OnClickGankItemListener() {
            @Override
            public void onClick(String url, String desc, String who, String type, String publishAt) {
                startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
            }
        });
    }

    @Override
    public void setPresenter(CollectionGankContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
