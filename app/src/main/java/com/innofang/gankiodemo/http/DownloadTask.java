package com.innofang.gankiodemo.http;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.innofang.gankiodemo.utils.CloseUtils;
import com.innofang.gankiodemo.utils.StringFormatUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Inno Fang
 * Time: 2017/2/16 19:11
 * Description:
 */

public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    private static final String TAG = "DownloadTask";
    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_FAILED = 1;
    private static final int TYPE_PAUSED = 2;
    private static final int TYPE_CANCELED = 3;

    private DownloadListener mListener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int mLastProgress;

    public DownloadTask(DownloadListener listener) {
        mListener = listener;
    }


    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
            long downloadedLength = 0; // 记录文件下载长度
            String downloadUrl = params[0];
            String fileName = StringFormatUtil.formatIamgeFileName(downloadUrl);
            String directory = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            if (file.exists()) {
                downloadedLength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadedLength) {
                // 已下载字节和文件总字节相等，说明已经下载完成了
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    // 断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE", "byte=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (null != response) {
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadedLength); // 跳过已下载字节
                byte[] bytes = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(bytes)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        savedFile.write(bytes, 0, len);
                        // 计算已下载的百分比
                        int progress = (int) ((total + downloadedLength) / contentLength * 100);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
        } finally {
            CloseUtils.closeQuietly(is);
            CloseUtils.closeQuietly(savedFile);
            if (isCanceled && null != file){
                file.delete();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > mLastProgress){
            mListener.onProgress(progress);
            mLastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case TYPE_SUCCESS:
                mListener.onSuccess();
                break;
            case TYPE_FAILED:
                mListener.onFailed();
                break;
            case TYPE_PAUSED:
                mListener.onPaused();
                break;
            case TYPE_CANCELED:
                mListener.onPaused();
                break;
            default:
                break;
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }

    public interface DownloadListener {
        void onProgress(int progress);

        void onSuccess();

        void onFailed();

        void onPaused();

        void onCanceled();
    }
}
