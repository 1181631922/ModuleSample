package com.ripple.media.picker.image.listener

import com.ripple.media.picker.model.RippleMediaModel
import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/4/23 10:50
 * Email: fanyafeng@live.cn
 * Description:
 */
interface SelectImageListListener : Serializable {
    fun selectImageList(imageList: List<RippleMediaModel>)
}