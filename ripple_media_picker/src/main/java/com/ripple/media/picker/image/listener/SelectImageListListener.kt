package com.ripple.media.picker.image.listener

import com.ripple.media.picker.model.RippleMediaModel

/**
 * Author: fanyafeng
 * Data: 2020/4/23 10:50
 * Email: fanyafeng@live.cn
 * Description:
 */
interface SelectImageListListener {
    fun selectImageList(imageList: List<RippleMediaModel>)
}