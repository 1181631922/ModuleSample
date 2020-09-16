package com.ripple.sdk.cache.strategy.impl

import com.ripple.sdk.cache.strategy.ICache
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * Author: fanyafeng
 * Data: 2020/8/28 11:08
 * Email: fanyafeng@live.cn
 * Description:
 *
 * 最近最久未使用
 * LRU算法实现
 */
class LruCache<K, V>(private var maxSize: Int) :
    ICache<K, V> {

    private val map: LinkedHashMap<K, V>
    private var hitCount = 0
    private var missCount = 0
    private var createCount = 0
    private var size = 0
    private var putCount = 0
    private var evictionCount = 0

    init {
        if (maxSize <= 0) {
            throw IllegalArgumentException("maxsize <= 0")
        }
        map = LinkedHashMap(0, 0.75F, true)
    }

    override fun resize(maxSize: Int) {
        if (maxSize <= 0) {
            throw IllegalArgumentException("maxsize <= 0")
        }
        synchronized(this) {
            this.maxSize = maxSize;
        }
        trimToSize(maxSize)
    }

    override fun put(key: K, value: V): V? {
        if (key == null || value == null) {
            throw NullPointerException("key == null || value == null")
        }
        val previous: V?
        synchronized(this) {
            putCount++
            size += safeSizeOf(key, value)
            previous = map.put(key, value)
            if (previous != null) {
                size -= safeSizeOf(key, value)
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, value)
        }
        trimToSize(maxSize)
        return previous
    }

    override fun get(key: K): V? {
        if (key == null) {
            throw NullPointerException("key == null")
        }
        var mapValue: V?
        synchronized(this) {
            /*
            当调用get方法时，如果不为空，则将此元素进行尾插，也就是放在头部
             */
            mapValue = map[key]
            if (mapValue != null) {
                hitCount++
                return mapValue!!
            }
            missCount++
        }
        val createdValue = create() ?: return null
        synchronized(this) {
            createCount++
            mapValue = map.put(key, createdValue)
            if (mapValue != null) {
                map.put(key, mapValue!!)
            } else {
                size += safeSizeOf(key, createdValue)
            }
        }
        return if (mapValue != null) {
            entryRemoved(false, key, createdValue, mapValue!!)
            mapValue
        } else {
            trimToSize(maxSize)
            createdValue
        }
    }

    override fun remove(key: K): V? {
        if (key == null) {
            throw NullPointerException("key == null")
        }
        val previous: V?
        synchronized(this) {
            previous = map.remove(key)
            if (previous != null) {
                size -= safeSizeOf(key, previous)
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, null)
        }
        return previous
    }


    override fun trimToSize(maxSize: Int) {
        while (true) {
            val key: K
            val value: V
            synchronized(this) {
                if (size < 0 || map.isEmpty() && size != 0) {
                    throw IllegalStateException(
                        javaClass.name
                                + ".sizeOf() is reporting inconsistent results!"
                    )
                }
                if (size <= maxSize || map.isEmpty()) {
                    return
                }

                /*
                1.8之前是头插，之后是尾插，所以每次删除的都是头，不是尾
                换句话说也就是头部是最少使用
                 */
                val toEvict = map.entries.iterator().next()
                key = toEvict.key
                value = toEvict.value
                map.remove(key)
                size -= safeSizeOf(key, value)
                evictionCount++
            }
            entryRemoved(true, key, value, null)
        }
    }


    override fun evictAll() {
        trimToSize(-1)
    }

    open fun entryRemoved(evicted: Boolean, key: K, oldValue: V, newValue: V?) {

    }

    open fun create(): V? {
        return null
    }

    open fun sizeOf(key: K, value: V) = 1

    private fun safeSizeOf(key: K, value: V): Int {
        val result = sizeOf(key, value)
        if (result < 0) {
            throw IllegalStateException("Negative size: $key=$value")
        }
        return result
    }

    @Synchronized
    fun maxSize() = maxSize

    @Synchronized
    fun size() = size

    @Synchronized
    fun hitCount() = hitCount

    @Synchronized
    fun missCount() = missCount

    @Synchronized
    fun createCount() = createCount

    @Synchronized
    fun putCount() = putCount

    @Synchronized
    fun evictionCount() = evictionCount

    @Synchronized
    override fun snapshot() = LinkedHashMap(map)

    @Synchronized
    override fun toString(): String {
        val accesses = hitCount + missCount
        val hitPercent = if (accesses != 0) 100 * hitCount / accesses else 0
        return String.format(
            Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]",
            maxSize, hitCount, missCount, hitPercent
        )
    }
}
