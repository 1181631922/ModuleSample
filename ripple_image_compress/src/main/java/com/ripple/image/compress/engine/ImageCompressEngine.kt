package com.dmall.compress.image.engine

import com.dmall.handle.task.engine.ProcessEngine
import com.ripple.image.compress.service.ImageCompressService
import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/5/7 10:15
 * Email: fanyafeng@live.cn
 * Description: 图片压缩引擎定义
 */
interface ImageCompressEngine: Serializable {

    companion object {
        /**
         * 单个回调key
         */
        const val IMAGE_ITEM = "image_item"

        /**
         * 成功列表回调key
         */
        const val IMAGE_LIST_SUCCESS = "image_list_success"

        /**
         * 失败列表回调key
         */
        const val IMAGE_LIST_FAILED = "image_list_failed"
    }

    /**
     * 任务引擎
     */
    fun getTaskEngine(): ProcessEngine

    /**
     * 图片压缩服务
     */
    fun getImageCompressService(): ImageCompressService


}