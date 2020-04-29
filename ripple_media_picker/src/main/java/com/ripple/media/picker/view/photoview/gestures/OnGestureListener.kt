package com.ripple.media.picker.view.photoview.gestures

/**
 * Author: fanyafeng
 * Data: 2020/4/29 09:50
 * Email: fanyafeng@live.cn
 * Description:
 */
interface OnGestureListener {
    /**
     * 拖动
     */
    fun onDrag(dx: Float, dy: Float)

    /**
     * 滑动
     * 当用户滑动view，会有惯性，进行滑行
     */
    fun onFling(startX: Float, startY: Float, velocityX: Float, velocityY: Float)

    /**
     * 缩放
     */
    fun onScale(scaleFactor: Float, focusX: Float, focusY: Float)
}