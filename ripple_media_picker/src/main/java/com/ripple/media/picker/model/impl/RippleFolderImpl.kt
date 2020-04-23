package com.ripple.media.picker.model.impl

import com.ripple.media.picker.model.RippleFolderModel
import com.ripple.media.picker.model.RippleMediaModel

/**
 * Author: fanyafeng
 * Data: 2020/4/21 10:30
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleFolderImpl : RippleFolderModel {

    private lateinit var name: String

    private lateinit var mediaList: MutableList<RippleMediaModel>

    private var isCheck = false

    private lateinit var path: String

    private var any: Any? = null

    @JvmOverloads
    constructor(
        name: String,
        path: String,
        mediaList: MutableList<RippleMediaModel>,
        isCheck: Boolean = false
    ) {
        this.name = name
        this.mediaList = mediaList
        this.isCheck = isCheck
        this.path = path
    }

    constructor()


    fun setName(name: String) {
        this.name = name
    }

    fun setMediaList(mediaList: MutableList<RippleMediaModel>) {
        this.mediaList = mediaList
    }

    override fun setCheck(isCheck: Boolean) {
        this.isCheck = isCheck
    }

    fun setPath(path: String) {
        this.path = path
    }

    override fun getName(): String {
        return name
    }

    override fun getMediaList(): MutableList<RippleMediaModel> {
        return mediaList
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RippleFolderImpl

        if (path != other.path) return false

        return true
    }

    override fun toString(): String {
        return "RippleFolderImpl(name='$name', mediaList=$mediaList, isCheck=$isCheck, path='$path')"
    }


}