package com.example.mikt.myapplication.db_download;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DbDownloader<T, V> extends HandlerThread {
    private static final int KEEP_ALIVE_TIME = 1;

    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private static final String TAG = "ThumbnailDownloader";

    private static final int MESSAGE_DOWNLOAD = 0;

    private Handler mRequestHandler;

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private final ConcurrentHashMap<T,Callable> mDecodeWorkQueue;

    // A managed pool of background download threads
    private final ExecutorService mDownloadThreadPool;


    private Handler mResponseHandler;

    private ThumbnailDownloadListener<T, V> mThumbnailDownloadListener;

    public interface ThumbnailDownloadListener<T, V> {
        void onThumbnailDownloaded(T target, List<V> thumbnail);
    }
    public void setThumbnailDownloadListener
            (ThumbnailDownloadListener<T, V> listener) {
        mThumbnailDownloadListener = listener;
    }


    private DbDownloader(String name, int priority, Handler responseHandler) {
        super(name, priority);
        mDecodeWorkQueue = new ConcurrentHashMap<>();
        mResponseHandler = responseHandler;
        mDownloadThreadPool = Executors.newFixedThreadPool(NUMBER_OF_CORES);

    }

    public void queueTask(T target, Callable callable) {
        Log.i(TAG, "Got a Callable: ");

        if (callable == null) {
            mDecodeWorkQueue.remove(target);
        } else {
            mDecodeWorkQueue.put(target, callable);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                    .sendToTarget();
        }
    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    Log.i(TAG, "Got a request for URL: " +
                            mDecodeWorkQueue.get(target));
                    handleRequest(target);
                }
            }
        };
    }

    private void handleRequest(final T target) {
        Callable callable = mDecodeWorkQueue.get(target);
        mDownloadThreadPool.submit(callable);
    }

}
