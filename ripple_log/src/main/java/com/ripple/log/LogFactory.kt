package com.ripple.log

import android.util.Log
import kotlin.reflect.KClass


/**
 * Author: fanyafeng
 * Data: 2020/7/6 13:34
 * Email: fanyafeng@live.cn
 * Description:
 */
object LogFactory {
    @JvmOverloads
    fun d(msg: String?, tag: String? = "TAG") {
        Log.d(tag, msg)
    }

    fun d(tag: Class<*>, msg: String?) {
        d(tag.simpleName, msg)
    }

}