package com.innofang.gankiodemo.module.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.category.gank.GankFragment;
import com.innofang.gankiodemo.module.imageshower.ImageShowerActivity;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 10:50
 * Description: 分类浏览
 */

public class CategoryFragment extends Fragment implements CategoryContract.View{
    private static final String TAG = "CategoryFragment";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mMeizhiImageView;
    private CategoryContract.Presenter mPresenter;

    private String mImgUrl;
    public static Fragment newInstance(){
        return new CategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CategoryPresenter(this);
        mImgUrl = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_category, container, false);
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        mMeizhiImageView = (ImageView) v.findViewById(R.id.meizhi_image_view);

        mMeizhiImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getImageUrl();
                ViewCompat.setTransitionName(mMeizhiImageView, "meizhi");
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(), mMeizhiImageView, "meizhi");
                startActivity(ImageShowerActivity.newIntent(getActivity(), url), options.toBundle());
            }
        });

        showMeizhi();

        String[] titles = {"Android", "iOS", "前端", "拓展资源", "瞎推荐", "App", "休息视频"};
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), titles);

        GankFragment androidFragment = GankFragment.newInstance("Android");
        GankFragment iOSFragment = GankFragment.newInstance("iOS");
        GankFragment webFragment = GankFragment.newInstance("前端");
        GankFragment expandResourceFragment = GankFragment.newInstance("拓展资源");
        GankFragment recommendFragment = GankFragment.newInstance("瞎推荐");
        GankFragment appFragment = GankFragment.newInstance("App");
        GankFragment videoFragment = GankFragment.newInstance("休息视频");

        adapter.addFragment(androidFragment);
        adapter.addFragment(iOSFragment);
        adapter.addFragment(webFragment);
        adapter.addFragment(expandResourceFragment);
        adapter.addFragment(recommendFragment);
        adapter.addFragment(appFragment);
        adapter.addFragment(videoFragment);


        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager.setCurrentItem(0);
        return v;
    }

    @Override
    public void showMeizhi() {
        mPresenter.requestRandomMeizhi(mMeizhiImageView);
    }

    @Override
    public void showErrorOrEmptyInfo(String info) {
        Snackbar.make(mMeizhiImageView, info, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void setImageUrl(String url) {
        mImgUrl = url;
    }

    @Override
    public String getImageUrl() {
        return mImgUrl;
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != mPresenter) {
            mPresenter.start();
        }
    }
}
