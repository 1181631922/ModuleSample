package com.ripple.image.compress.config.impl

import android.graphics.Bitmap
import com.ripple.image.compress.config.CompressOption
import kotlin.math.ceil

/**
 * Author: fanyafeng
 * Data: 2020/5/6 12:08
 * Email: fanyafeng@live.cn
 * Description: 普清压缩方式
 */
object LD : CompressOption {
    override fun inSampleSize(sourceWidth: Int, sourceHeight: Int): Int {
        val width = if (sourceWidth % 2 == 1) sourceWidth + 1 else sourceWidth
        val height = if (sourceHeight % 2 == 1) sourceHeight + 1 else sourceHeight

        val longSide = width.coerceAtLeast(height)
        val shortSide = width.coerceAtMost(height)

        val scle = shortSide.toFloat() / longSide.toFloat()

        var inSampleSize = 1
        inSampleSize = if (scle <= 1 && scle > 0.5625) {
            if (longSide < 1664) {
                1
            } else if (longSide in 1664..4989) {
                2
            } else if (longSide in 4991..10239) {
                4
            } else {
                if (longSide / 200 == 0) 1 else longSide / 1280
            }
        } else if (scle <= 0.5625 && scle > 0.5) {
            if (longSide / 1280 == 0) 1 else longSide / 1280
        } else {
            ceil(longSide / (1280.0 / scle)).toInt()
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