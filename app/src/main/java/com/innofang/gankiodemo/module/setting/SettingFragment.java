package com.innofang.gankiodemo.module.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.module.setting.about.AboutFragment;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 10:52
 * Description:
 */

public class SettingFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "SettingFragment";
    private static final String DIALOG_ABOUT = "dialog_about";

    public static Fragment newInstance(){
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        CardView cardView = (CardView) view.findViewById(R.id.about_app_card_view);
        cardView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.show(getFragmentManager(), DIALOG_ABOUT);
    }
}
