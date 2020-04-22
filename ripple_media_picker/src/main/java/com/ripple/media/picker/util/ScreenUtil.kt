package com.ripple.media.picker.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

/**
 * Author: fanyafeng
 * Data: 2020/4/22 13:53
 * Email: fanyafeng@live.cn
 * Description:
 */
internal object ScreenUtil {

    /**
     * 非刘海屏
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val outMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 非刘海屏
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        val outMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /**
     * 刘海屏
     * 获取屏幕宽度
     */
    fun getRealScreenWidth(context: Context): Int {
        val outMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getRealMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 刘海屏
     * 获取屏幕高度
     */
    fun getRealScreenHeight(context: Context): Int {
        val outMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getRealMetrics(outMetrics)
        return outMetrics.heightPixels
    }
}

internal fun Activity.screenwidth() = ScreenUtil.getScreenWidth(this)

internal fun Activity.screenHeight() = ScreenUtil.getScreenHeight(this)

internal fun Activity.realScreenWidth() = ScreenUtil.getRealScreenWidth(this)

internal fun Activity.realScreenHeight() = ScreenUtil.getRealScreenHeight(this)