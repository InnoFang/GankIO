package com.innofang.gankiodemo.module.imageshower;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.innofang.gankiodemo.utils.CloseUtils;
import com.innofang.gankiodemo.utils.StringFormatUtil;
import com.innofang.gankiodemo.utils.ToastUtil;
import com.innofang.gankiodemo.widget.DragImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Inno Fang
 * Time: 2017/2/15 21:39
 * Description:
 */

public class ImageShowerPresenter implements ImageShowerContract.Presenter {
    private static final String TAG = "ImageShowerPresenter";

    private ImageShowerContract.View mView;

    public ImageShowerPresenter(ImageShowerContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void loadingIamge(Context context, DragImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mView.setLoadingIndicator(false);
                        return false;
                    }
                })
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);

    }

    @Override
    @SuppressWarnings("deprecation")
    public void shareImage(final Context context, final String url) {
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                Bitmap bitmap = null;
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .into(500, 500)
                        .get();
                if (null == bitmap) {
                    e.onError(new Exception("分享失败"));
                }
                e.onNext(bitmap);
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
                        if (!file.exists()){
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(file);
                                if (null != bitmap){
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                }
                                fos.flush();
                            } catch (IOException e) {
                                Log.e(TAG, "onNext: ", e);
                            }finally {
                                CloseUtils.closeQuietly(fos);
                            }
                        }
                        Uri uri = Uri.fromFile(file);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.setType("image/jpeg");
                        context.startActivity(Intent.createChooser(intent, "分享到"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @Override
    @SuppressWarnings("deprecation")
    public void downloadImage(final Context context, final String url) {
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                Bitmap bitmap = null;
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(url)
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
                        if (!file.exists()){
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(file);
                                if (null != bitmap){
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                }
                                fos.flush();
                            } catch (IOException e) {
                                Log.e(TAG, "onNext: ", e);
                            }finally {
                                CloseUtils.closeQuietly(fos);
                            }
                        }
                        mView.showMessage("下载完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.toString());
                    }

                    @Override
                    public void onComplete() {
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
