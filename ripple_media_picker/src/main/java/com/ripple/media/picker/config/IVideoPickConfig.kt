package com.ripple.media.picker.config

/**
 * Author: fanyafeng
 * Data: 2020/4/21 18:42
 * Email: fanyafeng@live.cn
 * Description:
 */
interface IVideoPickConfig : MediaPickConfig {
    fun getVideoLength(): Long
}