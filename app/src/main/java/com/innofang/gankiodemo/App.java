package com.innofang.gankiodemo;

import android.app.Application;
import android.content.Context;

import com.innofang.gankiodemo.utils.CrashHandler;

/**
 * Author: Inno Fang
 * Time: 2017/2/1 12:01
 * Description:
 */

public class App extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        CrashHandler.getInstance().init(getApplicationContext());
    }

    public static Context getContext() {
        return sContext;
    }
}
