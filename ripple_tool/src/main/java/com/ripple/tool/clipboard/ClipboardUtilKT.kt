package com.ripple.tool.clipboard

import android.content.ClipData
import android.content.Context

/**
 * Author: fanyafeng
 * Data: 2020/6/19 17:05
 * Email: fanyafeng@live.cn
 * Description:
 */

/**
 * 将数据存储到剪切板
 */
fun Context?.copy2Clipboard(clipData: ClipData) = ClipboardUtil.copy2Clipboard(this, clipData)

/**
 * 从剪切板获取数据
 */
fun Context?.getFromClipboard() = ClipboardUtil.getFromClipboard(this)