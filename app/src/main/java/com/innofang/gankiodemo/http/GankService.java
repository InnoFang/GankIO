package com.innofang.gankiodemo.http;

import com.innofang.gankiodemo.bean.Gank;
import com.innofang.gankiodemo.bean.GankDate;
import com.innofang.gankiodemo.bean.GankDetail;
import com.innofang.gankiodemo.bean.GankSearch;
import com.innofang.gankiodemo.bean.Luck;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Author: Inno Fang
 * Time: 2017/5/3 12:49
 * Description: Retrofit interface
 */


public interface GankService {

    // 获取每日数据或历史
    @GET("day/{date}")
    Observable<GankDetail> getDate(@Path("date") String date);

    // 获取每日数据或历史
    @GET("day/history")
    Observable<GankDate> getHistory();

    // 分类数据，请求福利图片个数：10 页数：1
    @GET("data/福利/{count}/{page}")
    Observable<Luck> getLuck(@Path("count") int count,
                             @Path("page") int page);

    @GET("random/data/%E7%A6%8F%E5%88%A9/{count}")
    Observable<Luck> getRadomLuck(@Path("count") int count);

    // 分类数据，获取指定分类数据，个数：指定，页数：1
    @GET("data/{category}/{count}/{page}")
    Observable<Gank> getCategoryData(@Path("category") String category,
                                     @Path("count") int count,
                                     @Path("page") int page);

    // 分类数据，获取指定分类数据，个数：指定，页数：1
    @GET("search/query/{query}/category/all/count/{count}/page/{page}")
    Observable<GankSearch> getGankSearch(@Path("query") String query,
                                         @Path("count") int count,
                                         @Path("page") int page);

}
