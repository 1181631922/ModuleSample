package com.fanyafeng.modules.download.dealpicture.impl

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.facebook.common.executors.UiThreadImmediateExecutorService
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.RotationOptions
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.fanyafeng.modules.download.Download
import com.fanyafeng.modules.download.dealpicture.DownloadPicture
import com.ripple.media.picker.camera.PictureGalleryUtil
import java.io.File
import java.io.FileOutputStream

/**
 * Author: fanyafeng
 * Data: 2020/5/6 15:06
 * Email: fanyafeng@live.cn
 * Description:
 */
class FrescoDownloadPicture : DownloadPicture {
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

        val uri = Uri.parse(sourcePath)
        val decodeOptions = ImageDecodeOptions.newBuilder().build()
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
            .setImageDecodeOptions(decodeOptions)
            .setRotationOptions(RotationOptions.autoRotate())
            .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
            .build()

        val imagePipeline = Fresco.getImagePipeline()
        val dataSource = imagePipeline.fetchDecodedImage(imageRequest, this)
        dataSource.subscribe(object : BaseBitmapDataSubscriber() {
            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {
            }

            override fun onNewResultImpl(bitmap: Bitmap?) {
                if (bitmap != null) {
                    var out: FileOutputStream? = null
                    try {
                        Thread(Runnable {
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

        }, UiThreadImmediateExecutorService.getInstance())

    }

    override fun download(
        context: Context,
        sourcePath: String,
        onResultCallBack: Download.OnResultCallBack
    ) {
        this.download(context, sourcePath, null, onResultCallBack)
    }
}