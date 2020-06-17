package com.fanyafeng.modules.dealpicture

import android.content.Context
import java.io.File
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: fanyafeng
 * Data: 2020/5/6 14:01
 * Email: fanyafeng@live.cn
 * Description:
 */
interface DownloadPicture : Serializable {
    fun download(
        context: Context,
        sourcePath: String,
        targetPath: String?,
        onResultCallBack: OnResultCallBack
    )

    interface OnResultCallBack {
        fun onError(message: String)

        fun onSuccess()
    }

    interface SimpleResultCallBack : OnResultCallBack {
        override fun onSuccess() {
        }

        override fun onError(message: String) {
        }
    }

    /**
     * 创建文件
     */
    fun createFile(
        folder: File,
        prefix: String,
        suffix: String
    ): File {
        if (!folder.exists() || !folder.isDirectory) folder.mkdirs()
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
        val filename =
            prefix + dateFormat.format(Date(System.currentTimeMillis())).toString() + suffix
        return File(folder, filename)
    }
}