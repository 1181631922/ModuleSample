package com.ripple.tool.screen

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager

/**
 * Author: fanyafeng
 * Data: 2020/6/19 15:30
 * Email: fanyafeng@live.cn
 * Description: 屏幕尺寸工具类，系统方法获得
 * 如果屏幕进行旋转那么宽高就会反转
 * 方法不会因为上下文为空就会崩溃，但是获取不到正确的信息
 * 并且会以Log Error级别输出log
 */
object ScreenUtil {

    private val TAG = ScreenUtil::class.java.simpleName
    private const val ERROR_MESSAGE = "请检查context是否为空"

    /**
     * 获取屏幕尺寸宽高
     *
     * @param context 上下文环境
     */
    fun getScreenSize(context: Context?): String {
        context?.let {
            val displayMetrics = it.resources.displayMetrics
            return displayMetrics.heightPixels.toString() + "*" + displayMetrics.widthPixels.toString()
        }
        Log.e(TAG, ERROR_MESSAGE)
        return ERROR_MESSAGE
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文环境
     */
    fun getScreenWidth(context: Context?): Int {
        context?.let {
            val displayMetrics = it.resources.displayMetrics
            return displayMetrics.widthPixels
        }
        Log.e(TAG, ERROR_MESSAGE)
        return 0
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context?): Int {
        context?.let {
            val displayMetrics = it.resources.displayMetrics
            return displayMetrics.heightPixels
        }
        Log.e(TAG, ERROR_MESSAGE)
        return 0
    }

    /**
     * 针对有虚拟按键的手机
     * 获取屏幕高度
     */
    fun getRealScreenHeight(context: Context?): Int {
        context?.let {
            val windowManger = it.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            windowManger.defaultDisplay.getRealMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }
        Log.e(TAG, ERROR_MESSAGE)
        return 0
    }

    /**
     * 获取顶部状态栏高度
     */
    @JvmStatic
    fun getStatusBarHeight(context: Context?): Int {
        var height = 0
        context?.let {
            val resourceId = it.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                height = it.resources.getDimensionPixelSize(resourceId)
            }
        }
        return height
    }
}