package com.ripple.media.picker.image.listener

import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/4/30 16:09
 * Email: fanyafeng@live.cn
 * Description:
 */
interface TakePhotoListener {
    fun onTakePhotoListener(file: File?)
}