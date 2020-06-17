package com.ripple.image.compress.model

import com.ripple.image.compress.config.CompressConfig

/**
 * Author: fanyafeng
 * Data: 2020/5/6 17:11
 * Email: fanyafeng@live.cn
 * Description:
 */
interface ImageItem : BaseModel {
    /**
     * 获取压缩数据源
     */
    fun getSourcePath(): String

    /**
     * 获取压缩规则
     */
    fun getCompressConfig(): CompressConfig

    /**
     * tag标签，用于扩展
     */
    fun getTag(): Any?
}