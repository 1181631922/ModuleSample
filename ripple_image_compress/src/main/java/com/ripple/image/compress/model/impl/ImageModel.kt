package com.ripple.image.compress.model.impl

import com.ripple.image.compress.config.CompressConfig
import com.ripple.image.compress.config.impl.SimpleCompressConfig
import com.ripple.image.compress.model.ImageItem

/**
 * Author: fanyafeng
 * Data: 2020/5/6 18:32
 * Email: fanyafeng@live.cn
 * Description:
 */
class ImageModel @JvmOverloads constructor(
    private val sourcePath: String,
    private val compressConfig: CompressConfig = SimpleCompressConfig.Builder().create(),
    private val any: Any? = null
) : ImageItem {
    override fun getSourcePath(): String {
        return sourcePath
    }

    override fun getCompressConfig(): CompressConfig {
        return compressConfig
    }

    override fun getTag(): Any? {
        return any
    }

    override fun toString(): String {
        return "ImageModel(sourcePath='$sourcePath',targetPath='${compressConfig.getTargetDir()}/${compressConfig.getTargetPath()}')"
    }


}