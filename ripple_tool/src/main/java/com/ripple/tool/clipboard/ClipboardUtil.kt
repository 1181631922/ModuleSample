package com.ripple.tool.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Author: fanyafeng
 * Data: 2020/6/19 16:44
 * Email: fanyafeng@live.cn
 * Description: 剪切板工具类
 */
object ClipboardUtil {

    /**
     * 将数据拷贝到剪切板
     */
    fun copy2Clipboard(context: Context?, clipData: ClipData) {
        context?.let {
            val clipboardManager =
                it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager?.let { manager ->
                manager.setPrimaryClip(clipData)
            }
        }
    }


    /**
     * 从剪切板获取数据
     */
    fun getFromClipboard(context: Context?): ClipData? {
        context?.let {
            val clipboardManager =
                it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager?.let { manager ->
                return manager.primaryClip
            }
        }
        return null
    }

}