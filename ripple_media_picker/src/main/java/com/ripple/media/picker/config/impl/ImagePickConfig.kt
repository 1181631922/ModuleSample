package com.ripple.media.picker.config.impl

import android.os.Environment
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.config.MediaPickConfig
import com.ripple.media.picker.model.RippleMediaModel
import java.io.File

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
     * 是否显示拍照按钮，默认显示
     */
    private var showCamera = true

    /**
     * 拍照存储路径
     */
    private var photoFile =
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            File(Environment.getExternalStorageDirectory(), "/DCIM/camera/")
        else
            Environment.getDataDirectory()

    /**
     * 默认多选，单选会带裁剪，暂时不支持
     */
    private var chooseType = MediaPickConfig.ChooseType.MULTIPLE_CHOOSE_TYPE

    /**
     * 已选中图片
     */
    private var selectList: List<RippleMediaModel> = mutableListOf()

    init {
        count = builder.count
        size = builder.size
        chooseType = builder.chooseType
        photoFile = builder.photoFile
        showCamera = builder.showCamera
        selectList = builder.selectList
    }

    override fun getPhotoFile(): File {
        return photoFile
    }

    override fun showCamera(): Boolean {
        return showCamera;
    }

    override fun getSelectList(): List<RippleMediaModel> {
        return selectList
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

        var showCamera: Boolean = true
            private set(value) {
                field = value
            }

        var photoFile = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
            File(Environment.getExternalStorageDirectory(), "/DCIM/camera/")
        else
            Environment.getDataDirectory()
            private set(value) {
                field = value
            }

        var selectList: List<RippleMediaModel> = mutableListOf()
            private set(value) {
                field = value
            }

        fun setShowCamera(showCamera: Boolean): Builder {
            this.showCamera = showCamera
            return this
        }

        fun setPhotoFile(photoFile: File): Builder {
            this.photoFile = photoFile
            return this
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

        fun setSelectList(selectList: List<RippleMediaModel>): Builder {
            this.selectList = selectList
            return this
        }

        fun build(): ImagePickConfig {
            return ImagePickConfig(this)
        }

        fun create(): ImagePickConfig {
            return Builder().setCount(9).setSize(-1L)
                .setChooseType(MediaPickConfig.ChooseType.MULTIPLE_CHOOSE_TYPE)
                .setPhotoFile(
                    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
                        File(Environment.getExternalStorageDirectory(), "/DCIM/camera/")
                    else
                        Environment.getDataDirectory()
                ).setShowCamera(true)
                .build()
        }

    }


}