package com.ripple.task.task

import com.ripple.task.callback.result.OnAllResult
import com.ripple.task.config.ProcessModel
import java.io.Serializable
import java.util.concurrent.CountDownLatch

/**
 * Author: fanyafeng
 * Data: 2020/6/3 20:08
 * Email: fanyafeng@live.cn
 * Description:
 */
interface ProcessAllResultTask<T : List<ProcessModel>> : Runnable, Serializable {

    fun getCountDownLatch(): CountDownLatch

    fun getAllResult(): OnAllResult<T>

    override fun run() {
        val allResult = getAllResult()
        try {
            allResult.onStart()
            allResult.onDoing(null)
            getCountDownLatch().await()
            getAllResult().onSuccess(null)
        } catch (e: Exception) {
            getAllResult().onFailed(null)
        } finally {
            getAllResult().onFinish(null, null)
        }
    }
}