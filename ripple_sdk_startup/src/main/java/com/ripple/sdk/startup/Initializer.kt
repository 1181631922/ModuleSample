package com.ripple.sdk.startup

import android.content.Context

/**
 * Author: fanyafeng
 * Date: 2020/9/25 10:10
 * Email: fanyafeng@live.cn
 * Description:
 */
/**
 * [Initializer]s can be used to initialize libraries during app startup, without
 * the need to use additional [android.content.ContentProvider]s.
 *
 * @param <T> The instance type being initialized
 *
 * 初始化的方法需要实现的接口
 *
 * </T>
 */
interface Initializer<T> {
    /**
     * Initializes and a component given the application [Context]
     *
     * @param context The application context.
     *
     * 创建需要初始化的实例
     */
    fun create(context: Context): T

    /**
     * @return A list of dependencies that this [Initializer] depends on. This is
     * used to determine initialization order of [Initializer]s.
     * <br></br>
     * For e.g. if a [Initializer] `B` defines another
     * [Initializer] `A` as its dependency, then `A` gets initialized before `B`.
     *
     * 在此类初始化之前先去初始化此module所依赖的module
     */
    fun dependencies(): List<Class<out Initializer<*>?>?>
}