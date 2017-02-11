package com.innofang.gankiodemo.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.innofang.gankiodemo.App;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 16:36
 * Description:
 */

public class ToastUtil {

    private static Toast mToast;
    private static long time = 0;

    /**
     * 显示Toast
     * 点一次显示内容并且为短时间显示，若在两秒内点击两次，则更改为长时间显示
     * 不会重复创建Toast
     * @param content
     */
    public static void showToast(String content) {
        long temp = System.currentTimeMillis();
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), content, Toast.LENGTH_SHORT);
        }
        mToast.setText(content);
        if (temp - time < 2000) {
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        time = temp;
        mToast.show();
    }


    public static void showToast(@StringRes int resId) {
        long temp = System.currentTimeMillis();
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), resId, Toast.LENGTH_SHORT);
        }
        mToast.setText(resId);
        if (temp - time < 2000) {
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        time = temp;
        mToast.show();
    }
}
