package com.ripple.tool.screen

import android.content.Context
import com.ripple.tool.density.DensityUtil

/**
 * Author: fanyafeng
 * Data: 2020/6/19 15:54
 * Email: fanyafeng@live.cn
 * Description:
 */

/**
 * 获取android状态栏高度
 */
fun Context?.getStatusBarHeight() = ScreenUtil.getStatusBarHeight(this)

/**
 * 获取屏幕尺寸信息
 */
fun Context?.getScreenSize() = ScreenUtil.getScreenSize(this)

/**
 * 获取屏幕宽度
 */
fun Context?.getScreenWidth() = ScreenUtil.getScreenWidth(this)

/**
 * 获取屏幕高度
 */
fun Context?.getScreenHeight() = ScreenUtil.getScreenHeight(this)

/**
 * 获取屏幕真实高度
 */
fun Context?.getRealScreenHeight() = ScreenUtil.getRealScreenHeight(this)