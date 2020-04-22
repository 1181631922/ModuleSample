package com.ripple.media.picker.model

/**
 * Author: fanyafeng
 * Data: 2020/4/21 09:22
 * Email: fanyafeng@live.cn
 * Description:
 */
interface RippleMediaModel : RippleBaseModel,Comparable<RippleMediaModel> {
    /**
     * 获取父路径
     */
    fun getParentPath(): String

    /**
     * 获取文件最后修改时间
     */
    fun getDateModified(): Long

    /**
     * 获取文件添加时间
     */
    fun getDateAdded(): Long

    /**
     * 获取文件大小
     */
    fun getSize(): Long

    /**
     * 获取文件标题
     */
    fun getTitle(): String

    /**
     * 获取文件名
     */
    fun getDisplayName(): String

    /**
     * 获取文件高度
     */
    fun getHeight(): Int

    /**
     * 获取文件宽度
     */
    fun getWidth(): Int

    /**
     * 获取时长（图片API需要大于19）
     */
    fun getDuration(): Long

    /**
     * 获取文件类型
     */
    fun getType(): String

}