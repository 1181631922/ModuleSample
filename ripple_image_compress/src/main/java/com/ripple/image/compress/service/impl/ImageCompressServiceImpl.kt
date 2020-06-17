package com.ripple.image.compress.service.impl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.ripple.image.compress.config.CompressOption
import com.ripple.image.compress.model.ImageItem
import com.ripple.image.compress.service.ImageCompressService
import com.ripple.task.util.Preconditions
import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/5/6 18:41
 * Email: fanyafeng@live.cn
 * Description: 压缩图片的实现，同步压缩
 */
class ImageCompressServiceImpl : ImageCompressService {

    override fun compress(imageItem: ImageItem): File {
        val sourcePath = Preconditions.checkNotNull(imageItem.getSourcePath())
        val compressConfig = Preconditions.checkNotNull(imageItem.getCompressConfig())
        val targetDir = compressConfig.getTargetDir()
        val targetPath = compressConfig.getTargetPath()
        val compressOption = compressConfig.getCompressOption()
        val inPreferredConfig = compressOption.inPreferredConfig()
        val largestSize = compressOption.largestSize()
        val qualityLevel = compressOption.qualityLevel()
        val largestThreshold = compressOption.largestThreshold()


        val angle = getImageSpinAngle(sourcePath)

        val sourceFile = File(sourcePath)

        val fileLength = sourceFile.length()
        if (largestThreshold == CompressOption.UNDO_SIZE || fileLength <= largestThreshold) {
            return sourceFile
        }

        val width = getImageSize(sourcePath)[0]
        val height = getImageSize(sourcePath)[1]

        val options = BitmapFactory.Options();

        options.inSampleSize = compressOption.inSampleSize(width, height)
        options.inPreferredConfig = inPreferredConfig

        options.inJustDecodeBounds = false
        var bitmap = BitmapFactory.decodeFile(sourcePath, options)

        bitmap = rotateImage(angle.toFloat(), bitmap)
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height
        val compressPair = compressOption.resizeImageBitmap(bitmapWidth, bitmapHeight)
        val newWidth = compressPair.first
        val newHeight = compressPair.second
        if (newWidth != CompressOption.UNDO_SIZE_INT || newHeight != CompressOption.UNDO_SIZE_INT) {
            if (newWidth > 0 && newHeight > 0) {
                val scaleWidth = newWidth.toFloat() / width.toFloat()
                val scaleHeight = newHeight.toFloat() / height.toFloat()
                val matrix = Matrix()
                matrix.postScale(scaleWidth, scaleHeight)
                bitmap =
                    Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true)
            }
        }

        val targetFilePath = File(targetDir, targetPath).absolutePath

        val targetFile = saveImage(Bitmap.CompressFormat.JPEG, targetFilePath, bitmap, qualityLevel)
            ?: return File("")

        if (targetFile.length() >= sourceFile.length()) {
            return sourceFile
        }

        return targetFile
    }

}