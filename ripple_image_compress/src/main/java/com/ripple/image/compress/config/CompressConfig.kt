package com.ripple.image.compress.config

import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/5/6 17:02
 * Email: fanyafeng@live.cn
 * Description: 使用压缩图片库的配置
 */
interface CompressConfig {
    fun getTargetDir():File

    /**
     * 图片被压缩后存放的路径
     * 不可空，默认存放在系统目录
     * 并且根据时间戳生成图片的名字
     */
    fun getTargetPath(): String

    /**
     * 设置图片压缩配置
     * 不可空，默认为普清压缩
     * [CompressOption.getLD]
     * 如果特殊需要，比如合同，证件照这些需要查看照片上的字的情况
     * 需要使用高清压缩
     * [CompressOption.getHD]
     */
    fun getCompressOption(): CompressOption
}