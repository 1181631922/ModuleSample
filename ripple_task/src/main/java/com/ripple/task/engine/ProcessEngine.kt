package com.ripple.task.engine

import java.io.Serializable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

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

                private val executor = Executors.newFixedThreadPool(2)
                override fun getExecutorService(): ExecutorService {
                    return executor
                }

                override fun shutdown() {
                    executor.shutdown()
                }

            }

        /**
         * 自带6个线程的处理器
         * 不用纠结个数为什么这么定义，纯属个人喜欢的数字
         * 处理任务为并行处理，并且顺序是打乱的
         */
        val MULTI_THREAD_EXECUTOR_MAX: ProcessEngine =
            object : ProcessEngine {

                private val executor = Executors.newFixedThreadPool(2)
                override fun getExecutorService(): ExecutorService {
                    return executor
                }

                override fun shutdown() {
                    executor.shutdown()
                }

            }

        val MULTI_THREAD_EXECUTOR_NORMAL: ProcessEngine =
            object : ProcessEngine {

                private val executor = Executors.newFixedThreadPool(Thread.NORM_PRIORITY)
                override fun getExecutorService(): ExecutorService {
                    return executor
                }

                override fun shutdown() {
                    executor.shutdown()
                }

            }

        val MULTI_THREAD_EXECUTOR_MIN: ProcessEngine =
            object : ProcessEngine {

                private val executor = Executors.newFixedThreadPool(Thread.MIN_PRIORITY)
                override fun getExecutorService(): ExecutorService {
                    return executor
                }

                override fun shutdown() {
                    executor.shutdown()
                }

            }
    }

    /**
     * 获取任务处理任务
     *
     * 1、线程池： 提供一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁的额外开销，提高了响应的速度。
     *
     * [java.util.concurrent.Executor]
     * [java.util.concurrent.ExecutorService]
     * [java.util.concurrent.ThreadPoolExecutor]
     * [java.util.concurrent.ScheduledExecutorService]
     * [java.util.concurrent.ScheduledThreadPoolExecutor]
     *
     * 2、线程池的体系结构：
     * java.util.concurrent.Executor 负责线程的使用和调度的根接口
     *      |--ExecutorService 子接口： 线程池的主要接口
     *              |--ThreadPoolExecutor 线程池的实现类
     *              |--ScheduledExecutorService 子接口： 负责线程的调度
     *                      |--ScheduledThreadPoolExecutor : 继承ThreadPoolExecutor，实现了ScheduledExecutorService
     *
     * [java.util.concurrent.Executor]
     *
     * 3、工具类 ： Executors
     * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
     * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
     * ExecutorService newSingleThreadExecutor() : 创建单个线程池。 线程池中只有一个线程
     *
     * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务
     */
    fun getExecutorService(): ExecutorService

    /**
     * 停止任务
     */
    fun shutdown()

}