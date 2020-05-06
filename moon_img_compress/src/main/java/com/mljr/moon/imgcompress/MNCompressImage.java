package com.mljr.moon.imgcompress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.MainThread;

import com.mljr.moon.imgcompress.compressrule.MNCompressOption;
import com.mljr.moon.imgcompress.engine.EngineFactory;
import com.mljr.moon.imgcompress.model.MNImageModel;
import com.mljr.moon.imgcompress.callback.OnAllThreadResultCallback;
import com.mljr.moon.imgcompress.callback.OnThreadResultCallback;
import com.mljr.moon.imgcompress.callback.state.OnAllFailedCallback;
import com.mljr.moon.imgcompress.callback.state.OnAllSuccessCallback;
import com.mljr.moon.imgcompress.callback.state.OnFinishCallback;
import com.mljr.moon.imgcompress.callback.state.OnInterruptedCallback;
import com.mljr.moon.imgcompress.callback.state.OnStartCallBack;
import com.mljr.moon.imgcompress.compressrule.ICompressRule;
import com.mljr.moon.imgcompress.task.CompressListener;
import com.mljr.moon.imgcompress.task.CompressPicture;
import com.mljr.moon.imgcompress.util.LogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author： fanyafeng
 * Date： 17/11/29 上午10:35
 * Email: fanyafeng@live.cn
 */

public class MNCompressImage {
    private final static String TAG = MNCompressImage.class.getSimpleName();

    /**
     * 线程开始
     */
    private final static int THREAD_START_CODE = 100;

    /**
     * 线程完成
     */
    private final static int THREAD_FINISH_CODE = 101;

    /**
     * 线程被中断
     */
    private final static int THREAD_INTERRUPT_CODE = 102;

    /**
     * 所有线程完成
     */
    private final static int THREAD_ALL_SUCCESS_CODE = 103;

    /**
     * 所有线程执行失败
     */
    private final static int THREAD_ALL_FAILED_CODE = 104;

    /**
     * 任务数量
     */
    private int threadCount = 0;
    /**
     * 线程池核心数
     * <p>
     * 单线程处理，
     * 这样可以保证压缩顺序
     * 如果不需要保证压缩顺序可以进行多核心处理
     */
    private int threadCore = 1;
    /**
     * 线程池
     */
    private ExecutorService executorService;
    /**
     * 计数器
     */
    private CountDownLatch countDownLatch;

    /**
     * 失败回调，默认必须有
     */
    private OnAllFailedCallback onAllFailedCallback;

    /**
     * 成功回调，默认必须有
     */
    private OnAllSuccessCallback onAllSuccessCallback;

    private OnFinishCallback onThreadFinishCallback;

    private OnInterruptedCallback onThreadInterruptedCallback;

    private OnStartCallBack onThreadProgressStartCallBack;


    private CompressHandler compressHandler;

    private List<MNImageModel> dealImageList = new ArrayList<>();

    public List<MNImageModel> getDealImageList() {
        return dealImageList;
    }

    /**
     * 默认构造方法，图片压缩串行
     */
    public MNCompressImage() {
        init();
    }

    /**
     * 自定义压缩线程数，并行
     *
     * @param threadCore
     */
    public MNCompressImage(int threadCore) {
        this.threadCore = threadCore;
        init();
    }


    /**
     * 所有图片压缩失败回调
     *
     * @param onAllFailedCallback
     * @return
     */
    public MNCompressImage setOnAllFailedCallback(OnAllFailedCallback onAllFailedCallback) {
        this.onAllFailedCallback = onAllFailedCallback;
        return this;
    }

    /**
     * 所有图片压缩成功回调
     *
     * @param onAllSuccessCallback
     * @return
     */
    public MNCompressImage setOnAllSuccessCallback(OnAllSuccessCallback onAllSuccessCallback) {
        this.onAllSuccessCallback = onAllSuccessCallback;
        return this;
    }

    /**
     * 某一张图片压缩失败回调
     *
     * @param onThreadFinishCallback
     * @return
     */
    public MNCompressImage setOnFinishCallback(OnFinishCallback onThreadFinishCallback) {
        this.onThreadFinishCallback = onThreadFinishCallback;
        return this;
    }

    /**
     * 某一张图片压缩被打断回调
     *
     * @param onThreadInterruptedCallback
     * @return
     */
    public MNCompressImage setOnInterruptedCallback(OnInterruptedCallback onThreadInterruptedCallback) {
        this.onThreadInterruptedCallback = onThreadInterruptedCallback;
        return this;
    }

    /**
     * 某一张图片压缩开始回调
     *
     * @param onThreadProgressStartCallBack
     * @return
     */
    public MNCompressImage setOnStartCallBack(OnStartCallBack onThreadProgressStartCallBack) {
        this.onThreadProgressStartCallBack = onThreadProgressStartCallBack;
        return this;
    }

    private void init() {
        compressHandler = new CompressHandler(this);
        executorService = Executors.newFixedThreadPool(threadCore + 1);
        initDealPicPathList();
    }

    private void initDealPicPathList() {
        if (dealImageList != null) {
            dealImageList.clear();
        } else {
            dealImageList = new ArrayList<>();
        }
    }

    /**
     * 终止图片压缩
     */
    @MainThread
    public void shutDownNow() {
        if (executorService != null)
            executorService.shutdownNow();
    }

    /**
     * 提交单张图片压缩
     * 使用内置压缩引擎
     * 使用高清模式压缩
     *
     * @param imageBean
     */
    @MainThread
    public void submit(MNImageModel imageBean) {
        submit(Arrays.asList(imageBean), MNCompressConfig.getCompressConfig().getEngineFactory(), MNCompressOption.PHOTO_HD);
    }

    /**
     * 提交单张图片压缩
     * 使用内置压缩引擎
     * 自定义压缩规则需要实现{@link ICompressRule}接口
     *
     * @param imageBean
     * @param iCompressRule
     */
    @MainThread
    public void submit(MNImageModel imageBean, ICompressRule iCompressRule) {
        submit(Arrays.asList(imageBean), MNCompressConfig.getCompressConfig().getEngineFactory(), iCompressRule);
    }

    /**
     * 提交多张图片压缩
     * 自定义压缩引擎
     * 自定义压缩规则需要实现{@link ICompressRule}接口
     * 如果自定义压缩引擎不需要这些规则，可以不用配置
     *
     * @param imageBean
     * @param compressEngineFactory
     * @param iCompressRule
     */
    @MainThread
    public void submit(MNImageModel imageBean, EngineFactory compressEngineFactory, ICompressRule iCompressRule) {
        submit(Arrays.asList(imageBean), compressEngineFactory, iCompressRule);
    }

    /**
     * 类比{@link #submit(MNImageModel)}
     * 使用配置中的压缩引擎
     *
     * @param imageBeanList
     * @param iCompressRule
     */
    @MainThread
    public void submit(List<MNImageModel> imageBeanList, ICompressRule iCompressRule) {
        submit(imageBeanList, MNCompressConfig.getCompressConfig().getEngineFactory(), iCompressRule);
    }

    /**
     * 类比{@link #submit(MNImageModel, ICompressRule)}
     * 使用配置中的引擎
     * 高清压缩
     *
     * @param imageBeanList
     */
    @MainThread
    public void submit(List<MNImageModel> imageBeanList) {
        submit(imageBeanList, MNCompressConfig.getCompressConfig().getEngineFactory(), MNCompressOption.PHOTO_HD);
    }

    /**
     * 类比{@link #submit(MNImageModel, EngineFactory, ICompressRule)}
     * 自定义压缩引擎
     * 自定义压缩规则
     *
     * @param mlImageBeanList
     * @param compressEngineFactory
     * @param compressRule
     */
    @MainThread
    public void submit(final List<MNImageModel> mlImageBeanList, EngineFactory compressEngineFactory, ICompressRule compressRule) {
        threadCount = mlImageBeanList.size();
        countDownLatch = new CountDownLatch(threadCount);

        if (executorService.isShutdown()) {
            executorService = Executors.newFixedThreadPool(threadCore + 1);
        }

        executorService.submit(new CompressListener(countDownLatch, new OnAllThreadResultCallback() {
            @Override
            public void onSuccess() {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("dealPicturePathList", (ArrayList<? extends Parcelable>) dealImageList);
                Message.obtain(compressHandler, THREAD_ALL_SUCCESS_CODE, bundle).sendToTarget();
            }

            @Override
            public void onFailed() {
                compressHandler.sendEmptyMessage(THREAD_ALL_FAILED_CODE);
            }
        }));

        for (int i = 0; i < threadCount; i++) {
            final Bundle bundle = new Bundle();
            LogUtil.e(TAG, "处理图片position：" + i);//图片数量正确
            final MNImageModel mlImageBean = mlImageBeanList.get(i);
            LogUtil.e(TAG, "获取图片position：" + mlImageBean.getPosition());
            bundle.putParcelable("imageBean", mlImageBean);
            executorService.submit(new CompressPicture(countDownLatch, mlImageBean, new OnThreadResultCallback() {
                @Override
                public void onStart() {
                    Message.obtain(compressHandler, THREAD_START_CODE, bundle).sendToTarget();
                }

                @Override
                public void onFinish(MNImageModel imageBean) {
                    imageBean.setSourcePath(mlImageBean.getSourcePath());
                    dealImageList.add(imageBean);
                    bundle.putParcelable("dealImageBean", imageBean);
                    Message.obtain(compressHandler, THREAD_FINISH_CODE, bundle).sendToTarget();
                }

                @Override
                public void onInterrupted() {
                    Message.obtain(compressHandler, THREAD_INTERRUPT_CODE, bundle).sendToTarget();
                }
            }, compressEngineFactory, compressRule));
        }
        executorService.shutdown();
    }

    private static class CompressHandler extends Handler {
        private WeakReference<MNCompressImage> compressPictureUtilWeakReference;

        public CompressHandler(MNCompressImage MLBatchCompressPictureUtil) {
            super(Looper.getMainLooper());
            compressPictureUtilWeakReference = new WeakReference<MNCompressImage>(MLBatchCompressPictureUtil);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MNCompressImage MLBatchCompressPictureUtil = compressPictureUtilWeakReference.get();
            if (MLBatchCompressPictureUtil != null) {
                Bundle bundle = (Bundle) msg.obj;
                MNImageModel mlImageBean;
                switch (msg.what) {
                    case THREAD_START_CODE:
                        mlImageBean = bundle.getParcelable("imageBean");
                        if (MLBatchCompressPictureUtil.onThreadProgressStartCallBack != null) {
                            MLBatchCompressPictureUtil.onThreadProgressStartCallBack.onThreadProgressStart(mlImageBean);
                        }
                        break;
                    case THREAD_FINISH_CODE:
                        mlImageBean = bundle.getParcelable("imageBean");
                        if (MLBatchCompressPictureUtil.onThreadFinishCallback != null) {
                            MLBatchCompressPictureUtil.onThreadFinishCallback.onThreadFinish(mlImageBean);
                        }
                        break;
                    case THREAD_INTERRUPT_CODE:
                        mlImageBean = bundle.getParcelable("imageBean");
                        if (MLBatchCompressPictureUtil.onThreadInterruptedCallback != null) {
                            MLBatchCompressPictureUtil.onThreadInterruptedCallback.onThreadInterrupted(mlImageBean);
                        }
                        break;
                    case THREAD_ALL_SUCCESS_CODE:
                        if (MLBatchCompressPictureUtil.onAllSuccessCallback != null) {
                            List<MNImageModel> mlImageBeanList = bundle.getParcelableArrayList("dealPicturePathList");
                            LogUtil.e(TAG, "传过去的长度size：" + mlImageBeanList.size());
                            MLBatchCompressPictureUtil.onAllSuccessCallback.onAllSuccess(mlImageBeanList);
                        }
                        MLBatchCompressPictureUtil.getDealImageList().clear();
                        break;
                    case THREAD_ALL_FAILED_CODE:
                        if (MLBatchCompressPictureUtil.onAllFailedCallback != null) {
                            MLBatchCompressPictureUtil.onAllFailedCallback.onAllFailed();
                        }
                        break;
                }
            }
        }
    }

}
