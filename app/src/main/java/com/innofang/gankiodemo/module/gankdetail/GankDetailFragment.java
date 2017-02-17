package com.innofang.gankiodemo.module.gankdetail;

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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innofang.gankiodemo.service.DownloadService;
import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.GankDetail;
import com.innofang.gankiodemo.constant.GankItem;
import com.innofang.gankiodemo.module.imageshower.ImageShowerActivity;
import com.innofang.gankiodemo.module.web.WebActivity;
import com.innofang.gankiodemo.utils.ToastUtil;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 19:04
 * Description:
 */

public class GankDetailFragment extends Fragment implements GankDetailContract.View {

    private static final String ARG_GANK_URL = "gank_url";

    private String mGankUrl,
                   mImageUrl;
    private ImageView mMeizhiIamgeView;
    private TextView mAndroid,
            mIOS,
            mWeb,
            mExpandResource,
            mRecommend,
            mApp,
            mVideo;
    private RecyclerView mAndroidRecyclerView,
            mIOSRecyclerViwe,
            mWebRecyclerView,
            mExpandResourceRecyclerView,
            mRecommendRecyclerView,
            mAppRecyclerView,
            mVideoRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GankDetailContract.Presenter mPresenter;

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

    public static Fragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_GANK_URL, url);
        GankDetailFragment fragment = new GankDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankUrl = getArguments().getString(ARG_GANK_URL);
        mPresenter = new GankDetailPresenter(this);
        mImageUrl = null;
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank_detail, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mMeizhiIamgeView = (ImageView) view.findViewById(R.id.meizhi_image_view);
        mAndroid = (TextView) view.findViewById(R.id.android);
        mIOS = (TextView) view.findViewById(R.id.ios);
        mWeb = (TextView) view.findViewById(R.id.web);
        mExpandResource = (TextView) view.findViewById(R.id.expand_resource);
        mRecommend = (TextView) view.findViewById(R.id.recommend);
        mApp = (TextView) view.findViewById(R.id.app);
        mVideo = (TextView) view.findViewById(R.id.video);
        mAndroidRecyclerView = (RecyclerView) view.findViewById(R.id.android_recycler_view);
        mIOSRecyclerViwe = (RecyclerView) view.findViewById(R.id.ios_recycler_view);
        mWebRecyclerView = (RecyclerView) view.findViewById(R.id.web_recycler_view);
        mExpandResourceRecyclerView = (RecyclerView) view.findViewById(R.id.expand_resource_recycler_view);
        mRecommendRecyclerView = (RecyclerView) view.findViewById(R.id.recommend_recycler_view);
        mAppRecyclerView = (RecyclerView) view.findViewById(R.id.app_recycler_view);
        mVideoRecyclerView = (RecyclerView) view.findViewById(R.id.video_recycler_view);

        mAndroidRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mIOSRecyclerViwe.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWebRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExpandResourceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecommendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAppRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

       /* mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadingGank(mGankUrl, mMeizhiIamgeView);
            }
        });*/
        mPresenter.loadingGank(mGankUrl, mMeizhiIamgeView);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        String title = (String) mGankUrl.subSequence(mGankUrl.length() - 10, mGankUrl.length());
        toolbar.setTitle(title);

        mMeizhiIamgeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getImageUrl();
                ViewCompat.setTransitionName(mMeizhiIamgeView, "image");
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(), mMeizhiIamgeView, "image");
                startActivity(ImageShowerActivity.newIntent(getActivity(), url), options.toBundle());
            }
        });

        FloatingActionButton downloadFab = (FloatingActionButton) view.findViewById(R.id.download_image_fab);
        downloadFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.downloadImage(getActivity(), mImageUrl);
                Intent intent = new Intent(getActivity(), DownloadService.class);
                getActivity().startService(intent);
                getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
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
                if (null == mDownloadBinder) {
                    return;
                }
                mDownloadBinder.startDownload(mImageUrl);
            }
        });
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
        getActivity().unbindService(mConnection);
    }


    @Override
    public void setLoadingIndicator(final boolean active) {
/*        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(active);
            }
        });*/
    }

    @Override
    public void setImageUrl(String url) {
        mImageUrl = url;
    }

    @Override
    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    public void showGankOfAndroid(List<GankDetail.ResultsBean.AndroidBean> list) {
        if (null != list) {
            mAndroid.setVisibility(View.VISIBLE);
            GankDetailAdapter adapter = new GankDetailAdapter(getContext(), list, GankItem.ANDROID);
            mAndroidRecyclerView.setAdapter(adapter);
            adapter.setOnClickItemListener(new GankDetailAdapter.OnClickItemListener() {

                @Override
                public void onClick(String url, String desc, String who, String type, String publishAt) {
                    startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
                }
            });
        }
    }

    @Override
    public void showGankOfIOS(List<GankDetail.ResultsBean.IOSBean> list) {
        if (null != list) {
            mIOS.setVisibility(View.VISIBLE);
            GankDetailAdapter adapter = new GankDetailAdapter(getContext(), list, GankItem.IOS);
            mIOSRecyclerViwe.setAdapter(adapter);
            adapter.setOnClickItemListener(new GankDetailAdapter.OnClickItemListener() {

                @Override
                public void onClick(String url, String desc, String who, String type, String publishAt) {
                    startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
                }
            });
        }
    }

    @Override
    public void showGankOfApp(List<GankDetail.ResultsBean.AppBean> list) {
        if (null != list) {
            mApp.setVisibility(View.VISIBLE);
            GankDetailAdapter adapter = new GankDetailAdapter(getContext(), list, GankItem.APP);
            mAppRecyclerView.setAdapter(adapter);
            adapter.setOnClickItemListener(new GankDetailAdapter.OnClickItemListener() {
                @Override
                public void onClick(String url, String desc, String who, String type, String publishAt) {
                    startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
                }
            });
        }
    }

    @Override
    public void showGankOfVideo(List<GankDetail.ResultsBean.休息视频Bean> list) {
        if (null != list) {
            mVideo.setVisibility(View.VISIBLE);
            GankDetailAdapter adapter = new GankDetailAdapter(getContext(), list, GankItem.VIDEO);
            mVideoRecyclerView.setAdapter(adapter);
            adapter.setOnClickItemListener(new GankDetailAdapter.OnClickItemListener() {
                @Override
                public void onClick(String url, String desc, String who, String type, String publishAt) {
                    startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
                }
            });
        }
    }

    @Override
    public void showGankOfWeb(List<GankDetail.ResultsBean.前端Bean> list) {
        if (null != list) {
            mWeb.setVisibility(View.VISIBLE);
            GankDetailAdapter adapter = new GankDetailAdapter(getContext(), list, GankItem.WEB);
            mWebRecyclerView.setAdapter(adapter);
            adapter.setOnClickItemListener(new GankDetailAdapter.OnClickItemListener() {
                @Override
                public void onClick(String url, String desc, String who, String type, String publishAt) {
                    startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
                }
            });
        }
    }

    @Override
    public void showGankOfExpand(List<GankDetail.ResultsBean.拓展资源Bean> list) {
        if (null != list) {
            mExpandResource.setVisibility(View.VISIBLE);
            GankDetailAdapter adapter = new GankDetailAdapter(getContext(), list, GankItem.EXPAND_RESOURCE);
            mExpandResourceRecyclerView.setAdapter(adapter);
            adapter.setOnClickItemListener(new GankDetailAdapter.OnClickItemListener() {
                @Override
                public void onClick(String url, String desc, String who, String type, String publishAt) {
                    startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
                }
            });
        }
    }

    @Override
    public void showGankOfRecommend(List<GankDetail.ResultsBean.瞎推荐Bean> list) {
        if (null != list) {
            mRecommend.setVisibility(View.VISIBLE);
            GankDetailAdapter adapter = new GankDetailAdapter(getContext(), list, GankItem.RECOMMEND);
            mRecommendRecyclerView.setAdapter(adapter);
            adapter.setOnClickItemListener(new GankDetailAdapter.OnClickItemListener() {
                @Override
                public void onClick(String url, String desc, String who, String type, String publishAt) {
                    startActivity(WebActivity.newIntent(getActivity(), url, desc, who, type, publishAt));
                }
            });
        }
    }

    @Override
    public void setPresenter(GankDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
