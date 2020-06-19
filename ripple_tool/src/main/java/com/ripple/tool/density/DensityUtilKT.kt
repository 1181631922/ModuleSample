package com.ripple.tool.density

import android.content.Context

/**
 * Author: fanyafeng
 * Data: 2020/6/19 15:01
 * Email: fanyafeng@live.cn
 * Description: density kotlin扩展工具类
 */

/**
 * dp转px
 */
val Float.dp2pxF: Float
    get() {
        return DensityUtil.dp2px(this)
    }

val Float.px2dpF: Float
    get() {
        return DensityUtil.px2dp(this)
    }

val Float.dp2px: Int
    get() {
        return DensityUtil.dp2px(this).toInt()
    }

val Float.px2dp: Int
    get() {
        return DensityUtil.px2dp(this).toInt()
    }

val Int.dp2pxF: Float
    get() {
        return DensityUtil.dp2px(this.toFloat())
    }

val Int.px2dpF: Float
    get() {
        return DensityUtil.px2dp(this.toFloat())
    }

val Int.dp2px: Int
    get() {
        return DensityUtil.dp2px(this.toFloat()).toInt()
    }

val Int.px2dp: Int
    get() {
        return DensityUtil.px2dp(this.toFloat()).toInt()
    }


/**
 * 获取屏幕密度
 */
fun Context?.getDensity(): Float {
    return DensityUtil.getDensity(this)
}

/**
 * 获取屏幕密度Dpi
 */
fun Context?.getDensityDpi(): Int {
    return DensityUtil.getDensityDpi(this)
}