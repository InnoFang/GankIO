package com.innofang.gankiodemo.module.imageshower;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Author: Inno Fang
 * Time: 2017/2/15 21:08
 * Description:
 */

public class ImageShowerFragment extends Fragment {
    private static final String TAG = "ImageShowerFragment";
    private static final String ARGS_URL = "com.innofang.gankiodemo.module.showpicture.url";

    public static ImageShowerFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(ARGS_URL, url);
        ImageShowerFragment fragment = new ImageShowerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
