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
     * 设置选中
     */
    fun setCheck(check: Boolean)

    /**
     * 获取当前文件路径
     */
    fun getPath(): String

    /**
     * 为了兼容未考虑到的情况，添加TAG
     */
    fun getTag(): Any?

    fun setTag(tag: Any?)
}