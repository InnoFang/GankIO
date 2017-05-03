package com.innofang.gankiodemo.module.gankdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innofang.gankiodemo.App;
import com.innofang.gankiodemo.R;
import com.innofang.gankiodemo.bean.GankDetail;
import com.innofang.gankiodemo.constant.URL;
import com.innofang.gankiodemo.http.Api;
import com.innofang.gankiodemo.utils.CloseUtils;
import com.innofang.gankiodemo.utils.StringFormatUtil;
import com.innofang.gankiodemo.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Inno Fang
 * Time: 2017/2/6 15:19
 * Description:
 */

public class GankDetailPresenter implements GankDetailContract.Presenter {
    private static final String TAG = "GankDetailPresenter";

    private GankDetailContract.View mView;

    public GankDetailPresenter(GankDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadingGank(final String date, final ImageView imageView) {
        Api.getGankService().getDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankDetail>() {
                    @Override
                    public void accept(GankDetail gankDetail) throws Exception {
                        List<GankDetail.ResultsBean.福利Bean> luck = gankDetail.getResults().get福利();
                        if (null != luck) {
                            String imgUrl = luck.get(0).getUrl() + URL.REQUEST_IMAGE_POSTFIX_FOR_SPANNER;
                            Log.i(TAG, "onNext: imgUrl = " + imgUrl);
                            mView.setImageUrl(imgUrl);
                            Glide.with(App.getContext())
                                    .load(imgUrl)
                                    .placeholder(R.drawable.default_nav_img)
                                    .animate(R.anim.anim_scale)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imageView);
                        }
                        showGankOfAndroid(gankDetail.getResults().getAndroid());
                        showGankOfIOS(gankDetail.getResults().getIOS());
                        showGankOfWeb(gankDetail.getResults().get前端());
                        showGankOfExpand(gankDetail.getResults().get拓展资源());
                        showGankOfRecommend(gankDetail.getResults().get瞎推荐());
                        showGankOfApp(gankDetail.getResults().getApp());
                        showGankOfVideo(gankDetail.getResults().get休息视频());
                        mView.setLoadingIndicator(false);
                    }
                });
    }

    @Override
    public void showGankOfAndroid(List<GankDetail.ResultsBean.AndroidBean> list) {
        mView.showGankOfAndroid(list);
    }

    @Override
    public void showGankOfIOS(List<GankDetail.ResultsBean.IOSBean> list) {
        mView.showGankOfIOS(list);
    }

    @Override
    public void showGankOfApp(List<GankDetail.ResultsBean.AppBean> list) {
        mView.showGankOfApp(list);
    }

    @Override
    public void showGankOfVideo(List<GankDetail.ResultsBean.休息视频Bean> list) {
        mView.showGankOfVideo(list);
    }

    @Override
    public void showGankOfWeb(List<GankDetail.ResultsBean.前端Bean> list) {
        mView.showGankOfWeb(list);
    }

    @Override
    public void showGankOfExpand(List<GankDetail.ResultsBean.拓展资源Bean> list) {
        mView.showGankOfExpand(list);
    }

    @Override
    public void showGankOfRecommend(List<GankDetail.ResultsBean.瞎推荐Bean> list) {
        mView.showGankOfRecommend(list);
    }

    @Override
    public void downloadImage(final Context context, final String url) {
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                Bitmap bitmap = null;
                bitmap = Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .into(500, 500)
                        .get();
                if (null == bitmap) {
                    e.onError(new Exception("下载失败"));
                }
                ToastUtil.showToast("下载进行中");
                e.onNext(bitmap);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        File dir = new File(Environment.getExternalStorageDirectory(), "Gank");
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        String fileName = StringFormatUtil.formatIamgeFileName(url);
                        File file = new File(dir, fileName);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                            if (null != bitmap) {
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            }
                            fos.flush();
                        } catch (IOException e) {
                            Log.e(TAG, "onNext: ", e);
                        } finally {
                            CloseUtils.closeQuietly(fos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        ToastUtil.showToast("下载完成");
                    }
                });
    }


    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }

}
