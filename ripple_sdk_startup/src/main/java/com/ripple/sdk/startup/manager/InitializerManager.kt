package com.ripple.sdk.startup.manager

import android.content.Context
import com.ripple.sdk.startup.AppInitializer
import com.ripple.sdk.startup.config.InitializerConfig
import java.util.*


/**
 * Author: fanyafeng
 * Date: 2020/9/28 09:39
 * Email: fanyafeng@live.cn
 * Description:
 *
 * 1.支持用户手动构建component初始化链表
 */
class InitializerManager {

    private var appInitializerInstance: AppInitializer? = null

    /**
     * 需要提前初始化的component
     * 有序
     */
    private val beforeConfigList = LinkedList<InitializerConfig>()

    /**
     * 构建初始化component链表
     * 有序
     */
    private val configList = LinkedList<InitializerConfig>()

    /**
     * 批量构建，可配置多线程同时初始化
     * 批量还是有序的
     */
    private val batchConfigList = LinkedList<LinkedList<InitializerConfig>>()

    /**
     * 所有需要初始化的component列表
     */
    private val allConfigList = LinkedList<LinkedList<InitializerConfig>>()


    /**
     * 初始化AppInitializer
     * 归根到第最后的实现都是AppInitializer
     */
    fun getInstance(context: Context) = apply {
        beforeConfigList.clear()
        configList.clear()
        batchConfigList.clear()
        allConfigList.clear()
        appInitializerInstance = AppInitializer.getInstance(context)
    }

    /**
     * 最开始需要初始化的component
     * 如果没有则会根据配置进行预初始化
     */
    fun setNeedBeforeInit(needBeforeInitializer: InitializerConfig) = apply {
        beforeConfigList.add(needBeforeInitializer)
    }

    fun addAppInitializerConfig(item: InitializerConfig) = apply {
        configList.add(item)
    }

    fun addAppInitializerConfigList(configList: LinkedList<InitializerConfig>) = apply {
        batchConfigList.add(configList)
    }

    fun init() {

    }


}