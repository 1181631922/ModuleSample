package com.ripple.image.compress.task.impl

import com.ripple.image.compress.model.ImageItem
import com.ripple.image.compress.task.ImageCompressResultTask
import com.ripple.task.callback.OnFailed
import com.ripple.task.callback.OnFinish
import com.ripple.task.callback.OnSuccess
import java.util.concurrent.CountDownLatch

/**
 * Author: fanyafeng
 * Data: 2020/5/7 17:33
 * Email: fanyafeng@live.cn
 * Description: 压缩任务列表结果简单实现
 */
class ImageCompressResultTaskImpl(
    private val countDownLatch: CountDownLatch,
    private val onFailed: OnFailed<List<ImageItem>>,
    private val onSuccess: OnSuccess<List<ImageItem>>,
    private val onFinish: OnFinish<List<ImageItem>>
) : ImageCompressResultTask<List<ImageItem>> {
    override fun getCountDownLatch(): CountDownLatch {
        return countDownLatch
    }

    override fun getOnFailed(): OnFailed<List<ImageItem>> {
        return onFailed
    }

    override fun getOnSuccess(): OnSuccess<List<ImageItem>> {
        return onSuccess
    }

    override fun getOnFinish(): OnFinish<List<ImageItem>> {
        return onFinish
    }
}