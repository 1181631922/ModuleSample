package com.fanyafeng.modules.download.dealfile

import com.fanyafeng.modules.download.Download
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * Author: fanyafeng
 * Date: 2020/11/17 18:30
 * Email: fanyafeng@live.cn
 * Description:
 */
interface DownloadFile : Download {

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

    fun createFile(folder: File, fileName: String): File {
        if (!folder.exists() || !folder.isDirectory) folder.mkdirs()
        return File(folder, fileName)
    }
}