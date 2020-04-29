package com.ripple.media.picker.view.photoview.scrollerproxy

import android.content.Context
import android.widget.Scroller

/**
 * Author: fanyafeng
 * Data: 2020/4/29 10:25
 * Email: fanyafeng@live.cn
 * Description:
 */
class PreGingerScroller(private val mContext: Context) : ScrollerProxy() {

    private val mScroller: Scroller = Scroller(mContext)

    /**
     * Call this when you want to know the new location.  If it returns true,
     * the animation is not yet finished.
     */
    override fun computeScrollOffset() = mScroller.computeScrollOffset()

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
        mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY)
    }

    override fun forceFinished(finish: Boolean) {
        mScroller.forceFinished(finish)
    }

    override fun isFinished() = mScroller.isFinished

    override fun getCurrX() = mScroller.currX

    override fun getCurrY() = mScroller.currY

}