package com.ripple.media.picker.config.impl

import android.os.Environment
import com.ripple.media.picker.config.CropImageConfig
import com.ripple.media.picker.view.CropImageView
import java.io.File


/**
 * Author: fanyafeng
 * Date: 2020/10/21 14:44
 * Email: fanyafeng@live.cn
 * Description: 裁剪配置的实现类
 */

class CropImageConfigImpl @JvmOverloads constructor(
    private val cropImageStyle: CropImageView.Style = CropImageView.Style.RECTANGLE,
    private val inWidth: Int = IN_WIDTH, private val inHeight: Int = IN_HEIGHT,
    private val outWidth: Int = OUT_WIDTH, private val outHeight: Int = OUT_HEIGHT,
    private val cropCacheFolder: File = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        File(Environment.getExternalStorageDirectory(), "/DCIM/camera/")
    } else {
        Environment.getDataDirectory()
    }
) :
    CropImageConfig {

    companion object {
        /**
         * 默认不进行边框处理
         * 取屏幕宽高最小值
         * 默认为方形
         */
        private const val UNDO_SIZE = -1
        const val OUT_WIDTH = UNDO_SIZE
        const val OUT_HEIGHT = UNDO_SIZE
        const val IN_WIDTH = UNDO_SIZE
        const val IN_HEIGHT = UNDO_SIZE
    }

    override fun getCropImageStyle(): CropImageView.Style {
        return cropImageStyle
    }

    override fun getOutWidth(): Int {
        return outWidth
    }

    override fun getOutHeight(): Int {
        return outHeight
    }

    override fun getInWidth(): Int {
        return inWidth
    }

    override fun getInHeight(): Int {
        return inHeight
    }

    override fun getCropCacheFolder(): File? {
        return cropCacheFolder
    }
}