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

    private lateinit var mediaList: List<RippleMediaModel>

    private var isCheck = false

    private lateinit var path: String

    @JvmOverloads
    constructor(
        name: String,
        mediaList: List<RippleMediaModel>,
        path: String,
        isCheck: Boolean = false
    ) {
        this.name = name
        this.mediaList = mediaList
        this.isCheck = isCheck
        this.path = path
    }


    fun setName(name: String) {
        this.name = name
    }

    fun setMediaList(mediaList: List<RippleMediaModel>) {
        this.mediaList = mediaList
    }

    fun setCheck(isCheck: Boolean) {
        this.isCheck = isCheck
    }

    fun setPath(path: String) {
        this.path = path
    }

    override fun getName(): String {
        return name
    }

    override fun getMediaList(): List<RippleMediaModel> {
        return mediaList
    }

    override fun isCheck(): Boolean {
        return isCheck
    }

    override fun getPath(): String {
        return path
    }

}