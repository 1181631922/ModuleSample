package com.ripple.task.engine

import java.io.Serializable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * Author: fanyafeng
 * Data: 2020/6/11 14:48
 * Email: fanyafeng@live.cn
 * Description: 预定处理器引擎
 *
 *
 */
interface ScheduledProcessEngine : Serializable {

    companion object {
        /**
         * 单线程处理器
         * 处理任务为串行处理
         */
        val SINGLE_THREAD_EXECUTOR: ScheduledProcessEngine = object : ScheduledProcessEngine {
            private val executor = Executors.newSingleThreadScheduledExecutor()

            override fun getScheduledProcessService(): ScheduledExecutorService {
                return executor
            }

            override fun shutdown() {
                executor.shutdown()
            }


        }

        /**
         * 处理任务为并行处理，并且顺序是打乱的
         */
        val MULTI_THREAD_EXECUTOR_MAX: ScheduledProcessEngine = object : ScheduledProcessEngine {
            private val executor = Executors.newScheduledThreadPool(Thread.MAX_PRIORITY)
            override fun getScheduledProcessService(): ScheduledExecutorService {
                return executor
            }

            override fun shutdown() {
                executor.shutdown()
            }
        }

        val MULTI_THREAD_EXECUTOR_NORMAL: ScheduledProcessEngine = object : ScheduledProcessEngine {
            private val executor = Executors.newScheduledThreadPool(Thread.NORM_PRIORITY)
            override fun getScheduledProcessService(): ScheduledExecutorService {
                return executor
            }

            override fun shutdown() {
            }
        }

        val MULTI_THREAD_EXECUTOR_MIN: ScheduledProcessEngine = object : ScheduledProcessEngine {
            private val executor = Executors.newScheduledThreadPool(Thread.MIN_PRIORITY)
            override fun getScheduledProcessService(): ScheduledExecutorService {
                return executor
            }

            override fun shutdown() {
                executor.shutdown()
            }
        }
    }


    /**
     *
     * 延迟任务处理
     *
     * [java.util.concurrent.ScheduledExecutorService]
     */
    fun getScheduledProcessService(): ScheduledExecutorService

    /**
     * 停止任务
     */
    fun shutdown()
}