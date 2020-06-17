package com.mljr.moon.imgcompress.callback;

import com.mljr.moon.imgcompress.model.MNImageModel;

/**
 * Author： fanyafeng
 * Date： 17/11/29 上午9:52
 * Email: fanyafeng@live.cn
 */
public interface OnThreadResultCallback {

    /**
     * 线程开始
     */
    void onStart();

    /**
     * 线程完成
     */
    void onFinish(MNImageModel mlImageBean);

    /**
     * 线程被中断
     */
    void onInterrupted();
}
