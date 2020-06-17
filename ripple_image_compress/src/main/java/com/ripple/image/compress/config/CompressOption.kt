package com.ripple.image.compress.config

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ripple.image.compress.config.impl.HD
import com.ripple.image.compress.config.impl.LD
import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/5/6 11:39
 * Email: fanyafeng@live.cn
 * Description: 图片压缩规则
 */
interface CompressOption : Serializable {

    companion object {
        /**
         * 默认压缩质量为百分之八十
         */
        const val QUALITY_80 = 80

        /**
         * 如果图片大小小于500k不压缩
         */
        const val LARGEST_SIZE = 500 * 1024L

        /**
         * 图片不进行操作标准值
         */
        const val UNDO_SIZE = -1L

        /**
         * 图片不进行操作标准值
         */
        const val UNDO_SIZE_INT = -1

        /**
         * 获取高清压缩
         */
        fun getHD() = HD

        /**
         * 获取普清压缩
         */
        fun getLD() = LD
    }


    /**
     * 通过不同的宽高进行不同规则的压缩
     * [BitmapFactory.Options.inSampleSize]
     * [BitmapFactory.Options.outWidth]
     * [BitmapFactory.Options.outHeight]
     */
    fun inSampleSize(sourceWidth: Int, sourceHeight: Int): Int

    /**
     * 质量压缩
     */
    fun qualityLevel(): Int

    /**
     * Bitmap的Config
     * [BitmapFactory.Options.inPreferredConfig]
     * [Bitmap.Config.RGB_565] 一般是565
     */
    fun inPreferredConfig(): Bitmap.Config

    /**
     * https://blog.csdn.net/zz_mm/article/details/42496651
     * 缩小图片的尺寸
     */
    fun resizeImageBitmap(width: Int, height: Int): Pair<Int, Int>

    /**
     * 压缩图片的最大阈值
     * 如果超过此阈值则进行压缩，否则不进行压缩
     */
    fun largestThreshold(): Long

    /**
     * 压缩后最大的图片大小
     * 压缩后图片不得超过此大小
     */
    fun largestSize(): Long


}