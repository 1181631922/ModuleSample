package com.fanyafeng.modules.download.dealfile.impl

import android.app.Activity
import android.content.Context
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestOptions
import com.fanyafeng.modules.download.Download
import com.fanyafeng.modules.download.dealfile.DownloadFile
import java.io.*

/**
 * Author: fanyafeng
 * Date: 2020/11/17 18:33
 * Email: fanyafeng@live.cn
 * Description:
 */
class GlideDownloadFile : DownloadFile {
    override fun download(
        context: Context,
        sourcePath: String,
        targetPath: String?,
        onResultCallBack: Download.OnResultCallBack
    ) {
        var fileDir =
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
                File(Environment.getExternalStorageDirectory(), "/DCIM/camera/")
            else
                Environment.getDataDirectory()

        if (targetPath != null) {
            fileDir = File(targetPath)
        }
        val sourceFile = createFile(fileDir, "FILE_", sourcePath.split("/").last())

        val requestOptions = RequestOptions().timeout(2000)
        val target: FutureTarget<File> = Glide
            .with(context)
            .asFile()
            .load(sourcePath)
            .apply(requestOptions)
            .submit()

        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            Thread(Runnable {
                val targetFile = target.get()
                inputStream = FileInputStream(targetFile)
                outputStream = FileOutputStream(sourceFile)

                val buffer = ByteArray(1024)
                var len: Int

                while (inputStream!!.read(buffer).also { len = it } > 0) {
                    outputStream!!.write(buffer, 0, len)
                }
                (context as Activity).runOnUiThread {
                    onResultCallBack.onSuccess()
                }
            }).start()
        } catch (e: Exception) {
//            e.printStackTrace()
            onResultCallBack.onError(e.message ?: "文件下载失败")
        } finally {
            inputStream?.close()
            outputStream?.close()
        }


    }

    override fun download(
        context: Context,
        sourcePath: String,
        onResultCallBack: Download.OnResultCallBack
    ) {
        this.download(context, sourcePath, null, onResultCallBack)
    }

}