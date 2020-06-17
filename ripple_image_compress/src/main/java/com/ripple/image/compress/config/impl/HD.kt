package com.ripple.image.compress.config.impl

import android.graphics.Bitmap
import com.ripple.image.compress.config.CompressOption
import kotlin.math.ceil

/**
 * Author: fanyafeng
 * Data: 2020/5/6 12:02
 * Email: fanyafeng@live.cn
 * Description: 高清压缩方式
 */
object HD : CompressOption {
    override fun inSampleSize(sourceWidth: Int, sourceHeight: Int): Int {
        var thumbW = sourceWidth
        var thumbH = sourceHeight

        when {
            sourceWidth > 4000 -> {
                thumbW = (sourceWidth * 0.4).toInt()
                thumbH = (sourceHeight * 0.4).toInt()
            }
            sourceWidth > 3000 -> {
                thumbW = sourceWidth shr 1
                thumbH = sourceHeight shr 1
            }
            sourceWidth > 2048 -> {
                thumbW = (sourceWidth * 0.6).toInt()
                thumbH = (sourceHeight * 0.6).toInt()
            }
        }

        var inSampleSize = 1

        if (sourceHeight > thumbH || sourceWidth > thumbW) {
            val halfH = sourceHeight shr 1
            val halfW = sourceWidth shr 1
            while (halfH / inSampleSize > thumbH && halfW / inSampleSize > thumbW) {
                inSampleSize *= 2
            }
        }

        val heightRatio =
            ceil(sourceHeight / thumbH.toFloat()).toInt()
        val widthRatio = ceil(sourceWidth / thumbW.toFloat()).toInt()

        if (heightRatio > 1 || widthRatio > 1) {
            inSampleSize = if (heightRatio > widthRatio) {
                heightRatio
            } else {
                widthRatio
            }
        }

        return inSampleSize
    }

    override fun qualityLevel(): Int {
        return CompressOption.QUALITY_80
    }

    override fun inPreferredConfig(): Bitmap.Config {
        return Bitmap.Config.RGB_565
    }

    override fun resizeImageBitmap(width: Int, height: Int): Pair<Int, Int> {
        return Pair(CompressOption.UNDO_SIZE_INT, CompressOption.UNDO_SIZE_INT)
    }

    override fun largestThreshold(): Long {
        return CompressOption.LARGEST_SIZE
    }

    override fun largestSize(): Long {
        return CompressOption.UNDO_SIZE
    }
}