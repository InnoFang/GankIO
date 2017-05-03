package com.innofang.gankiodemo.http;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: Inno Fang
 * Time: 2017/5/3 13:27
 * Description:
 */


public class Api {

    private static GankService sGankService;
    private static OkHttpClient sOkHttpClient = new OkHttpClient();
    private static Converter.Factory sGsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory sRxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();

    public static GankService getGankService() {
        if (null == sGankService) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(sOkHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(sGsonConverterFactory)
                    .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                    .build();
            sGankService = retrofit.create(GankService.class);
        }
        return sGankService;
    }

}
