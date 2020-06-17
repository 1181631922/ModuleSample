package com.ripple.image.compress

import com.ripple.image.compress.model.ImageItem
import com.ripple.image.compress.service.ImageCompressService
import com.ripple.image.compress.service.impl.ImageCompressServiceImpl
import com.ripple.task.engine.ProcessEngine
import com.ripple.task.util.Preconditions
import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/5/6 17:17
 * Email: fanyafeng@live.cn
 * Description:
 */
object RippleImageCompress {

    private var imageCompressEngine: ProcessEngine? = null
    private var imageCompressService: ImageCompressService? = null


    @JvmOverloads
    @JvmStatic
    fun initialize(imageCompressEngine: ProcessEngine, imageCompressService: ImageCompressService) {
        Preconditions.checkNotNull(imageCompressEngine, "需要实现TaskEngine接口，不能为空")
        Preconditions.checkNotNull(imageCompressService, "需要实现ImageCompressService接口，不能为空")
        RippleImageCompress.imageCompressEngine = imageCompressEngine
        RippleImageCompress.imageCompressService = imageCompressService
    }

    @JvmStatic
    fun getImageTaskEngine(): ProcessEngine? {
        return imageCompressEngine
    }

    @JvmStatic
    fun getImageCompressService(): ImageCompressService? {
        return imageCompressService
    }

    /**
     * 单张图片同步压缩
     */
    @JvmStatic
    fun compressImageSync(imageCompressService: ImageCompressService, imageItem: ImageItem) =
        imageCompressService.compress(imageItem)

    /**
     * 单张图片同步压缩
     */
    @JvmStatic
    fun compressImageSync(imageItem: ImageItem) =
        compressImageSync(ImageCompressServiceImpl(), imageItem)

    /**
     * 多张图片同步压缩
     */
    @JvmStatic
    fun compressImageListSync(imageList: List<ImageItem>): List<File> {
        val fileList: ArrayList<File> = arrayListOf()
        imageList.forEach {
            fileList.add(compressImageSync(it))
        }
        return fileList
    }


}