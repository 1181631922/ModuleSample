package com.ripple.media.picker.view.photoview

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import android.view.GestureDetector
import android.view.View
import android.widget.ImageView
import com.ripple.media.picker.view.photoview.impl.PhotoViewAttacher
import java.nio.channels.FileLock

/**
 * Author: fanyafeng
 * Data: 2020/4/26 16:10
 * Email: fanyafeng@live.cn
 * Description:
 */
interface IPhotoView {
    companion object {
        const val DEFAULT_MAX_SCALE = 3.0F
        const val DEFAULT_MID_SCALE = 1.75F
        const val DEFAULT_MIN_SCALE = 1.0F
        const val DEFAULT_ZOOM_DURATION = 200
    }

    /**
     * 图片是否可以变焦
     */
    fun canZoom(): Boolean

    /**
     * 获取可绘制图形的显示矩形
     * 用于放缩和平移
     */
    fun getDisplayRect(): RectF?


    /**
     * 设置当前可绘制图形矩阵
     */
    fun setDisplayMatrix(matrix: Matrix?): Boolean

    /**
     * 获取当前可绘制图形矩阵
     */
    fun getDisplayMatrix(): Matrix

    /**
     * 返回最小Scale
     */
    fun getMinimumScale():Float

    fun setMinimumScale(minimumScale: Float)

    fun getMediumScale():Float

    fun setMediumScale(mediumScale: Float)

    fun getMaximumScale():Float

    fun setMaximumScale(maximumScale: Float)

    /**
     * 获取当前的Scale
     */
    fun getScale(): Float

    fun getScaleType(): ImageView.ScaleType

    /**
     * 是否允许父类拦截触摸事件
     */
    fun setAllowParentInterceptOnEdge(allow: Boolean)

    /**
     * 设置scale level
     */
    fun setScaleLevels(minimumScale: Float, mediumScale: Float, maximumScale: Float)

    /**
     * 旋转图片
     */
    fun setRotationTo(rotationDegree: Float)

    fun setRotationBy(rotationDegree: Float)

    /**
     * 指定缩放比例
     */
    fun setScale(scale: Float)

    fun setScale(scale: Float, animate: Boolean)

    fun setScale(scale: Float, focalX: Float, focalY: Float, animate: Boolean)

    /**
     * 控制图像调整大小和缩放的方式
     */
    fun setScaleType(scaleType: ImageView.ScaleType)

    /**
     * 是否允许缩放功能
     */
    fun setZoomable(zoomable: Boolean)

    /**
     *
     */
    fun setPhotoViewRotation(rotationDegree: Float)

    /**
     * 获取可见区域的bitmap
     */
    fun getVisibleRectangleBitmap(): Bitmap?

    /**
     * 设置变焦转换速度
     */
    fun setZoomTransitionDuration(milliseconds: Int)

    /**
     * 获取当前类的实现
     */
    fun getIPhotoViewImplementation(): IPhotoView

    /**
     * 长按
     */
    fun setOnLongClickListener(listener: View.OnLongClickListener)

    /**
     * 设置矩阵更改监听
     */
    fun setOnMatrixChangeListener(listener: PhotoViewAttacher.Companion.OnMatrixChangedListener)

    fun setOnPhotoTapListener(listener: PhotoViewAttacher.Companion.OnPhotoTapListener)

    fun getOnPhotoTapListener(): PhotoViewAttacher.Companion.OnPhotoTapListener?

    fun setOnViewTapListener(listener: PhotoViewAttacher.Companion.OnViewTapListener)

    fun getOnViewTapListener(): PhotoViewAttacher.Companion.OnViewTapListener?

    fun setOnDoubleTapListener(newOnDoubleTapListener: GestureDetector.OnDoubleTapListener?)

    fun setOnScaleChangeListener(onScaleChangeListener: PhotoViewAttacher.Companion.OnScaleChangeListener)


}