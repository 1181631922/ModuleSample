package com.ripple.task.engine

import java.io.Serializable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Author: fanyafeng
 * Data: 2020/5/6 17:51
 * Email: fanyafeng@live.cn
 * Description: 使用java自带的任务服务
 */
interface ProcessEngine : Serializable {


    companion object {
        /**
         * 单线程处理器
         * 处理任务为串行处理
         */
        val SINGLE_THREAD_EXECUTOR: ProcessEngine =
            object : ProcessEngine {
                override fun getExecutorService(): ExecutorService {
                    return Executors.newSingleThreadExecutor()
                }

            }

        /**
         * 自带6个线程的处理器
         * 不用纠结个数为什么这么定义，纯属个人喜欢的数字
         * 处理任务为并行处理，并且顺序是打乱的
         */
        val MULTI_THREAD_EXECUTOR: ProcessEngine =
            object : ProcessEngine {
                override fun getExecutorService(): ExecutorService {
                    return Executors.newFixedThreadPool(6)
                }

            }
    }

    /**
     * 获取任务处理任务
     */
    fun getExecutorService(): ExecutorService

}