package com.ripple.media.picker.model

import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/4/17 09:41
 * Email: fanyafeng@live.cn
 * Description: 实体的基类，防止混淆
 */
interface RippleBaseModel : Serializable {
    /**
     * 是否选中
     */
    fun isCheck(): Boolean

    /**
     * 获取当前文件路径
     */
    fun getPath(): String
}