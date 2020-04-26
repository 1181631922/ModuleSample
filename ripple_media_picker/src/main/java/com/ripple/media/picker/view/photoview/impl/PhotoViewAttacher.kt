package com.ripple.media.picker.view.photoview.impl

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import com.ripple.media.picker.view.photoview.IPhotoView


/**
 * Author: fanyafeng
 * Data: 2020/4/26 16:25
 * Email: fanyafeng@live.cn
 * Description:
 */
class PhotoViewAttacher : IPhotoView, View.OnTouchListener, GestureDetector.OnGestureListener,
    ViewTreeObserver.OnGlobalLayoutListener {

    companion object {
        /**
         * 矩阵改变后的回调
         */
        interface OnMatrixChangedListener {
            fun onMatrixChanged(rectF: Rect)
        }

        /**
         * scale改变后的回调
         */
        interface OnScaleChangeListener {
            fun onScaleChange(scaleFactor: Float, focusX: Float, focusY: Float)
        }

        /**
         * 图片顶层点击回调
         */
        interface OnPhotoTapListener {
            fun onPhotoTap(view: View, x: Float, y: Float)
        }

        /**
         * 当前view顶层点击回调
         */
        interface OnViewTapListener {
            fun onViewTap(view: View, x: Float, y: Float)
        }

        private val TAG = PhotoViewAttacher::class.java.simpleName

        /**
         * 由快到慢的插值器
         */
        val interpllator = AccelerateInterpolator()

        val ZOOM_DURATION = IPhotoView.DEFAULT_ZOOM_DURATION

        val EDGE_NONE = -1
        val EDGE_LEFT = 0
        val EDGE_RIGHT = 1
        val EDGE_BOTH = 2

    }

    private val mMinScale = IPhotoView.DEFAULT_MIN_SCALE
    private val mMidScale = IPhotoView.DEFAULT_MID_SCALE
    private val mMaxScale = IPhotoView.DEFAULT_MAX_SCALE

    /**
     * 默认允许父类拦截事件
     */
    private var mAllowParentInterceptOnEdge = true

    /**
     * 阻止父类拦截事件
     */
    private var mBlockParentIntercept = false

    private val mMatrixChangeListener: OnMatrixChangedListener? = null
    private val mPhotoTapListener: OnPhotoTapListener? = null
    private val mViewTapListener: OnViewTapListener? = null
    private val mLongClickListener: OnLongClickListener? = null
    private val mScaleChangeListener: OnScaleChangeListener? = null
    override fun canZoom(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDisplayRect(): RectF {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setDisplayMatrix(matrix: Matrix): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDisplayMatrix(): Matrix {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMinimumScale() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMinimumScale(minimumScale: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMediumScale() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMediumScale(mediumScale: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMaximumScale() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMaximumScale(maximumScale: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getScale(): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getScaleType(): ImageView.ScaleType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setAllowParentInterceptOnEdge(allow: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setScaleLevels(minimumScale: Float, mediumScale: Float, maximumScale: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRotationTo(rotationDegree: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRotationBy(rotationDegree: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setScale(scale: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setScale(scale: Float, animate: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setScale(scale: Float, focalX: Float, focalY: Float, animate: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setScaleType(scaleType: ImageView.ScaleType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setZoomable(zoomable: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPhotoViewRotation(rotationDegree: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getVisibleRectangleBitmap(): Bitmap {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setZoomTransitionDuration(milliseconds: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getIPhotoViewImplementation(): IPhotoView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnLongClickListener(listener: OnLongClickListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnMatrixChangeListener(listener: OnMatrixChangedListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnPhotoTapListener(listener: OnPhotoTapListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOnPhotoTapListener(): OnPhotoTapListener {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnViewTapListener(listener: OnViewTapListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOnViewTapListener(): OnViewTapListener {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnDoubleTapListener(newOnDoubleTapListener: GestureDetector.OnDoubleTapListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnScaleChangeListener(onScaleChangeListener: OnScaleChangeListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onShowPress(e: MotionEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDown(e: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLongPress(e: MotionEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGlobalLayout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}