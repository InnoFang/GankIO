package com.innofang.gankiodemo.module.luck;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innofang.gankiodemo.R;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 10:51
 * Description:
 * 福利
 */

public class LuckFragment extends Fragment{
    private static final String TAG = "LuckFragment";

    public static Fragment newInstance(){
        return new LuckFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_luck, container, false);
    }
}
