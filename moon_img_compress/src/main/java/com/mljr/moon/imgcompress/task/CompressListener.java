package com.mljr.moon.imgcompress.task;

import com.mljr.moon.imgcompress.callback.OnAllThreadResultCallback;

import java.util.concurrent.CountDownLatch;

/**
 * Author： fanyafeng
 * Date： 17/11/29 上午10:31
 * Email: fanyafeng@live.cn
 */
public class CompressListener implements Runnable {
    /**
     * 计数器
     */
    private CountDownLatch countDownLatch;
    /**
     * 压缩回调
     */
    private OnAllThreadResultCallback onAllThreadResultCallback;

    private CompressListener() {
    }

    public CompressListener(CountDownLatch countDownLatch, OnAllThreadResultCallback onAllThreadResultCallback) {
        this.countDownLatch = countDownLatch;
        this.onAllThreadResultCallback = onAllThreadResultCallback;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            onAllThreadResultCallback.onSuccess();
        } catch (InterruptedException e) {
            onAllThreadResultCallback.onFailed();
        }
    }
}
