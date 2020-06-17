package com.ripple.image.compress.task.impl

import com.ripple.image.compress.model.ImageItem
import com.ripple.image.compress.service.ImageCompressService
import com.ripple.image.compress.task.ImageCompressTask
import com.ripple.task.callback.OnItemInterrupted
import com.ripple.task.callback.OnItemStart
import com.ripple.task.callback.OnItemSuccess
import com.ripple.task.util.Preconditions
import java.util.concurrent.CountDownLatch

/**
 * Author: fanyafeng
 * Data: 2020/5/7 09:36
 * Email: fanyafeng@live.cn
 * Description: 图片压缩任务简单实现
 */
class ImageCompressTaskImpl @JvmOverloads constructor(
    val mImageItem: ImageItem? = null,
    val mCountDownLatch: CountDownLatch? = null,
    var mOnItemStart: OnItemStart<ImageItem>? = null,
    var mOnItemInterrupted: OnItemInterrupted<ImageItem>? = null,
    var mOnItemSuccess: OnItemSuccess<ImageItem>? = null,
    var mCompressService: ImageCompressService? = null
) : ImageCompressTask {


    override fun getCountDownLatch(): CountDownLatch {
        return Preconditions.checkNotNull(mCountDownLatch)
    }

    override fun getOnItemStart(): OnItemStart<ImageItem> {
        return Preconditions.checkNotNull(mOnItemStart)
    }

    override fun getOnItemInterrupted(): OnItemInterrupted<ImageItem> {
        return Preconditions.checkNotNull(mOnItemInterrupted)
    }

    override fun getOnItemSuccess(): OnItemSuccess<ImageItem> {
        return Preconditions.checkNotNull(mOnItemSuccess)
    }

    override fun getImageCompressService(): ImageCompressService {
        return Preconditions.checkNotNull(mCompressService)
    }

    override fun getImageItem(): ImageItem {
        return Preconditions.checkNotNull(mImageItem)
    }
}