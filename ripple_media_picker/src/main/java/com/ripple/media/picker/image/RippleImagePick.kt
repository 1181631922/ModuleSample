package com.ripple.media.picker.image

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.image.activity.RippleImagePickerActivity
import com.ripple.media.picker.image.listener.SelectImageListListener
import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/4/17 09:22
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleImagePick private constructor() {
    var selectImageListListener: SelectImageListListener? = null

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
}