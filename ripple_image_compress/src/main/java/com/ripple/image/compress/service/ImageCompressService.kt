package com.ripple.image.compress.service

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Environment
import com.ripple.image.compress.model.ImageItem
import com.ripple.task.util.Preconditions
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/5/6 18:13
 * Email: fanyafeng@live.cn
 * Description: 图片压缩服务封装
 */
interface ImageCompressService : Serializable {

    /**
     * 压缩图片
     */
    fun compress(imageItem: ImageItem): File


    /**
     * 判断图片是否是JPG或者JPEG
     * 非JPG和JPEG图片无Exif信息
     */
    fun isJPG(path: String): Boolean {
        return if (path != "") {
            val suffix = path.substringAfterLast('.', "")
            suffix.contains("jpg") || suffix.contains("jpeg")
        } else {
            false
        }
    }

    /**
     * 判断是否是png图片
     */
    fun isPNG(path: String): Boolean {
        return if (path != "") {
            val suffix = path.substringAfterLast('.', "")
            suffix.contains("png")
        } else {
            false
        }
    }

    /**
     * 获取图片旋转角度
     */
    fun getImageSpinAngle(path: String): Int {
        var degree = 0
        if (!isJPG(path)) {
            return degree
        }
        try {
            val exifInterface =
                ExifInterface(path) //仅支持jpg
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            degree = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (e: IOException) {
//            e.printStackTrace()
            return degree
        }
        return degree
    }

    /**
     * 获取图片扩展名
     */
    fun getCompressFormat(path: String): Bitmap.CompressFormat {
        return if (isPNG(path)) {
            Bitmap.CompressFormat.PNG
        } else {
            Bitmap.CompressFormat.JPEG
        }
    }

    /**
     * 旋转图片
     */
    fun rotateImage(angle: Float, bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }

    /**
     * 保存图片
     */
    fun saveImage(
        compressFormat: CompressFormat,
        compressPath: String,
        bitmap: Bitmap,
        quality: Int
    ): File? {
        Preconditions.checkNotNull(bitmap, "bitmap is null")

        val resultPath = compressPath.substring(0, compressPath.lastIndexOf("/"))
        val result =
            File(resultPath)

        val createdDir = if (!result.exists()) {
            getSDCardPath() + compressPath
        } else {
            compressPath
        }

        val compressFile = File(createdDir)

        if (compressFile.isFile && compressFile.exists()) {
            return null
        } else {
            compressFile.createNewFile()
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(compressFormat, quality, byteArrayOutputStream)
        try {
            val fileOutputStream = FileOutputStream(createdDir)
            fileOutputStream.write(byteArrayOutputStream.toByteArray())
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return compressFile
    }

    /**
     * 获取图片宽高
     */
    fun getImageSize(imagePath: String): IntArray {
        val res = IntArray(2)
        val options = BitmapFactory.Options()
        //不分配图片实际内存，只用来获取图片的bitmap信息，避免造成图片过大造成内存溢出
        options.inJustDecodeBounds = true
        options.inSampleSize = 1
        BitmapFactory.decodeFile(imagePath, options)
        res[0] = options.outWidth
        res[1] = options.outHeight
        return res
    }

    /**
     * 获取sd卡路径
     */
    fun getSDCardPath(): String? {
        return Environment.getExternalStorageDirectory()
            .toString()
    }

    /**
     * 创建文件夹
     */
    fun createDir(path: String): String? {
        val file = File(getSDCardPath() + path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath
    }
}


