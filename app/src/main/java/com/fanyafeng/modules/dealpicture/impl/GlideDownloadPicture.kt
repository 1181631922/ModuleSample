package com.fanyafeng.modules.dealpicture.impl

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.target.Target
import com.fanyafeng.modules.dealpicture.DownloadPicture
import com.ripple.media.picker.camera.PictureGalleryUtil
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: fanyafeng
 * Data: 2020/5/6 14:03
 * Email: fanyafeng@live.cn
 * Description:
 */
class GlideDownloadPicture : DownloadPicture {
    override fun download(
        context: Context,
        sourcePath: String,
        targetPath: String?,
        onResultCallBack: DownloadPicture.OnResultCallBack
    ) {
        var photoFileDir =
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
                File(Environment.getExternalStorageDirectory(), "/DCIM/camera/")
            else
                Environment.getDataDirectory()

        if (targetPath != null) {
            photoFileDir = File(targetPath)
        }
        val takeImageFile = createFile(photoFileDir, "IMG_", ".jpg")

        val target: FutureTarget<Bitmap> = Glide
            .with(context)
            .asBitmap()
            .load(sourcePath)
            .submit()

        var out: FileOutputStream? = null
        try {
            Thread(Runnable {
                val bitmap = target.get()

                out = FileOutputStream(takeImageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out?.flush()
                out?.close()
                PictureGalleryUtil.updatePictureGallery(context, takeImageFile)
                Log.d("下载图片", "图片下载成功")
                onResultCallBack.onSuccess()
                (context as Activity).runOnUiThread {
                    Toast.makeText(
                        context,
                        "图片下载成功",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }).start()
        } catch (e: Exception) {
            onResultCallBack.onError(e.toString())
            Toast.makeText(context, "图片下载失败", Toast.LENGTH_SHORT).show()
        } finally {
            out?.close()
        }


    }
}