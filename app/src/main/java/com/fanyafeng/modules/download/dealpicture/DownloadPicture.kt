package com.fanyafeng.modules.download.dealpicture

import android.content.Context
import com.fanyafeng.modules.download.Download
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
interface DownloadPicture : Download {

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