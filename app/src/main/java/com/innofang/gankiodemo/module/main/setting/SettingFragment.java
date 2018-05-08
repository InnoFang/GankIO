package com.innofang.gankiodemo.module.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.base.BaseFragment;
import com.innofang.gankiodemo.module.main.setting.about.AboutFragment;
import com.innofang.gankiodemo.utils.ToastUtil;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 10:52
 * Description:
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "SettingFragment";
    private static final String DIALOG_ABOUT = "dialog_about";

    public static Fragment newInstance(){
        return new SettingFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void createView(View view, @Nullable Bundle savedInstanceState) {
        ImageView icon = (ImageView) find(R.id.app_icon);
        // 将图标圆角处理
        Glide.with(getActivity())
                .load(R.drawable.icon)
                .apply(new RequestOptions().transform(new CropCircleTransformation(getActivity())))
                .into(icon);

        Switch modeSwitcher = (Switch) find(R.id.switch_mode);
        modeSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setDayNightMode(isChecked);
            }
        });
        CardView aboutApp = (CardView) find(R.id.about_app_card_view);
        aboutApp.setOnClickListener(this);
    }
    
    private void setDayNightMode(boolean isChecked) {
        ToastUtil.showToast("这个坏了，还没修好~  ╮(╯▽╰)╭ ");
        /*if (isChecked){
            ToastUtil.showToast("夜间模式已打开");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            ToastUtil.showToast("夜间模式已关闭");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        getActivity().recreate();*/
    }

    @Override
    public void onClick(View v) {
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.show(getFragmentManager(), DIALOG_ABOUT);
    }


}
