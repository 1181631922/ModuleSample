package com.mljr.moon.imgcompress.task;

import com.mljr.moon.imgcompress.engine.EngineFactory;
import com.mljr.moon.imgcompress.model.MNImageModel;
import com.mljr.moon.imgcompress.callback.OnThreadResultCallback;
import com.mljr.moon.imgcompress.compressrule.ICompressRule;
import com.mljr.moon.imgcompress.util.LogUtil;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * Author： fanyafeng
 * Date： 17/11/29 上午9:58
 * Email: fanyafeng@live.cn
 */
public class CompressPicture implements Runnable {
    private final static String TAG = CompressPicture.class.getSimpleName();

    /**
     * 计时器，辅助类
     * <p>
     * 在完成一组正在其他线程中执行的操作之前
     * 允许一个或多个线程一直等待
     */
    private CountDownLatch countDownLatch;

    //文件名称
    private MNImageModel mlImageBean;

    private OnThreadResultCallback onThreadResultCallback;

    private ICompressRule compressRule;

    private EngineFactory compressEngineFactory;


    private CompressPicture() {
    }

    public CompressPicture(CountDownLatch countDownLatch, MNImageModel mlImageBean, OnThreadResultCallback onThreadResultCallback, EngineFactory compressEngineFactory, ICompressRule compressRule) {
        this.countDownLatch = countDownLatch;
        this.mlImageBean = mlImageBean;
        this.onThreadResultCallback = onThreadResultCallback;
        this.compressEngineFactory = compressEngineFactory;
        this.compressRule = compressRule;
    }

    @Override
    public void run() {
        onThreadResultCallback.onStart();
        try {
            if (mlImageBean != null) {
                if (mlImageBean.getSourcePath() != null) {
                    File sourceFile = new File(mlImageBean.getSourcePath());

//                    File file = MLCompressEngine.Compress(sourceFile, compressRule);

                    File file = compressEngineFactory.compress(sourceFile, compressRule);

                    if (file != null) {
                        if (file.length() >= sourceFile.length()) {
                            mlImageBean.setTargetPath(mlImageBean.getSourcePath());
                        } else {
                            mlImageBean.setTargetPath(file.getAbsolutePath());
                        }
                        onThreadResultCallback.onFinish(mlImageBean);
                    } else {
                        onThreadResultCallback.onInterrupted();
                    }

                } else {
                    LogUtil.e(TAG, "photolevel is null");
                    onThreadResultCallback.onInterrupted();
                }
            } else {
                LogUtil.e(TAG, "fileName is null,please check the image file");
                onThreadResultCallback.onInterrupted();
            }
        } catch (Exception e) {
            onThreadResultCallback.onInterrupted();
        } finally {
            countDownLatch.countDown();
        }
    }
}
