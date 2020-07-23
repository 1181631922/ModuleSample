package com.ripple.image.compress.extend

import com.ripple.image.compress.engine.impl.ImageCompressEngineImpl
import com.ripple.image.compress.model.ImageItem
import com.ripple.image.compress.service.ImageCompressService
import com.ripple.image.compress.RippleImageCompress
import com.ripple.task.callback.*
import com.ripple.tool.kttypelians.PairLambda
import com.ripple.tool.kttypelians.SuccessLambda
import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/5/8 09:50
 * Email: fanyafeng@live.cn
 * Description: 对图片压缩支持lambda调用
 */

/**
 * 异步压缩单张图片
 */
fun compressImage(imageItem: ImageItem, lambda: ImageCompressExtra.() -> Unit) {
    compressImageList(listOf(imageItem), lambda)
}

/**
 * 同步单张压缩
 */
fun compressImageSync(imageCompressService: ImageCompressService, imageItem: ImageItem) =
    RippleImageCompress.compressImageSync(imageCompressService, imageItem)


fun compressImageSync(imageItem: ImageItem) =
    RippleImageCompress.compressImageSync(imageItem)


/**
 * 同步批量压缩
 */
fun compressImageListSync(imageList: List<ImageItem>): List<File> =
    RippleImageCompress.compressImageListSync(imageList)

/**
 * 异步批量压缩
 */
fun compressImageList(
    imageList: List<ImageItem>,
    lambda: ImageCompressExtra.() -> Unit
) {
    val imageCompressExtra = ImageCompressExtra(imageList)
    imageCompressExtra.apply {
        lambda()
    }
}


class ImageCompressExtra(imageList: List<ImageItem>) {

    private var itemStartLambda: SuccessLambda<ImageItem> = null
    private var itemInterruptedLambda: SuccessLambda<ImageItem> = null
    private var itemSuccessLambda: SuccessLambda<ImageItem> = null
    private var failedLambda: SuccessLambda<List<ImageItem>?> = null
    private var successLambda: SuccessLambda<List<ImageItem>?> = null
    private var finishLambda: PairLambda<List<ImageItem>?, List<ImageItem>?> = null

    private val engine = ImageCompressEngineImpl()


    init {
        engine.compressImageList(imageList)
    }


    fun onItemStart(itemStartLambda: SuccessLambda<ImageItem>) {
        this.itemStartLambda = itemStartLambda
        engine.onItemStart = object : OnItemStart<ImageItem> {
            override fun onItemStart(startResult: ImageItem) {
                this@ImageCompressExtra.itemStartLambda?.invoke(startResult)
            }
        }
    }

    fun onItemInterrupted(itemInterruptedLambda: SuccessLambda<ImageItem>) {
        this.itemInterruptedLambda = itemInterruptedLambda
        engine.onItemInterrupted = object : OnItemInterrupted<ImageItem> {
            override fun onItemInterrupted(interruptedResult: ImageItem) {
                this@ImageCompressExtra.itemInterruptedLambda?.invoke(interruptedResult)
            }
        }
    }

    fun onItemSuccess(itemSuccessLambda: SuccessLambda<ImageItem>) {
        this.itemSuccessLambda = itemSuccessLambda
        engine.onItemSuccess = object : OnItemSuccess<ImageItem> {
            override fun onItemSuccess(successResult: ImageItem) {
                this@ImageCompressExtra.itemSuccessLambda?.invoke(successResult)
            }
        }
    }

    fun onFailed(failedLambda: SuccessLambda<List<ImageItem>?>) {
        this.failedLambda = failedLambda
        engine.onFailed = object : OnFailed<List<ImageItem>> {
            override fun onFailed(failedResult: List<ImageItem>?) {
                this@ImageCompressExtra.failedLambda?.invoke(failedResult)
            }
        }
    }

    fun onSuccess(successLambda: SuccessLambda<List<ImageItem>?>) {
        this.successLambda = successLambda
        engine.onSuccess = object : OnSuccess<List<ImageItem>> {
            override fun onSuccess(successResult: List<ImageItem>?) {
                this@ImageCompressExtra.successLambda?.invoke(successResult)
            }
        }
    }

    fun onFinish(
        finishLambda: PairLambda<List<ImageItem>?, List<ImageItem>?>
    ) {
        this.finishLambda = finishLambda
        engine.onFinish = object : OnFinish<List<ImageItem>> {
            override fun onFinish(
                finishResult: List<ImageItem>?,
                unFinishResult: List<ImageItem>?
            ) {
                this@ImageCompressExtra.finishLambda?.invoke(finishResult, unFinishResult)
            }

        }
    }

}