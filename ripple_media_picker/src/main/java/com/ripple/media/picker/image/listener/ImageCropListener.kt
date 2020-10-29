package com.ripple.media.picker.image.listener

import java.io.File
import java.io.Serializable


/**
 * Author: fanyafeng
 * Date: 2020/10/26 09:59
 * Email: fanyafeng@live.cn
 * Description: 图片裁剪后是否能保存成功回调
 */
interface ImageCropListener : Serializable {
    fun onImageCropSaveResult(file: File?)
}