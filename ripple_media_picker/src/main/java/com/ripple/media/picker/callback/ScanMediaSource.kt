package com.ripple.media.picker.callback

/**
 * Author: fanyafeng
 * Data: 2020/4/17 09:30
 * Email: fanyafeng@live.cn
 * Description:
 */
interface ScanMediaSource<T> {
    fun onMediaLoaded(mediaList: List<T>)
}