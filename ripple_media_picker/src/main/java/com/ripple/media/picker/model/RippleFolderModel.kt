package com.ripple.media.picker.model

/**
 * Author: fanyafeng
 * Data: 2020/4/17 09:44
 * Email: fanyafeng@live.cn
 * Description:
 */
interface RippleFolderModel : RippleBaseModel {
    fun getName(): String

    fun getMediaList(): MutableList<RippleMediaModel>
}