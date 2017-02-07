package com.innofang.gankiodemo.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 17:33
 * Description:
 */

public class RemoteManager {
    private static final String TAG = "RemoteManager";

    private static RemoteManager sManager;

    private OkHttpClient mClient;

    private RemoteManager() {
        mClient = new OkHttpClient.Builder().build();
    }

    public static RemoteManager getInstance() {
        if (null == sManager){
            sManager = new RemoteManager();
        }
        return sManager;
    }

    public void asyncRequest(String url, final LoadingCallback callback){
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onUnavailable();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.i(TAG, "onResponse: " + json);
                    callback.onLoad(json);
                }
            }
        });
    }
}
