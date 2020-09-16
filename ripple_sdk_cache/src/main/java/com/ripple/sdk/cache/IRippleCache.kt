package com.ripple.sdk.cache

import android.content.Context
import java.io.Serializable


/**
 * Author: fanyafeng
 * Date: 2020/8/31 18:05
 * Email: fanyafeng@live.cn
 * Description:
 */
interface IRippleCache<K, V> : Serializable {
    /**
     * 存入数据
     */
    fun put(key: K, value: V): V?

    /**
     * 根据key获取数据
     */
    fun get(key: K): V?

    /**
     * 存入sp中
     */
    fun commit(context: Context)
}