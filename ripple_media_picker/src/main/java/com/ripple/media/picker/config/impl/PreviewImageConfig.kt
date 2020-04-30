package com.ripple.media.picker.config.impl

import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.config.IPreviewImageConfig
import com.ripple.media.picker.model.RippleMediaModel

/**
 * Author: fanyafeng
 * Data: 2020/4/30 10:10
 * Email: fanyafeng@live.cn
 * Description:
 */
class PreviewImageConfig private constructor(builder: Builder) : IPreviewImageConfig {

    private var currentPosition: Int = 0

    private var imageList: List<RippleMediaModel> = mutableListOf()

    init {
        currentPosition = builder.currentPosition
        imageList = builder.imageList
    }

    override fun getCurrentPosition(): Int {
        return currentPosition
    }

    override fun getImageList(): List<RippleMediaModel> {
        return imageList
    }

    class Builder {
        var currentPosition: Int = 0
            private set(value) {
                field = value
            }

        var imageList: List<RippleMediaModel> = mutableListOf()
            private set(value) {
                field = value
            }

        fun setCurrentPosition(currentPosition: Int): Builder {
            this.currentPosition = currentPosition
            return this
        }

        fun setImageList(imageList: List<RippleMediaModel>): Builder {
            this.imageList = imageList
            return this
        }

        fun build(): PreviewImageConfig {
            return PreviewImageConfig(this)
        }
    }

}