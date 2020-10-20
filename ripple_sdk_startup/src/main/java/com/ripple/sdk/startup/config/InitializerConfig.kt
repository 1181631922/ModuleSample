package com.ripple.sdk.startup.config

import com.ripple.sdk.startup.Initializer

/**
 * Author: fanyafeng
 * Date: 2020/9/25 16:23
 * Email: fanyafeng@live.cn
 * Description:
 *
 * 主要解决官方库的几个问题：
 * 1.官方默认初始化是在主线程，但是有些特别耗时的需要放在子线程，并且还需要获取到初始化的结果
 * 2.有些用户想手动指定延迟初始化时间
 * 3.官方无法进行链式初始化，这里为了方便进行链式初始化调用
 */
class InitializerConfig private constructor(builder: Builder) {

    /**
     * 需要进行初始化的依赖
     */
    @get:JvmName("component")
    val component: Class<out Initializer<*>>

    /**
     * 是否在主线程中进行初始化
     */
    @get:JvmName("isMainThread")
    val isMainThread: Boolean

    /**
     * 延迟初始化时间
     */
    @get:JvmName("delayMillis")
    val delayMillis: Long

    init {
        component = builder.component
        isMainThread = builder.isMainThread
        delayMillis = builder.delayMillis
    }

    open class Builder(val component: Class<out Initializer<*>>) {

        internal var isMainThread: Boolean = true

        internal var delayMillis: Long = 0L

        fun setIsMainThread(isMainThread: Boolean) = apply {
            this.isMainThread = isMainThread
        }

        fun setDelayMillis(delayMillis: Long) = apply {
            this.delayMillis = delayMillis
        }

        fun build(): InitializerConfig =
            InitializerConfig(Builder(component))
    }

}