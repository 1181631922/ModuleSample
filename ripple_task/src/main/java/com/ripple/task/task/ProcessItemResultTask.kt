package com.ripple.task.task

import com.ripple.task.callback.result.OnItemResult
import com.ripple.task.config.ProcessModel
import java.io.Serializable
import java.util.concurrent.CountDownLatch

/**
 * Author: fanyafeng
 * Data: 2020/6/4 09:55
 * Email: fanyafeng@live.cn
 * Description:
 */
interface ProcessItemResultTask<T : ProcessModel> : Runnable, Serializable {

    fun getProcessModel(): T

    fun getCountDownLatch(): CountDownLatch

    fun getItemResult(): OnItemResult<T>?


    override fun run() {
        val itemResult = getItemResult()
        val processModel = getProcessModel()
        itemResult?.onItemStart(processModel)

        val sourcePath = processModel.getSourcePath()
        val targetPath = processModel.getTargetPath()

        try {
            itemResult?.onItemDoing(processModel)
            val target = processModel.parse(sourcePath, targetPath)
            processModel.setTargetPath(target)
            itemResult?.onItemSuccess(processModel)
        } catch (e: Exception) {
            itemResult?.onItemFailed(processModel)
            itemResult?.onItemInterrupted(processModel)
        } finally {
            itemResult?.onItemFinish(processModel)
            getCountDownLatch().countDown()
        }
    }
}