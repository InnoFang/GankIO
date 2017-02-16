package com.innofang.gankiodemo.module.imageshower;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.widget.DragImageView;

/**
 * Author: Inno Fang
 * Time: 2017/2/15 21:08
 * Description:
 */

public class ImageShowerFragment extends Fragment
        implements ImageShowerContract.View, View.OnClickListener {
    private static final String TAG = "ImageShowerFragment";
    private static final String ARGS_URL = "com.innofang.gankiodemo.module.showpicture.url";

    private DragImageView mMeizhiImageView;
    private ProgressBar mProgressBar;
    private AppCompatImageView mDownloadImage;
    private AppCompatImageView mShareIamge;

    private ImageShowerContract.Presenter mPresenter;

    private String mUrl;

    public static ImageShowerFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARGS_URL, url);
        ImageShowerFragment fragment = new ImageShowerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ImageShowerPresenter(this);
        mUrl = getArguments().getString(ARGS_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_shower, container, false);
        mMeizhiImageView = (DragImageView) view.findViewById(R.id.meizhi_image_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mDownloadImage = (AppCompatImageView) view.findViewById(R.id.download_image);
        mShareIamge = (AppCompatImageView) view.findViewById(R.id.share_image);

        mDownloadImage.setOnClickListener(this);
        mShareIamge.setOnClickListener(this);

        mPresenter.loadingIamge(getActivity(), mMeizhiImageView, mUrl);
        return view;
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(mMeizhiImageView, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active){
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(ImageShowerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.download_image:
                mPresenter.downloadImage();
                break;
            case R.id.share_image:
                mPresenter.shareImage();
                break;
        }
    }
}
