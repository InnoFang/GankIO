package com.innofang.gankiodemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.innofang.gankiodemo.http.DownloadTask;
import com.innofang.gankiodemo.utils.StringFormatUtil;
import com.innofang.gankiodemo.utils.ToastUtil;

import java.io.File;

/**
 * Author: Inno Fang
 * Time: 2017/2/16 19:45
 * Description:
 */

public class DownloadService extends Service {
    private static final String TAG = "DownloadService";

    private DownloadTask mDownloadTask;
    private String mDownloadUrl;

    private DownloadTask.DownloadListener mDownloadListener = new DownloadTask.DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("正在下载", progress));
        }

        @Override
        public void onSuccess() {
            mDownloadTask = null;
            // 下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载成功", -1));
            ToastUtil.showToast("下载成功");
        }

        @Override
        public void onFailed() {
            mDownloadTask = null;
            // 下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载失败", -1));
            ToastUtil.showToast("下载失败");
        }

        @Override
        public void onPaused() {
            mDownloadTask = null;
            ToastUtil.showToast("下载已暂停");
        }

        @Override
        public void onCanceled() {
            mDownloadTask = null;
            stopForeground(true);
            ToastUtil.showToast("下载已取消");
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (null == mDownloadTask) {
                mDownloadUrl = url;
                mDownloadTask = new DownloadTask(mDownloadListener);
                mDownloadTask.execute(mDownloadUrl);
                startForeground(1, getNotification("正在下载", 0));
                ToastUtil.showToast("正在下载");
            }
        }

        public void pauseDownload() {
            if (null != mDownloadTask) {
                mDownloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (null != mDownloadTask){
                mDownloadTask.cancelDownload();
            } else {
                if (null != mDownloadUrl){
                    // 取消下载时需将文件删除，并将通知关闭
                    String fileName = StringFormatUtil.formatIamgeFileName(mDownloadUrl);
                    String directory = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    ToastUtil.showToast("文件已删除");
                }
            }
        }
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(title);
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}
