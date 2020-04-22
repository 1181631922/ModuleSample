package com.ripple.media.picker.loadframe.impl

import android.content.Context
import com.ripple.media.picker.loadframe.MediaLoadFrame

/**
 * Author: fanyafeng
 * Data: 2020/4/22 09:56
 * Email: fanyafeng@live.cn
 * Description:
 */
interface VideoLoadFrame : MediaLoadFrame {
    /**
     * 预览视频，内部不处理，交给外部处理
     */
    fun previewVideo(context: Context, videoPath: String)
}
