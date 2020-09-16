package com.ripple.sdk.cache

import android.content.Context
import com.ripple.log.extend.logD
import com.ripple.sdk.cache.impl.RippleFifoCache
import com.ripple.sdk.cache.impl.RippleLruCache
import java.lang.Exception


/**
 * Author: fanyafeng
 * Data: 2020/8/17 09:34
 * Email: fanyafeng@live.cn
 * Description:
 *
 * 每一个ripple cache都对应一个key
 * 有可能会有多个缓存表，每个缓存表都有可能对应一种缓存策略方法
 *
 * 首先需要根据key去获取表，再从表中获取数据
 */
class RippleCache private constructor() {

    companion object {
        /**
         * Ripple缓存框架内部最近最久未使用缓存表
         */
        internal const val CACHE = "ripple_cache_list"
        internal const val LRU_CACHE_KEY = "ripple_lru_cache_key"

        /**
         * Ripple缓存框架内部先进先出缓存表
         */
        internal const val FIFO_CACHE_KEY = "ripple_fifo_cache_key"

        @Volatile
        private var instance: RippleCache? = null

        @JvmStatic
        fun getInstance(): RippleCache {
            if (instance == null) {
                synchronized(RippleCache::class) {
                    if (instance == null) {
                        instance =
                            RippleCache()
                    }
                }
            }
            return instance!!
        }

        @JvmStatic
        @JvmOverloads
        fun initialize(
            context: Context,
            lruCache: IRippleCache<String, String> = RippleLruCache(context, 100),
            fifoCache: IRippleCache<String, String> = RippleFifoCache(context, 100)
        ) {
            getInstance().lruCache = lruCache
            getInstance().fifoCache = fifoCache
        }

        @JvmStatic
        fun commit(context: Context) {
            getInstance().lruCache?.commit(context)
            getInstance().fifoCache?.commit(context)
        }
    }


    var lruCache: IRippleCache<String, String>? = null
        /*
        如果缓存为空的话则创建缓存实例，否则直接获取
         */
        get() = checkNotNull(field) {
            throw Exception("Ripple LruCache has not been initialized!")
        }
        set(value) {
            if (field != null) {
                logD("Ripple LruCache has already been initialized!")
            } else {
                field = value
            }
        }

    var fifoCache: IRippleCache<String, String>? = null
        /*
        如果缓存为空的话则创建缓存实例，否则直接获取
         */
        get() = checkNotNull(field) {
            throw Exception("Ripple FifoCache has not been initialized!")
        }
        set(value) {
            if (field != null) {
                logD("Ripple FifoCache has already been initialized!")
            } else {
                field = value
            }
        }


    fun getLruCache(key: String) {

    }


}


