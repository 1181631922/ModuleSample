package com.ripple.media.picker.view.photoview.scrollerproxy

import android.annotation.TargetApi
import android.content.Context
import android.widget.OverScroller

/**
 * Author: fanyafeng
 * Data: 2020/4/29 10:31
 * Email: fanyafeng@live.cn
 * Description:
 */
@TargetApi(9)
open class GingerScroller(private val mContext: Context) : ScrollerProxy() {

    protected val mScroller = OverScroller(mContext)
    protected var mFirstScroll = false

    override fun computeScrollOffset(): Boolean {
        if (mFirstScroll) {
            mScroller.computeScrollOffset()
            mFirstScroll = false
        }
        return mScroller.computeScrollOffset()
    }

    override fun fling(
        startX: Int,
        startY: Int,
        velocityX: Int,
        velocityY: Int,
        minX: Int,
        maxX: Int,
        minY: Int,
        maxY: Int,
        overX: Int,
        overY: Int
    ) {
        mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY)
    }

    override fun forceFinished(finish: Boolean) {
        mScroller.forceFinished(finish)
    }

    override fun isFinished() = mScroller.isFinished

    override fun getCurrX() = mScroller.currX

    override fun getCurrY() = mScroller.currY

}