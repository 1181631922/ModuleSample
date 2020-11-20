package com.ripple.media.picker.image

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.config.*
import com.ripple.media.picker.config.impl.CropImageConfigImpl
import com.ripple.media.picker.config.impl.ImagePickConfig
import com.ripple.media.picker.config.impl.ThemeConfig
import com.ripple.media.picker.image.activity.RippleCropImageActivity
import com.ripple.media.picker.image.activity.RippleImagePickerActivity
import com.ripple.media.picker.image.activity.RipplePreviewImageActivity
import com.ripple.media.picker.image.activity.RippleTakePhotoAgencyActivity
import com.ripple.media.picker.image.listener.ImageCropListener
import com.ripple.media.picker.image.listener.SelectImageListListener
import com.ripple.media.picker.image.listener.TakePhotoListener
import com.ripple.media.picker.util.ScreenUtil
import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/4/17 09:22
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleImagePick private constructor() {
    var selectImageListListener: SelectImageListListener? = null

    var takePhotoListener: TakePhotoListener? = null

    var imageCropListener: ImageCropListener? = null

    var takePictureFile: File? = null

    companion object {

        const val RESULT_IMG_LIST = "result_img_list"


        @Volatile
        private var instance: RippleImagePick? = null

        fun getInstance(): RippleImagePick {
            if (instance == null) {
                synchronized(RippleImagePick::class) {
                    if (instance == null) {
                        instance = RippleImagePick()
                    }
                }
            }
            return instance!!
        }
    }

    /**
     * 图片多选
     */
    @JvmOverloads
    fun imagePickForMulti(
        context: Context,
        requestCode: Int = 823,
        config: IImagePickConfig = RippleMediaPick.getInstance().imagePickConfig
    ) {
        RippleMediaPick.getInstance().imageList.clear()
        val intent = Intent(context, RippleImagePickerActivity::class.java)
        intent.putExtra(IImagePickConfig.IMAGE_CONFIG_NAME, config)
        (context as Activity).startActivityForResult(intent, requestCode)
    }

    /**
     * 批量图片预览
     */
    @JvmOverloads
    fun imagePreview(
        context: Context,
        config: IPreviewImageConfig,
        requestCode: Int = IPreviewImageConfig.PREVIEW_RESULT
    ) {
        val intent = Intent(context, RipplePreviewImageActivity::class.java)

        intent.putExtra(IPreviewImageConfig.PREVIEW_IMAGE_CONFIG, config)
        (context as Activity).startActivityForResult(
            intent,
            requestCode
        )
    }

    /**
     * 调用系统拍照，获取相应的回调
     */
    @JvmOverloads
    fun takePhoto(context: Context, requestCode: Int = IImagePickConfig.TAKE_PICTURE_CODE) {
        val intent = Intent(context, RippleTakePhotoAgencyActivity::class.java)
        (context as Activity).startActivityForResult(intent, requestCode)
    }

    /**
     * 通过内部图片选择器进行图片裁剪
     */
    @JvmOverloads
    fun imageCrop(
        context: Context,
        cropImageConfig: CropImageConfig,
        requestCode: Int = CropImageConfig.CROP_IMAGE_REQUEST_CODE,
        themeConfig: MediaThemeConfig? = null
    ) {
        RippleMediaPick.getInstance().imageList.clear()
        val intent = Intent(context, RippleImagePickerActivity::class.java)
        intent.putExtra(
            IImagePickConfig.IMAGE_CONFIG_NAME,
            ImagePickConfig.Builder().setCount(-1).setShowCamera(true).setChooseType(
                MediaPickConfig.ChooseType.SINGLE_CHOOSE_TYPE
            ).setSize(-1L).build()
        )
        intent.putExtra(CropImageConfig.CROP_IMAGE_CONFIG, cropImageConfig)
        if (themeConfig != null) {
            intent.putExtra(
                MediaThemeConfig.THEME_CONFIG,
                themeConfig
            )
        }
        (context as Activity).startActivityForResult(intent, requestCode)
    }


    /**
     * 通过外部传入图片path进行图片裁剪
     */
    @JvmOverloads
    fun imageCrop(
        context: Context,
        imagePath: String,
        requestCode: Int = CropImageConfig.CROP_IMAGE_REQUEST_CODE,
        cropImageConfig: CropImageConfig? = null,
        themeConfig: MediaThemeConfig? = null
    ) {
        val intent = Intent(context, RippleCropImageActivity::class.java)
        intent.putExtra(CropImageConfig.CROP_IMAGE_URL, imagePath)
        if (cropImageConfig != null) {
            intent.putExtra(CropImageConfig.CROP_IMAGE_CONFIG, cropImageConfig)
        }
        if (themeConfig != null) {
            intent.putExtra(MediaThemeConfig.THEME_CONFIG, themeConfig)
        }
        (context as Activity).startActivityForResult(intent, requestCode)
    }
}