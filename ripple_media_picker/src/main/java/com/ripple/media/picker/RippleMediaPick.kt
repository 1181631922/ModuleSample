package com.ripple.media.picker

import android.os.Bundle
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.config.MediaThemeConfig
import com.ripple.media.picker.config.impl.ImagePickConfig
import com.ripple.media.picker.config.impl.ThemeConfig
import com.ripple.media.picker.loadframe.impl.ImageLoadFrame
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.util.Preconditions
import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/4/17 09:27
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleMediaPick private constructor() {
    /**
     * 图片加载代理
     */
    var imageLoadFrame: ImageLoadFrame? = null
        get() = Preconditions.checkNotNull(field, "ImageLoadFrame接口未实现")
        private set(value) {
            field = value
        }

    /**
     * 图片选择配置，支持全局以及局部配置
     * 此为全局配置
     */
    var imagePickConfig: IImagePickConfig = ImagePickConfig.Builder().create()
        get() = Preconditions.checkNotNull(field, "IImagePickConfig接口未实现")
        private set(value) {
            field = value
        }

    /**
     * 主题配置，暂时只支持全局配置(已经支持局部配置)，后期会加上局部配置
     */
    var themeConfig: MediaThemeConfig = ThemeConfig.Builder().create()
        get() = Preconditions.checkNotNull(field, "MediaThemeConfig接口未实现")
        private set(value) {
            field = value
        }

    /**
     * 选取图片列表
     */
    var imageList = ArrayList<RippleMediaModel>()
        private set(value) {
            field = value
        }

    companion object {
        /**
         * 双check单例获取全局唯一对象
         */
        @Volatile
        private var instance: RippleMediaPick? = null

        fun getInstance(): RippleMediaPick {
            if (instance == null) {
                synchronized(RippleMediaPick::class) {
                    if (instance == null) {
                        instance = RippleMediaPick()
                    }
                }
            }
            return instance!!
        }
    }

    fun setImageLoadFrame(imageLoadFrame: ImageLoadFrame): RippleMediaPick {
        this.imageLoadFrame = Preconditions.checkNotNull(imageLoadFrame, "ImageLoadFrame 接口初始化不能为空")
        return this
    }

    fun setImagePickConfig(imagePickConfig: IImagePickConfig): RippleMediaPick {
        this.imagePickConfig =
            Preconditions.checkNotNull(imagePickConfig, "实现IImagePickConfig接口定义类不能为空")
        return this
    }

    fun setThemeConfig(themeConfig: MediaThemeConfig): RippleMediaPick {
        this.themeConfig = Preconditions.checkNotNull(themeConfig, "实现MediaThemeConfig接口定义类不能为空")
        return this
    }


    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        imageLoadFrame = savedInstanceState.getSerializable("imageLoadFrame") as ImageLoadFrame?
        imagePickConfig = savedInstanceState.getSerializable("imagePickConfig") as IImagePickConfig
        themeConfig = savedInstanceState.getSerializable("themeConfig") as MediaThemeConfig
        imageList = savedInstanceState.getSerializable("imageList") as ArrayList<RippleMediaModel>
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("imageLoadFrame", imageLoadFrame)
        outState.putSerializable("imagePickConfig", imagePickConfig)
        outState.putSerializable("themeConfig", themeConfig)
        outState.putSerializable("imageList", imageList)
    }
}