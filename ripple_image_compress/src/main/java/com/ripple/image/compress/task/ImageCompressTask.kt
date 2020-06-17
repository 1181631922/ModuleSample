package com.ripple.image.compress.task

import com.ripple.image.compress.model.ImageItem
import com.ripple.image.compress.service.ImageCompressService
import com.ripple.task.callback.OnItemInterrupted
import com.ripple.task.callback.OnItemStart
import com.ripple.task.callback.OnItemSuccess
import com.ripple.task.util.LogUtil
import java.io.Serializable
import java.util.concurrent.CountDownLatch

/**
 * Author: fanyafeng
 * Data: 2020/5/7 09:29
 * Email: fanyafeng@live.cn
 * Description: 同步压缩任务接口
 */
interface ImageCompressTask : Runnable, Serializable {
    fun getCountDownLatch(): CountDownLatch

    fun getOnItemStart(): OnItemStart<ImageItem>

    fun getOnItemInterrupted(): OnItemInterrupted<ImageItem>

    fun getOnItemSuccess(): OnItemSuccess<ImageItem>

    fun getImageCompressService(): ImageCompressService

    fun getImageItem(): ImageItem

    override fun run() {
        //开始压缩
        getOnItemStart().onItemStart(getImageItem())

        try {
            //获取压缩服务，进行压缩
            val targetFile = getImageCompressService().compress(getImageItem())
            if (targetFile.length() > 0) {
                //压缩完成
                getOnItemSuccess().onItemSuccess(getImageItem())
            } else {
                //文件为空
                getOnItemInterrupted().onItemInterrupted(getImageItem())
            }
        } catch (e: Exception) {
            //压缩被打断
            getOnItemInterrupted().onItemInterrupted(getImageItem())
            e.printStackTrace()
            LogUtil.d(msg = "catch exception走一次")
        } finally {
            //压缩失败，进行停止
            getCountDownLatch().countDown()
        }
    }

}