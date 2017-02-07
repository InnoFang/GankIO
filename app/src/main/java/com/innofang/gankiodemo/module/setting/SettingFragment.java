package com.innofang.gankiodemo.module.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innofang.gankiodemo.R;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 10:52
 * Description:
 */

public class SettingFragment extends Fragment {
    private static final String TAG = "SettingFragment";

    public static Fragment newInstance(){
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}
