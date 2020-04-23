package com.ripple.media.picker.model.impl

import com.ripple.media.picker.model.RippleImageModel
import com.ripple.media.picker.model.RippleMediaModel

/**
 * Author: fanyafeng
 * Data: 2020/4/21 09:30
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleImageImpl : RippleImageModel {

    private lateinit var parentPath: String

    private lateinit var path: String

    private var dateModified: Long = 0L

    private var dateAdded: Long = 0L

    private var size: Long = 0L

    private lateinit var title: String

    private lateinit var displayName: String

    private var height: Int = 0

    private var width: Int = 0

    private var duration: Long = 0L

    private lateinit var type: String

    private var isCheck = false

    private var any: Any? = null

    @JvmOverloads
    constructor(
        parentPath: String,
        path: String,
        dateModified: Long,
        dateAdded: Long,
        size: Long,
        title: String,
        displayName: String,
        height: Int,
        width: Int,
        type: String,
        duration: Long = 0L,//因为需要API19图片才会有，所以放在最后了
        isCheck: Boolean = false
    ) {
        this.parentPath = parentPath
        this.path = path
        this.dateModified = dateModified
        this.dateAdded = dateAdded
        this.size = size
        this.title = title
        this.displayName = displayName
        this.height = height
        this.width = width
        this.duration = duration
        this.type = type
        this.isCheck = isCheck
    }


    fun setParentPath(parentPath: String) {
        this.parentPath = parentPath
    }

    fun setPath(path: String) {
        this.path = path
    }

    fun setDateModified(dateModified: Long) {
        this.dateModified = dateModified
    }

    fun setDateAdded(dateAdded: Long) {
        this.dateAdded = dateAdded
    }

    fun setSize(size: Long) {
        this.size = size
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDisplayName(displayName: String) {
        this.displayName = displayName
    }

    fun setHeight(height: Int) {
        this.height = height
    }

    fun setWidth(width: Int) {
        this.width = width
    }

    fun setDuration(duration: Long) {
        this.duration = duration
    }

    fun setType(type: String) {
        this.type = type
    }

    override fun setCheck(isCheck: Boolean) {
        this.isCheck = isCheck
    }


    override fun getParentPath(): String {
        return parentPath
    }

    override fun getDateModified(): Long {
        return dateModified
    }

    override fun getDateAdded(): Long {
        return dateAdded
    }

    override fun getSize(): Long {
        return size
    }

    override fun getTitle(): String {
        return title
    }

    override fun getDisplayName(): String {
        return displayName
    }

    override fun getHeight(): Int {
        return height
    }

    override fun getWidth(): Int {
        return width
    }

    override fun getDuration(): Long {
        return duration
    }

    override fun getType(): String {
        return type
    }

    override fun isCheck(): Boolean {
        return isCheck
    }

    override fun getPath(): String {
        return path
    }

    override fun getTag(): Any? {
        return any
    }

    override fun setTag(tag: Any?) {
        this.any = tag
    }

    override fun toString(): String {
        return "RippleImageImpl(parentPath='$parentPath', path='$path', dateModified=$dateModified, dateAdded=$dateAdded, size=$size, title='$title', displayName='$displayName', height=$height, width=$width, duration=$duration, type='$type', isCheck=$isCheck)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RippleImageImpl

        if (path != other.path) return false

        return true
    }

    /**
     * 暂定按照修改时间进行排序
     */
    override fun compareTo(other: RippleMediaModel): Int {
        return if (this.dateModified > other.getDateModified()) {
            -1
        } else {
            1
        }
    }

}