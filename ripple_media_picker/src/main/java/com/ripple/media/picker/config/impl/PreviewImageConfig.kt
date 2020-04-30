package com.ripple.media.picker.config.impl

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

    private var selectModel = IPreviewImageConfig.PreviewModel.SELECT

    init {
        currentPosition = builder.currentPosition
        imageList = builder.imageList
        selectModel = builder.selectModel
    }

    override fun getCurrentPosition(): Int {
        return currentPosition
    }

    override fun getImageList(): List<RippleMediaModel> {
        return imageList
    }

    override fun getModel(): IPreviewImageConfig.PreviewModel {
        return selectModel
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

        var selectModel = IPreviewImageConfig.PreviewModel.SELECT
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

        fun setSelectModel(selectModel: IPreviewImageConfig.PreviewModel): Builder {
            this.selectModel = selectModel
            return this
        }

        fun build(): PreviewImageConfig {
            return PreviewImageConfig(this)
        }
    }

}