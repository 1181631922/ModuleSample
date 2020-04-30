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

    /**
     * 编辑模式
     */
    fun getModel(): PreviewModel

    enum class PreviewModel{
        /**
         * 正常模式，只能进行查看图片
         */
        NORMAL,

        /**
         * 选取图片模式，显示图片被选取顺序等
         */
        SELECT,

        /**
         * 删除图片模式，只可以进行图片的删除
         */
        DELETE,
    }

}