package com.ripple.media.picker.image.listener

import java.io.File
import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/4/30 16:09
 * Email: fanyafeng@live.cn
 * Description:
 */
interface TakePhotoListener : Serializable {
    fun onTakePhotoListener(file: File?)
}