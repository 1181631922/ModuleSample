package com.ripple.image.compress.engine.impl

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.ripple.image.compress.engine.ImageCompressEngine
import com.ripple.image.compress.model.ImageItem
import com.ripple.image.compress.service.ImageCompressService
import com.ripple.image.compress.service.impl.ImageCompressServiceImpl
import com.ripple.image.compress.task.impl.ImageCompressResultTaskImpl
import com.ripple.image.compress.task.impl.ImageCompressTaskImpl
import com.ripple.image.compress.RippleImageCompress
import com.ripple.task.callback.*
import com.ripple.task.engine.ProcessEngine
import java.lang.ref.WeakReference
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

/**
 * Author: fanyafeng
 * Data: 2020/5/6 17:50
 * Email: fanyafeng@live.cn
 * Description: 图片压缩引擎的实现
 */
class ImageCompressEngineImpl : ImageCompressEngine {

    companion object {
        //任务开始
        const val CODE_START = OnItemStart.CODE_ITEM_START

        //任务被打断
        const val CODE_INTERRUPT = OnItemInterrupted.CODE_ITEM_INTERRUPTED

        //任务结束
        const val CODE_FINISH = OnItemFinish.CODE_ITEM_FINISH

        //所有任务成功
        const val CODE_ALL_SUCCESS = OnSuccess.CODE_SUCCESS

        //所有任务失败
        const val CODE_ALL_FAILED = OnFailed.CODE_FAILED

        //所有任务完成
        const val CODE_ALL_FINISH = OnFinish.CODE_FINISH
    }

    /**
     * 同步计数器
     */
    private var mCountDownLatch: CountDownLatch? = null

    /**
     * 单项任务开始回调
     */
    var onItemStart: OnItemStart<ImageItem>? = null

    /**
     * 单项任务被打断回调
     */
    var onItemInterrupted: OnItemInterrupted<ImageItem>? = null

    /**
     * 单项任务完成回调
     * 未完成就是被打断
     */
    var onItemSuccess: OnItemSuccess<ImageItem>? = null

    /**
     * 所有任务完成后失败回调
     */
    var onFailed: OnFailed<List<ImageItem>>? = null

    /**
     * 所有任务完成后成功回调
     */
    var onSuccess: OnSuccess<List<ImageItem>>? = null

    /**
     * 任务完成回调
     */
    var onFinish: OnFinish<List<ImageItem>>? = null

    /**
     * 图片压缩任务
     * 允许用户从外部注入
     */
    var mCompressService: ImageCompressService =
        RippleImageCompress.getImageCompressService() ?: ImageCompressServiceImpl()

    /**
     * 压缩任务
     * 允许用户从外部注入
     */
    var executorProcess: ProcessEngine =
        RippleImageCompress.getImageTaskEngine() ?: ProcessEngine.MULTI_THREAD_EXECUTOR_MAX

    private val compressHandler = ImageCompressHandler(this)

    private var threadCount = 0

    /**
     * 失败任务结果
     */
    private val failedImageResult: ArrayList<ImageItem> = arrayListOf()

    /**
     * 成功任务结果
     */
    private val successImageResult: ArrayList<ImageItem> = arrayListOf()


    init {
        failedImageResult.clear()
        successImageResult.clear()
    }

    /**
     * 压缩单张图片
     */
    fun compressImage(image: ImageItem) {
        compressImageList(listOf(image))
    }

    private var executorServiceInner: ExecutorService? = null


    /**
     * 压缩图片列表
     */
    fun compressImageList(imageList: List<ImageItem>) {
        threadCount = imageList.size
        mCountDownLatch = CountDownLatch(threadCount)

        var executorService = executorProcess.getExecutorService()
        if (executorService.isShutdown) {
            executorService = Executors.newFixedThreadPool(Thread.MAX_PRIORITY)
        }
        executorServiceInner = Executors.newSingleThreadExecutor()
        executorServiceInner?.submit(
            ImageCompressResultTaskImpl(mCountDownLatch!!,
                object : OnFailed<List<ImageItem>> {
                    override fun onFailed(failedResult: List<ImageItem>?) {
                        val bundle = Bundle()
                        bundle.putSerializable(
                            ImageCompressEngine.IMAGE_LIST_FAILED,
                            failedImageResult
                        )
                        Message.obtain(compressHandler, CODE_ALL_FAILED, bundle).sendToTarget()
                    }

                }, object : OnSuccess<List<ImageItem>> {
                    override fun onSuccess(successResult: List<ImageItem>?) {
                        val bundle = Bundle()
                        bundle.putSerializable(
                            ImageCompressEngine.IMAGE_LIST_SUCCESS,
                            successImageResult
                        )
                        Message.obtain(compressHandler, CODE_ALL_SUCCESS, bundle).sendToTarget()
                    }

                }, object : OnFinish<List<ImageItem>> {
                    override fun onFinish(
                        finishResult: List<ImageItem>?,
                        unFinishResult: List<ImageItem>?
                    ) {
                        val bundle = Bundle()
                        bundle.putSerializable(
                            ImageCompressEngine.IMAGE_LIST_SUCCESS,
                            successImageResult
                        )
                        bundle.putSerializable(
                            ImageCompressEngine.IMAGE_LIST_FAILED,
                            failedImageResult
                        )
                        Message.obtain(compressHandler, CODE_ALL_FINISH, bundle).sendToTarget()
                        executorServiceInner?.shutdown()
                    }

                })
        )



        imageList.forEachIndexed { _, imageItem ->
            val bundle = Bundle()
            bundle.putSerializable(ImageCompressEngine.IMAGE_ITEM, imageItem)

            executorService.submit(
                ImageCompressTaskImpl(imageItem, mCountDownLatch, object : OnItemStart<ImageItem> {
                    override fun onItemStart(startResult: ImageItem) {
                        //开始回调
//                        LogUtil.d(
//                            tag = "onItemStart",
//                            msg = "测试返回值  原路径:" + startResult.getSourcePath() + "，压缩后的路径:" + startResult.getCompressConfig()
//                                .getTargetPath()
//                        )
                        Message.obtain(compressHandler, CODE_START, bundle).sendToTarget()
                    }

                }, object : OnItemInterrupted<ImageItem> {
                    override fun onItemInterrupted(interruptedResult: ImageItem) {
                        //失败回调
//                        LogUtil.d(
//                            tag = "onItemInterrupted",
//                            msg = "测试返回值  原路径:" + interruptedResult.getSourcePath() + "，压缩后的路径:" + interruptedResult.getCompressConfig()
//                                .getTargetPath()
//                        )
                        failedImageResult.add(imageItem)
                        Message.obtain(compressHandler, CODE_INTERRUPT, bundle).sendToTarget()
                    }

                }, object :
                    OnItemSuccess<ImageItem> {
                    override fun onItemSuccess(successResult: ImageItem) {
                        //成功回调
//                        LogUtil.d(
//                            tag = "onItemFinish",
//                            msg = "测试返回值  原路径:" + successResult.getSourcePath() + "，压缩后的路径:" + successResult.getCompressConfig()
//                                .getTargetPath()
//                        )
                        successImageResult.add(imageItem)
                        Message.obtain(compressHandler, CODE_INTERRUPT, bundle).sendToTarget()
                    }

                }, mCompressService)
            )

        }


    }

    class ImageCompressHandler(compressEngineImpl: ImageCompressEngineImpl) :
        Handler(Looper.getMainLooper()) {

        private val compressEngineWeakReference: WeakReference<ImageCompressEngineImpl> =
            WeakReference(compressEngineImpl)


        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val engine = compressEngineWeakReference.get()
            if (engine != null) {
                val bundle = msg.obj as Bundle
                val imageItem: ImageItem
                val successResult: List<ImageItem>
                val failedResult: List<ImageItem>
                when (msg.what) {
                    CODE_START -> {
                        imageItem =
                            bundle.getSerializable(ImageCompressEngine.IMAGE_ITEM) as ImageItem
                        engine.onItemStart?.onItemStart(imageItem)
                    }
                    CODE_INTERRUPT -> {
                        imageItem =
                            bundle.getSerializable(ImageCompressEngine.IMAGE_ITEM) as ImageItem
                        engine.onItemInterrupted?.onItemInterrupted(imageItem)
                    }
                    CODE_FINISH -> {
                        imageItem =
                            bundle.getSerializable(ImageCompressEngine.IMAGE_ITEM) as ImageItem
                        engine.onItemSuccess?.onItemSuccess(imageItem)
                    }
                    CODE_ALL_SUCCESS -> {
                        successResult =
                            bundle.getSerializable(ImageCompressEngine.IMAGE_LIST_SUCCESS) as List<ImageItem>
                        engine.onSuccess?.onSuccess(successResult)
                    }
                    CODE_ALL_FAILED -> {
                        failedResult =
                            bundle.getSerializable(ImageCompressEngine.IMAGE_LIST_FAILED) as List<ImageItem>
                        engine.onFailed?.onFailed(failedResult)
                    }
                    CODE_ALL_FINISH -> {
                        successResult =
                            bundle.getSerializable(ImageCompressEngine.IMAGE_LIST_SUCCESS) as List<ImageItem>
                        failedResult =
                            bundle.getSerializable(ImageCompressEngine.IMAGE_LIST_FAILED) as List<ImageItem>
                        engine.onFinish?.onFinish(successResult, failedResult)
                    }
                }

            }
        }

    }

    override fun getTaskEngine(): ProcessEngine {
        return executorProcess
    }

    override fun getImageCompressService(): ImageCompressService {
        return mCompressService
    }


}