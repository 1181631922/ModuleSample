package com.ripple.media.picker.view.photoview.scrollerproxy

import android.content.Context
import android.os.Build

/**
 * Author: fanyafeng
 * Data: 2020/4/29 10:11
 * Email: fanyafeng@live.cn
 * Description:
 */
abstract class ScrollerProxy {
    companion object {
        fun getScrller(context: Context): ScrollerProxy {
            return when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD -> {
                    PreGingerScroller(context);
                }
                Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH -> {
                    GingerScroller(context);
                }
                else -> {
                    IcsScroller(context);
                }
            }
        }
    }

    /**
     * 计算获取新位置
     */
    abstract fun computeScrollOffset(): Boolean

    /**
     * fling整个过程
     */
    abstract fun fling(
        startX: Int,
        startY: Int,
        velocityX: Int,
        velocityY: Int,
        minX: Int,
        maxX: Int,
        minY: Int,
        maxY: Int,
        overX: Int,
        overY: Int
    )

    /**
     * 是否强制结束
     */
    abstract fun forceFinished(finish: Boolean)

    abstract fun isFinished(): Boolean

    /**
     * 获取当前x
     */
    abstract fun getCurrX(): Int

    /**
     * 获取当前y
     */
    abstract fun getCurrY(): Int


}