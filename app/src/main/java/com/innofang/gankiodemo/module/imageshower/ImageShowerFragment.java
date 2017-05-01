package com.innofang.gankiodemo.module.imageshower;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ProgressBar;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.base.BaseFragment;
import com.innofang.gankiodemo.service.DownloadService;
import com.innofang.gankiodemo.utils.ToastUtil;
import com.innofang.gankiodemo.widget.DragImageView;

/**
 * Author: Inno Fang
 * Time: 2017/2/15 21:08
 * Description:
 */

public class ImageShowerFragment extends BaseFragment
        implements ImageShowerContract.View, View.OnClickListener {
    private static final String TAG = "ImageShowerFragment";
    private static final String ARGS_URL = "com.innofang.gankiodemo.module.showpicture.url";

    private DragImageView mMeizhiImageView;
    private ProgressBar mProgressBar;
    private AppCompatImageView mDownloadImage;
    private AppCompatImageView mShareIamge;

    private ImageShowerContract.Presenter mPresenter;

    private String mUrl;

    private boolean isRunning = false;

    private DownloadService.DownloadBinder mDownloadBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_image_shower;
    }

    @Override
    protected void createView(View view,@Nullable Bundle savedInstanceState) {
        mMeizhiImageView = (DragImageView) find(R.id.meizhi_image_view);
        mProgressBar = (ProgressBar) find(R.id.progress_bar);
        mDownloadImage = (AppCompatImageView) find(R.id.download_image);
        mShareIamge = (AppCompatImageView) find(R.id.share_image);

        mDownloadImage.setOnClickListener(this);
        mShareIamge.setOnClickListener(this);

        mPresenter.loadingIamge(getActivity(), mMeizhiImageView, mUrl);
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(mMeizhiImageView, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
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
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
        switch (v.getId()) {
            case R.id.download_image:

//                mPresenter.downloadImage(getActivity(), mUrl);
//                ToastUtil.showToast("正在下载图片");
                // 采用后台下载
                Intent intent = new Intent(getActivity(), DownloadService.class);
                getActivity().startService(intent);
                getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                isRunning = true;
                if (null == mDownloadBinder) {
                    return;
                }
                mDownloadBinder.startDownload(mUrl);
                break;
            case R.id.share_image:
                mPresenter.shareImage(getActivity(), mUrl);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.showToast("拒绝权限将无法进行该操作");
                }
                break;
            case 2:
                if (grantResults.length > 0 &&
                        grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.showToast("拒绝权限将无法进行该操作");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRunning){
            getActivity().unbindService(mConnection);
        }
    }
}
