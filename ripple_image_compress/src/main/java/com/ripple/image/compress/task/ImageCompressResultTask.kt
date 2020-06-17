package com.ripple.image.compress.task

import com.ripple.task.callback.OnFailed
import com.ripple.task.callback.OnFinish
import com.ripple.task.callback.OnSuccess
import java.io.Serializable
import java.util.concurrent.CountDownLatch

/**
 * Author: fanyafeng
 * Data: 2020/5/7 17:24
 * Email: fanyafeng@live.cn
 * Description: 图片压缩任务列表结果封装
 */
interface ImageCompressResultTask<T> : Runnable,Serializable {

    fun getCountDownLatch(): CountDownLatch

    fun getOnFailed(): OnFailed<T>

    fun getOnSuccess(): OnSuccess<T>

    fun getOnFinish(): OnFinish<T>

    override fun run() {
        try {
            getCountDownLatch().await()
            getOnSuccess().onSuccess(null)
        } catch (e: Exception) {
            getOnFailed().onFailed(null)
        }
        getOnFinish().onFinish(null, null)
    }
}