package com.ripple.media.picker.loadframe

import android.content.Context
import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/4/21 17:33
 * Email: fanyafeng@live.cn
 * Description:
 */
interface MediaLoadFrame : Serializable {
    fun clearMemoryCache(context: Context)
}