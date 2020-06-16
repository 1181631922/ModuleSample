package com.ripple.task.engine

import java.io.Serializable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import kotlin.math.min

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

        private var singleExecutor = Executors.newSingleThreadScheduledExecutor()

        val SINGLE_THREAD_EXECUTOR: ScheduledProcessEngine = object : ScheduledProcessEngine {

            override fun getScheduledProcessService(): ScheduledExecutorService {
                return if (!singleExecutor.isShutdown) {
                    singleExecutor
                } else {
                    singleExecutor = Executors.newSingleThreadScheduledExecutor()
                    singleExecutor
                }
            }

            override fun shutdown() {
                singleExecutor.shutdown()
            }
        }

        /**
         * 处理任务为并行处理，并且顺序是打乱的
         */
        private var maxExecutor = Executors.newScheduledThreadPool(Thread.MAX_PRIORITY)

        val MULTI_THREAD_EXECUTOR_MAX: ScheduledProcessEngine = object : ScheduledProcessEngine {

            override fun getScheduledProcessService(): ScheduledExecutorService {
                return if (!maxExecutor.isShutdown) {
                    maxExecutor
                } else {
                    maxExecutor = Executors.newScheduledThreadPool(Thread.MAX_PRIORITY)
                    maxExecutor
                }
            }

            override fun shutdown() {
                maxExecutor.shutdown()
            }
        }

        private var normalExecutor = Executors.newScheduledThreadPool(Thread.NORM_PRIORITY)

        val MULTI_THREAD_EXECUTOR_NORMAL: ScheduledProcessEngine = object : ScheduledProcessEngine {
            override fun getScheduledProcessService(): ScheduledExecutorService {
                return if (!normalExecutor.isShutdown) {
                    normalExecutor
                } else {
                    normalExecutor = Executors.newScheduledThreadPool(Thread.NORM_PRIORITY)
                    normalExecutor
                }
            }

            override fun shutdown() {
                normalExecutor.shutdown()
            }
        }

        private var minExecutor = Executors.newScheduledThreadPool(Thread.MIN_PRIORITY)

        val MULTI_THREAD_EXECUTOR_MIN: ScheduledProcessEngine = object : ScheduledProcessEngine {

            override fun getScheduledProcessService(): ScheduledExecutorService {
                return if (!minExecutor.isShutdown) {
                    minExecutor
                } else {
                    minExecutor = Executors.newScheduledThreadPool(Thread.MIN_PRIORITY)
                    minExecutor
                }
            }

            override fun shutdown() {
                minExecutor.shutdown()
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