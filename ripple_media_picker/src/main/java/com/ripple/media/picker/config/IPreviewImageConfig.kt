package com.ripple.media.picker.config

import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.model.RippleMediaModel
import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/4/30 10:03
 * Email: fanyafeng@live.cn
 * Description:
 */
interface IPreviewImageConfig : Serializable {

    companion object {
        const val PREVIEW_RESULT = 1003
        const val PREVIEW_IMAGE_CONFIG = "preview_image_config"
    }

    /**
     * 当前选中图片的位置
     */
    fun getCurrentPosition(): Int

    /**
     * 图片列表
     */
    fun getImageList(): List<RippleMediaModel>
}