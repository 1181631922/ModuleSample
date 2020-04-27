package com.ripple.media.picker.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.image.RippleImagePick
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * Author: fanyafeng
 * Data: 2020/4/26 17:37
 * Email: fanyafeng@live.cn
 * Description: 使用系统相机拍照
 */
class TakePicture @JvmOverloads constructor(private val config: IImagePickConfig = RippleMediaPick.getInstance().imagePickConfig) {

    fun openCamera(context: Context, requestCode: Int = IImagePickConfig.TAKE_PICTURE_CODE) {
        val photoFile = config.getPhotoFile()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        if (intent.resolveActivity(context.packageManager) != null) {
            val takeImageFile = createFile(photoFile, "IMG_", ".jpg")
            var uri: Uri? = null
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                uri = Uri.fromFile(takeImageFile)
            } else {
                try {
                    uri = FileProvider.getUriForFile(
                        context,
                        context.packageName.toString() + ".provider",
                        takeImageFile
                    )
                    val resInfoList =
                        context.packageManager.queryIntentActivities(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        )
                    for (resolveInfo in resInfoList) {
                        val packageName = resolveInfo.activityInfo.packageName
                        context.grantUriPermission(
                            packageName,
                            uri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            RippleImagePick.getInstance().takePictureFile = takeImageFile
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        }
        (context as Activity).startActivityForResult(intent, requestCode)
    }

    /**
     * 创建文件
     */
    private fun createFile(
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