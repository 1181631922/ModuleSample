package com.ripple.media.picker.model

/**
 * Author: fanyafeng
 * Data: 2020/5/8 18:29
 * Email: fanyafeng@live.cn
 * Description:
 */
interface SimpleImageModel : RippleMediaModel {
    override fun getParentPath(): String {
        return ""
    }

    override fun getDateModified(): Long {
        return -1L
    }

    override fun getDateAdded(): Long {
        return -1L
    }

    override fun getSize(): Long {
        return -1L
    }

    override fun getTitle(): String {
        return ""
    }

    override fun getDisplayName(): String {
        return ""
    }

    override fun getHeight(): Int {
        return 0
    }

    override fun getWidth(): Int {
        return 0
    }

    override fun getDuration(): Long {
        return -1L
    }

    override fun getType(): String {
        return ""
    }

    override fun isCheck(): Boolean {
        return false
    }

    override fun setCheck(check: Boolean) {
    }

    override fun getTag(): Any? {
        return null
    }

    override fun setTag(tag: Any?) {
    }

    override fun compareTo(other: RippleMediaModel): Int {
        return 0
    }
}