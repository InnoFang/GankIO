package com.innofang.gankiodemo;

import android.app.Application;
import android.content.Context;

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
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
