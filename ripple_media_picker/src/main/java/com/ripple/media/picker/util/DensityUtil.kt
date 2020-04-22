package com.ripple.media.picker.util

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat

/**
 * Author: fanyafeng
 * Data: 2020/4/22 16:13
 * Email: fanyafeng@live.cn
 * Description:
 */
internal object DensityUtil {

    fun dp2px(dpValue: Float): Float {
        val density = Resources.getSystem().displayMetrics.density
        return 0.5F + dpValue * density
    }

    fun px2dp(pxValue: Float): Float {
        val density = Resources.getSystem().displayMetrics.density
        return pxValue / density
    }
}

internal val Float.dp2px: Float
    get() {
        return DensityUtil.dp2px(this)
    }

internal val Float.px2dp: Float
    get() {
        return DensityUtil.px2dp(this)
    }

internal val Int.dp2px: Float
    get() {
        return DensityUtil.dp2px(this.toFloat())
    }

internal val Int.px2dp: Float
    get() {
        return DensityUtil.px2dp(this.toFloat())
    }