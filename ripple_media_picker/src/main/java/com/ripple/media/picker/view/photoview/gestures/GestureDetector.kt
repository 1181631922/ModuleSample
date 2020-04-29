package com.ripple.media.picker.view.photoview.gestures

import android.view.MotionEvent

/**
 * Author: fanyafeng
 * Data: 2020/4/29 09:56
 * Email: fanyafeng@live.cn
 * Description:
 */
interface GestureDetector {
    /**
     * touch事件
     */
    fun onTouchEvent(event: MotionEvent):Boolean

    /**
     * 是否正在缩放
     */
    fun isScaling(): Boolean

    /**
     * 是否正在拖动
     */
    fun isDragging(): Boolean

    /**
     * 设置手势监听
     */
    fun setOnGestureListener(listener: OnGestureListener)
}