package com.ripple.media.picker.view.photoview.scrollerproxy

import android.annotation.TargetApi
import android.content.Context

/**
 * Author: fanyafeng
 * Data: 2020/4/29 10:31
 * Email: fanyafeng@live.cn
 * Description:
 */
@TargetApi(14)
class IcsScroller(private val mContext: Context) : GingerScroller(mContext) {

    override fun computeScrollOffset(): Boolean {
        return super.computeScrollOffset()
    }
}