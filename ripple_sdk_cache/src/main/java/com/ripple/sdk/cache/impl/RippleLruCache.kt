package com.ripple.sdk.cache.impl

import android.content.Context
import com.ripple.sdk.cache.RippleCache
import com.ripple.sdk.cache.strategy.ICache
import com.ripple.sdk.cache.IRippleCache
import com.ripple.sdk.cache.strategy.impl.LruCache
import com.ripple.tool.string.isNotNullOrEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject


/**
 * Author: fanyafeng
 * Data: 2020/8/31 15:19
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleLruCache(private val context: Context, private var maxSize: Int) :
    ICache<String, String>, IRippleCache<String, String> {

    companion object {
        private lateinit var lruCache: ICache<String, String>
    }

    init {
        lruCache = LruCache(maxSize)
        val sp = context.getSharedPreferences(RippleCache.CACHE, Context.MODE_PRIVATE)
        val cacheBody = sp.getString(RippleCache.LRU_CACHE_KEY, "")
        if (cacheBody.isNotNullOrEmpty()) {
            val cacheJson = JSONObject(cacheBody)
            cacheJson.keys().forEach {
                lruCache.put(it, cacheJson[it] as String)
            }
        }
    }


    /**
     * 确保保存的数据是有序的
     * 如果数量超出后则最后存入的则是最新的数据
     */
    override fun put(key: String, value: String): String? {
        return lruCache.put(key, value)
    }

    override fun get(key: String): String? {
        return lruCache.get(key)
    }

    override fun remove(key: String): String? {
        return lruCache.remove(key)
    }

    override fun trimToSize(maxSize: Int) {
        lruCache.trimToSize(maxSize)
    }

    override fun evictAll() {
        lruCache.evictAll()
    }

    override fun resize(maxSize: Int) {
        lruCache.resize(maxSize)
    }

    override fun snapshot(): HashMap<String, String> {
        return lruCache.snapshot()
    }

    override fun commit(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val sp = context.getSharedPreferences(RippleCache.CACHE, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString(
                RippleCache.LRU_CACHE_KEY,
                JSONObject(lruCache.snapshot().toMap()).toString()
            )
            launch(Dispatchers.Main) {
                editor.apply()
            }
        }
    }

}