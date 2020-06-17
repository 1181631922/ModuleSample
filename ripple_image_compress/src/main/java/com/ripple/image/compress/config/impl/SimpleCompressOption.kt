package com.ripple.image.compress.config.impl

import android.graphics.Bitmap
import com.ripple.image.compress.config.CompressOption
import kotlin.math.ceil

/**
 * Author: fanyafeng
 * Data: 2020/5/6 16:41
 * Email: fanyafeng@live.cn
 * Description: 压缩规格的简单实现封装
 */
open class SimpleCompressOption @JvmOverloads constructor(
    private val qualityLevel: Int = 100,
    private val inPreferredConfig: Bitmap.Config = Bitmap.Config.ARGB_8888,
    private val largestThreshold: Long = CompressOption.UNDO_SIZE,
    private val largestSize: Long = CompressOption.UNDO_SIZE
) : CompressOption {


    companion object {
        /**
         * for example
         */
        val PHOTO_LD = object : SimpleCompressOption(
            100,
            Bitmap.Config.RGB_565,
            CompressOption.UNDO_SIZE,
            CompressOption.UNDO_SIZE
        ) {
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

            override fun resizeImageBitmap(width: Int, height: Int): Pair<Int, Int> {
                return Pair((width * 0.9).toInt(), (height * 0.9).toInt())
            }
        }
    }

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
        return qualityLevel
    }

    override fun inPreferredConfig(): Bitmap.Config {
        return inPreferredConfig
    }

    override fun resizeImageBitmap(width: Int, height: Int): Pair<Int, Int> {
        return Pair(CompressOption.UNDO_SIZE_INT, CompressOption.UNDO_SIZE_INT)
    }

    override fun largestThreshold(): Long {
        return largestThreshold
    }

    override fun largestSize(): Long {
        return largestSize
    }

}