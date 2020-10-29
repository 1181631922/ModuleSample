package com.ripple.media.picker.config

import com.ripple.media.picker.view.CropImageView
import java.io.File
import java.io.Serializable


/**
 * Author: fanyafeng
 * Date: 2020/10/21 14:36
 * Email: fanyafeng@live.cn
 * Description: 图片裁剪接口
 */
interface CropImageConfig : Serializable {

    companion object {
        /*
        配置KEY
         */
        const val CROP_IMAGE_CONFIG = "crop_image_config"

        /*
        设置图片裁剪链接
         */
        const val CROP_IMAGE_URL = "crop_image_url"

        /*
        图片处理完成回调code
         */
        const val CROP_IMAGE_REQUEST_CODE = 516

        /*
        图片处理完成后回调
         */
        const val CROP_IMAGE_REQUEST_RESULT="crop_image_code"
    }

    /**
     * 需要裁剪的图片是圆形还是方形
     * 分一下两种情况：
     * 第一种：如果是方形
     * 就按照此接口实现类设置相应的宽高，以及输出相应的可视框
     * 第二种：如果是圆形
     * 理论上应该宽高相等，但是如果用户手滑写错，则取最小值
     */
    fun getCropImageStyle(): CropImageView.Style

    /**
     * 获取裁剪后图片的宽度
     */
    fun getOutWidth(): Int

    /**
     * 获取裁剪后图片的高度
     */
    fun getOutHeight(): Int

    /**
     * 获取裁剪框的宽度
     */
    fun getInWidth(): Int

    /**
     * 获取裁剪框的高度
     */
    fun getInHeight(): Int

    /**
     * 保存裁剪图片的文件夹
     */
    fun getCropCacheFolder(): File?
}