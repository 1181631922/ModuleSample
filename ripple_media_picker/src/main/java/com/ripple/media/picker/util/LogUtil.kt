package com.ripple.media.picker.util

import android.util.Log

/**
 * Author: fanyafeng
 * Data: 2020/4/21 17:02
 * Email: fanyafeng@live.cn
 * Description:
 */
internal object LogUtil {

    private const val isDebug = true

    @JvmOverloads
    @JvmStatic
    fun d(tag: String = "media picker:", msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }


}