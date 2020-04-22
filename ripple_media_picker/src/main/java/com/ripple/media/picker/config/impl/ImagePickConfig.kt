package com.ripple.media.picker.config.impl

import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.config.MediaPickConfig

/**
 * Author: fanyafeng
 * Data: 2020/4/21 17:38
 * Email: fanyafeng@live.cn
 * Description:
 */
class ImagePickConfig private constructor(builder: Builder) : IImagePickConfig {

    /**
     * 图片选取最大数量
     */
    private var count: Int = 9

    /**
     * 图片大小限制，-1不限制
     */
    private var size: Long = -1L

    /**
     * 默认多选，单选会带裁剪，暂时不支持
     */
    private var chooseType = MediaPickConfig.ChooseType.MULTIPLE_CHOOSE_TYPE

    init {
        count = builder.count
        size = builder.size
        chooseType = builder.chooseType
    }

    override fun getCount(): Int {
        return count
    }

    override fun getSize(): Long {
        return size
    }

    override fun getChooseType(): MediaPickConfig.ChooseType {
        return chooseType
    }

    class Builder {
        var count: Int = 9
            private set(value) {
                field = value
            }
        var size: Long = -1L
            private set(value) {
                field = value
            }
        var chooseType = MediaPickConfig.ChooseType.MULTIPLE_CHOOSE_TYPE
            private set(value) {
                field = value
            }

        fun setCount(count: Int): Builder {
            this.count = count
            return this
        }

        fun setSize(size: Long): Builder {
            this.size = size
            return this
        }

        fun setChooseType(chooseType: MediaPickConfig.ChooseType): Builder {
            this.chooseType = chooseType
            return this
        }

        fun build(): ImagePickConfig {
            return ImagePickConfig(this)
        }

        fun create(): ImagePickConfig {
            return Builder().setCount(9).setSize(-1L)
                .setChooseType(MediaPickConfig.ChooseType.MULTIPLE_CHOOSE_TYPE).build()
        }

    }


}