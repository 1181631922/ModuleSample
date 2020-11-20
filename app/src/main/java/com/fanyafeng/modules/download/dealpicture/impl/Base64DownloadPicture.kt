package com.fanyafeng.modules.download.dealpicture.impl

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Base64
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.fanyafeng.modules.download.Download
import com.fanyafeng.modules.download.dealpicture.DownloadPicture
import com.ripple.media.picker.camera.PictureGalleryUtil
import java.io.File
import java.io.FileOutputStream

/**
 * Author: fanyafeng
 * Data: 2020/6/3 17:03
 * Email: fanyafeng@live.cn
 * Description: 兼容base64转图片
 */
class Base64DownloadPicture : DownloadPicture {

    override fun download(
        context: Context,
        sourcePath: String,
        targetPath: String?,
        onResultCallBack: Download.OnResultCallBack
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

        val decodePath = Base64.decode(sourcePath.split(",")[1], Base64.DEFAULT)

        val target: FutureTarget<Bitmap> = Glide
            .with(context)
            .asBitmap()
            .load(decodePath)
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
                onResultCallBack.onSuccess()
            }).start()
        } catch (e: Exception) {
            onResultCallBack.onError(e.toString())
        } finally {
            out?.close()
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