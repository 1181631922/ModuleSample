package com.ripple.media.picker.config

import android.graphics.Color
import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/4/21 17:35
 * Email: fanyafeng@live.cn
 * Description:
 */
interface MediaThemeConfig : Serializable {
    /**
     * 是否是亮模式
     */
    fun isLight(): Boolean

    /**
     * 设置statusbar颜色
     */
    fun setStatusBarColor(): Int

    /**
     * 设置toolbar颜色
     */
    fun setToolbarColor(): Int

    /**
     * 设置中间标题的颜色
     */
    fun setToolbarCenterTitleColor(): Int

    /**
     * 设置右侧标题颜色
     */
    fun setToolbarRightTitleColor(): Int

    /**
     * 设置返回键
     */
    fun setNavigationIcon(): Int
}