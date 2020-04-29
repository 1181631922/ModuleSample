package com.ripple.media.picker.view.photoview.impl

import android.R.attr
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Matrix.ScaleToFit
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import com.ripple.media.picker.util.LogUtil
import com.ripple.media.picker.view.photoview.IPhotoView
import com.ripple.media.picker.view.photoview.IPhotoView.Companion.DEFAULT_ZOOM_DURATION
import com.ripple.media.picker.view.photoview.gestures.OnGestureListener
import com.ripple.media.picker.view.photoview.gestures.VersionedGestureDetector
import com.ripple.media.picker.view.photoview.impl.Compat.postOnAnimation
import com.ripple.media.picker.view.photoview.scrollerproxy.ScrollerProxy
import java.lang.String
import java.lang.ref.WeakReference
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


/**
 * Author: fanyafeng
 * Data: 2020/4/26 16:25
 * Email: fanyafeng@live.cn
 * Description:
 */
class PhotoViewAttacher @JvmOverloads constructor(
    private val imageView: ImageView,
    zoomable: Boolean = true
) :
    IPhotoView,
    View.OnTouchListener, OnGestureListener,
    ViewTreeObserver.OnGlobalLayoutListener {

    companion object {
        /**
         * 矩阵改变后的回调
         */
        interface OnMatrixChangedListener {
            fun onMatrixChanged(rectF: RectF)
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
            fun onPhotoTap(view: View?, x: Float, y: Float)
        }

        /**
         * 当前view顶层点击回调
         */
        interface OnViewTapListener {
            fun onViewTap(view: View?, x: Float, y: Float)
        }

        private val TAG = PhotoViewAttacher::class.java.simpleName


        /**
         * 由快到慢的插值器
         */
        val sInterpolator = AccelerateDecelerateInterpolator()

        var ZOOM_DURATION = IPhotoView.DEFAULT_ZOOM_DURATION

        val EDGE_NONE = -1
        val EDGE_LEFT = 0
        val EDGE_RIGHT = 1
        val EDGE_BOTH = 2

        private fun checkZoomLevels(minZoom: Float, midZoom: Float, maxZoom: Float) {
            if (minZoom >= midZoom) {
                throw IllegalArgumentException(
                    "MinZoom has to be less than MidZoom"
                );
            } else if (midZoom >= maxZoom) {
                throw IllegalArgumentException(
                    "MidZoom has to be less than MaxZoom"
                );
            }
        }

        private fun hasDrawable(imageView: ImageView?): Boolean {
            return imageView != null && imageView.drawable != null
        }

        private fun isSupportedScaleType(scaleType: ImageView.ScaleType?): Boolean {
            if (scaleType == null) {
                return false
            }
            return when (scaleType) {
                ImageView.ScaleType.MATRIX -> throw IllegalArgumentException(
                    scaleType.name
                        .toString() + " is not supported in PhotoView"
                )
                else -> true
            }
        }

        private fun setImageViewScaleTypeMatrix(imageView: ImageView?) {
            if (imageView != null && imageView !is IPhotoView) {
                if (ImageView.ScaleType.MATRIX != imageView.scaleType) {
                    imageView.scaleType = ImageView.ScaleType.MATRIX;
                }
            }
        }
    }

    private var mMinScale = IPhotoView.DEFAULT_MIN_SCALE
    private var mMidScale = IPhotoView.DEFAULT_MID_SCALE
    private var mMaxScale = IPhotoView.DEFAULT_MAX_SCALE

    /**
     * 默认允许父类拦截事件
     */
    private var mAllowParentInterceptOnEdge = true

    /**
     * 阻止父类拦截事件
     */
    private var mBlockParentIntercept = false

    private var mImageView: WeakReference<ImageView>? = null

    /**
     * 手势处理
     */
    private var mGestureDetector: GestureDetector? = null
    private var mScaleDragDetector: com.ripple.media.picker.view.photoview.gestures.GestureDetector? =
        null

    private val mBaseMatrix = Matrix()
    private val mDrawMatrix = Matrix()
    private val mSuppMatrix = Matrix()
    private val mDisplayRect = RectF()

    private val mMatrixValues = FloatArray(9)

    private var mMatrixChangeListener: OnMatrixChangedListener? = null
    private var mPhotoTapListener: OnPhotoTapListener? = null
    private var mViewTapListener: OnViewTapListener? = null
    private var mLongClickListener: OnLongClickListener? = null
    private var mScaleChangeListener: OnScaleChangeListener? = null

    private var mIvLeft: Int? = null
    private var mIvTop: Int? = null
    private var mIvRight: Int? = null
    private var mIvBottom: Int? = null

    private var mCurrentFlingRunnable: FlingRunnable? = null
    private var mScrollEdge = EDGE_BOTH

    private var mZoomEnabled: Boolean? = null
    private var mScaleType = ImageView.ScaleType.FIT_CENTER

    init {
        mImageView = WeakReference(imageView)

        imageView.isDrawingCacheEnabled = true
        imageView.setOnTouchListener(this)

        val observer = imageView.viewTreeObserver
        observer?.let {
            observer.addOnGlobalLayoutListener(this)
        }

        setImageViewScaleTypeMatrix(imageView)
        if (!imageView.isInEditMode) {
            mScaleDragDetector = VersionedGestureDetector.newInstance(imageView.context, this)
            mGestureDetector = GestureDetector(imageView.context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onLongPress(e: MotionEvent?) {
                        mLongClickListener?.onLongClick(getImageView())
                    }
                })

            mGestureDetector?.setOnDoubleTapListener(DefaultOnDoubleTapListener(this));

            setZoomable(zoomable);
        }

    }


    override fun canZoom() = mZoomEnabled ?: false

    override fun getDisplayRect(): RectF? {
        checkMatrixBounds();
        return getDisplayRect(getDrawMatrix());
    }

    private fun checkMatrixBounds(): Boolean {
        val imageView = getImageView() ?: return false
        val rect = getDisplayRect(getDrawMatrix()) ?: return false
        val height = rect.height()
        val width = rect.width()
        var deltaX = 0f
        var deltaY = 0f
        val viewHeight: Int = getImageViewHeight(imageView)
        if (height <= viewHeight) {
            deltaY = when (mScaleType) {
                ScaleType.FIT_START -> -rect.top
                ScaleType.FIT_END -> viewHeight - height - rect.top
                else -> (viewHeight - height) / 2 - rect.top
            }
        } else if (rect.top > 0) {
            deltaY = -rect.top
        } else if (rect.bottom < viewHeight) {
            deltaY = viewHeight - rect.bottom
        }
        val viewWidth: Int = getImageViewWidth(imageView)
        if (width <= viewWidth) {
            deltaX = when (mScaleType) {
                ScaleType.FIT_START -> -rect.left
                ScaleType.FIT_END -> viewWidth - width - rect.left
                else -> (viewWidth - width) / 2 - rect.left
            }
            mScrollEdge = EDGE_BOTH
        } else if (rect.left > 0) {
            mScrollEdge = EDGE_LEFT
            deltaX = -rect.left
        } else if (rect.right < viewWidth) {
            deltaX = viewWidth - rect.right
            mScrollEdge = EDGE_RIGHT
        } else {
            mScrollEdge = EDGE_NONE
        }

        mSuppMatrix.postTranslate(deltaX, deltaY)
        return true
    }

    private fun getImageViewWidth(imageView: ImageView?): Int {
        return if (null == imageView) 0 else imageView.width - imageView.paddingLeft - imageView.paddingRight
    }

    private fun getImageViewHeight(imageView: ImageView?): Int {
        return if (null == imageView) 0 else imageView.height - imageView.paddingTop - imageView.paddingBottom
    }

    override fun setDisplayMatrix(matrix: Matrix?): Boolean {
        requireNotNull(matrix) { "Matrix cannot be null" }

        val imageView = getImageView() ?: return false

        if (null == imageView.drawable) return false

        mSuppMatrix.set(matrix)
        setImageViewMatrix(getDrawMatrix())
        checkMatrixBounds()

        return true
    }

    override fun getDisplayMatrix() = Matrix(getDrawMatrix())


    override fun getMinimumScale() = mMinScale

    override fun setMinimumScale(minimumScale: Float) {
        checkZoomLevels(minimumScale, mMidScale, mMaxScale);
        mMinScale = minimumScale;
    }

    override fun getMediumScale() = mMidScale

    override fun setMediumScale(mediumScale: Float) {
        checkZoomLevels(mMinScale, mediumScale, mMaxScale);
        mMidScale = mediumScale;
    }

    override fun getMaximumScale() = mMaxScale

    override fun setMaximumScale(maximumScale: Float) {
        checkZoomLevels(mMinScale, mMidScale, maximumScale);
        mMaxScale = maximumScale;
    }

    override fun getScale(): Float {
        return sqrt(
            getValue(
                mSuppMatrix,
                Matrix.MSCALE_X
            ).toDouble().pow(2.0) + getValue(
                mSuppMatrix,
                Matrix.MSKEW_Y
            ).toDouble().pow(2.0)
        ).toFloat()
    }

    private fun getValue(matrix: Matrix, whichValue: Int): Float {
        matrix.getValues(mMatrixValues)
        return mMatrixValues[whichValue]
    }

    override fun getScaleType() = mScaleType

    override fun setAllowParentInterceptOnEdge(allow: Boolean) {
        mAllowParentInterceptOnEdge = allow;
    }

    override fun setScaleLevels(minimumScale: Float, mediumScale: Float, maximumScale: Float) {
        checkZoomLevels(minimumScale, mediumScale, maximumScale);
        mMinScale = minimumScale;
        mMidScale = mediumScale;
        mMaxScale = maximumScale;
    }

    override fun setRotationTo(rotationDegree: Float) {
        mSuppMatrix.setRotate(rotationDegree % 360);
        checkAndDisplayMatrix();
    }

    override fun setRotationBy(rotationDegree: Float) {
        mSuppMatrix.postRotate(rotationDegree % 360);
        checkAndDisplayMatrix();
    }

    override fun setScale(scale: Float) {
        setScale(scale, false)
    }

    override fun setScale(scale: Float, animate: Boolean) {
        val imageView = getImageView()

        if (null != imageView) {
            setScale(
                scale,
                (imageView.right / 2).toFloat(),
                (imageView.bottom / 2).toFloat(),
                animate
            )
        }
    }

    override fun setScale(scale: Float, focalX: Float, focalY: Float, animate: Boolean) {
        val imageView = getImageView()

        if (null != imageView) {
            if (scale < mMinScale || scale > mMaxScale) {
                LogUtil.d(
                    msg =
                    "Scale must be within the range of minScale and maxScale"
                )
                return
            }
            if (animate) {
                imageView.post(
                    AnimatedZoomRunnable(
                        getScale(), scale,
                        focalX, focalY
                    )
                )
            } else {
                mSuppMatrix.setScale(scale, scale, focalX, focalY)
                checkAndDisplayMatrix()
            }
        }
    }

    override fun setScaleType(scaleType: ImageView.ScaleType) {
        if (isSupportedScaleType(scaleType) && scaleType != mScaleType) {
            mScaleType = scaleType;

            update();
        }
    }

    fun update() {
        val imageView = getImageView()
        if (null != imageView) {
            if (mZoomEnabled!!) {
                setImageViewScaleTypeMatrix(imageView)

                updateBaseMatrix(imageView.drawable)
            } else {
                resetMatrix()
            }
        }
    }

    override fun setZoomable(zoomable: Boolean) {
        mZoomEnabled = zoomable;
        update();
    }

    override fun setPhotoViewRotation(rotationDegree: Float) {
        mSuppMatrix.setRotate(rotationDegree % 360)
        checkAndDisplayMatrix();
    }

    private fun checkAndDisplayMatrix() {
        if (checkMatrixBounds()) {
            setImageViewMatrix(getDrawMatrix())
        }
    }

    override fun getVisibleRectangleBitmap(): Bitmap? {
        val imageView = getImageView()
        return imageView?.drawingCache
    }

    override fun setZoomTransitionDuration(milliseconds: Int) {
        ZOOM_DURATION = if (milliseconds < 0) {
            DEFAULT_ZOOM_DURATION
        } else {
            milliseconds
        }
    }

    override fun getIPhotoViewImplementation() = this

    override fun setOnLongClickListener(listener: OnLongClickListener) {
        mLongClickListener = listener
    }

    override fun setOnMatrixChangeListener(listener: OnMatrixChangedListener) {
        mMatrixChangeListener = listener
    }

    override fun setOnPhotoTapListener(listener: OnPhotoTapListener) {
        mPhotoTapListener = listener
    }

    override fun getOnPhotoTapListener() = mPhotoTapListener

    override fun setOnViewTapListener(listener: OnViewTapListener) {
        mViewTapListener = listener
    }

    override fun getOnViewTapListener() = mViewTapListener

    override fun setOnDoubleTapListener(newOnDoubleTapListener: GestureDetector.OnDoubleTapListener?) {
        if (newOnDoubleTapListener != null) {
            this.mGestureDetector?.setOnDoubleTapListener(newOnDoubleTapListener);
        } else {
            this.mGestureDetector?.setOnDoubleTapListener(DefaultOnDoubleTapListener(this));
        }
    }

    override fun setOnScaleChangeListener(onScaleChangeListener: OnScaleChangeListener) {
        this.mScaleChangeListener = onScaleChangeListener;
    }

    override fun onTouch(v: View?, ev: MotionEvent): Boolean {
        var handled = false

        if (mZoomEnabled == true && hasDrawable(v as ImageView?)) {
            val parent = v!!.parent
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (null != parent) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        LogUtil.d(msg = "onTouch getParent() returned null")
                    }

                    cancelFling()
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP ->
                    // to min scale
                    if (getScale() < mMinScale) {
                        val rect = getDisplayRect()
                        if (null != rect) {
                            v!!.post(
                                AnimatedZoomRunnable(
                                    getScale(), mMinScale,
                                    rect.centerX(), rect.centerY()
                                )
                            )
                            handled = true
                        }
                    }
            }

            if (null != mScaleDragDetector) {
                val wasScaling = mScaleDragDetector?.isScaling() ?: false
                val wasDragging = mScaleDragDetector?.isDragging() ?: false
                handled = mScaleDragDetector?.onTouchEvent(ev) ?: false
                val didntScale = !wasScaling && !mScaleDragDetector!!.isScaling()
                val didntDrag = !wasDragging && !mScaleDragDetector!!.isDragging()
                mBlockParentIntercept = didntScale && didntDrag
            }

            if (null != mGestureDetector && mGestureDetector!!.onTouchEvent(ev)) {
                handled = true
            }
        }

        return handled
    }

    override fun onGlobalLayout() {
        val imageView = getImageView()

        if (null != imageView) {
            if (mZoomEnabled!!) {
                val top = imageView.top
                val right = imageView.right
                val bottom = imageView.bottom
                val left = imageView.left
                if (top != mIvTop || bottom != mIvBottom || left != mIvLeft || right != mIvRight
                ) {
                    updateBaseMatrix(imageView.drawable)

                    mIvTop = top
                    mIvRight = right
                    mIvBottom = bottom
                    mIvLeft = left
                }
            } else {
                updateBaseMatrix(imageView.drawable)
            }
        }
    }

    private fun updateBaseMatrix(d: Drawable?) {
        val imageView = getImageView()
        if (null == imageView || null == d) {
            return
        }
        val viewWidth = getImageViewWidth(imageView).toFloat()
        val viewHeight = getImageViewHeight(imageView).toFloat()
        val drawableWidth = d.intrinsicWidth
        val drawableHeight = d.intrinsicHeight
        mBaseMatrix.reset()
        val widthScale = viewWidth / drawableWidth
        val heightScale = viewHeight / drawableHeight
        if (mScaleType == ScaleType.CENTER) {
            mBaseMatrix.postTranslate(
                (viewWidth - drawableWidth) / 2f,
                (viewHeight - drawableHeight) / 2f
            )
        } else if (mScaleType == ScaleType.CENTER_CROP) {
            val scale = widthScale.coerceAtLeast(heightScale)
            mBaseMatrix.postScale(scale, scale)
            mBaseMatrix.postTranslate(
                (viewWidth - drawableWidth * scale) / 2f,
                (viewHeight - drawableHeight * scale) / 2f
            )
        } else if (mScaleType == ScaleType.CENTER_INSIDE) {
            val scale =
                1.0f.coerceAtMost(Math.min(widthScale, heightScale))
            mBaseMatrix.postScale(scale, scale)
            mBaseMatrix.postTranslate(
                (viewWidth - drawableWidth * scale) / 2f,
                (viewHeight - drawableHeight * scale) / 2f
            )
        } else {
            val mTempSrc = RectF(0F, 0F, drawableWidth.toFloat(), drawableHeight.toFloat())
            val mTempDst = RectF(0F, 0F, viewWidth, viewHeight)
            when (mScaleType) {
                ScaleType.FIT_CENTER -> mBaseMatrix
                    .setRectToRect(mTempSrc, mTempDst, ScaleToFit.CENTER)
                ScaleType.FIT_START -> mBaseMatrix.setRectToRect(
                    mTempSrc,
                    mTempDst,
                    ScaleToFit.START
                )
                ScaleType.FIT_END -> mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.END)
                ScaleType.FIT_XY -> mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.FILL)
            }
        }
        resetMatrix()
    }

    private fun resetMatrix() {
        mSuppMatrix.reset()
        setImageViewMatrix(getDrawMatrix())
        checkMatrixBounds()
    }

    override fun onDrag(dx: Float, dy: Float) {
        if (mScaleDragDetector!!.isScaling()) {
            return  // Do not drag if we are already scaling
        }

        LogUtil.d(
            msg =
            String.format("onDrag: dx: %.2f. dy: %.2f", dx, dy)
        )

        val imageView = getImageView()
        mSuppMatrix.postTranslate(dx, dy)
        checkAndDisplayMatrix()


        val parent = imageView!!.parent
        if (mAllowParentInterceptOnEdge && !mScaleDragDetector!!.isScaling() && !mBlockParentIntercept) {
            if (mScrollEdge == EDGE_BOTH || mScrollEdge == EDGE_LEFT && dx >= 1f
                || mScrollEdge == EDGE_RIGHT && dx <= -1f
            ) {
                parent?.requestDisallowInterceptTouchEvent(false)
            }
        } else {
            parent?.requestDisallowInterceptTouchEvent(true)
        }
    }

    override fun onFling(startX: Float, startY: Float, velocityX: Float, velocityY: Float) {
        LogUtil.d(
            msg =
            "onFling. sX: " + attr.startX + " sY: " + attr.startY + " Vx: "
                    + velocityX + " Vy: " + velocityY
        )
        val imageView = getImageView()
        mCurrentFlingRunnable = FlingRunnable(imageView!!.context)
        mCurrentFlingRunnable!!.fling(
            getImageViewWidth(imageView),
            getImageViewHeight(imageView), velocityX.toInt(), velocityY.toInt()
        )
        imageView?.post(mCurrentFlingRunnable)
    }

    override fun onScale(scaleFactor: Float, focusX: Float, focusY: Float) {
        LogUtil.d(
            msg =
            String.format(
                "onScale: scale: %.2f. fX: %.2f. fY: %.2f",
                scaleFactor, focusX, focusY
            )
        );

        if (getScale() < mMaxScale || scaleFactor < 1f) {
            if (null != mScaleChangeListener) {
                mScaleChangeListener?.onScaleChange(scaleFactor, focusX, focusY);
            }
            mSuppMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            checkAndDisplayMatrix();
        }
    }

    fun getImageView(): ImageView? {
        var imageView: ImageView? = null
        if (mImageView != null) {
            imageView = mImageView!!.get()
        }
        if (imageView == null) {
            cleanUp()
            LogUtil.d(
                msg =
                "ImageView no longer exists. You should not use this PhotoViewAttacher any more."
            );
        }
        return imageView
    }

    fun cleanUp() {
        if (mImageView == null) {
            return
        }

        val imageView = mImageView!!.get()
        imageView?.let {
            val observer = imageView.viewTreeObserver
            if (observer != null && observer.isAlive) {
                observer.removeGlobalOnLayoutListener(this)
            }
            imageView.setOnTouchListener(null)
            cancelFling()
        }

        mGestureDetector?.let {
            mGestureDetector!!.setOnDoubleTapListener(null)
        }

        mMatrixChangeListener = null
        mPhotoTapListener = null
        mViewTapListener = null

        mImageView = null
    }

    private fun cancelFling() {
        mCurrentFlingRunnable?.let {
            it.cancelFling()
            mCurrentFlingRunnable = null
        }
    }

    inner class AnimatedZoomRunnable(
        currentZoom: Float, targetZoom: Float,
        private val mFocalX: Float, private val mFocalY: Float
    ) :
        Runnable {
        private val mStartTime: Long
        private val mZoomStart: Float
        private val mZoomEnd: Float
        override fun run() {
            val imageView: ImageView = getImageView() ?: return
            val t = interpolate()
            val scale = mZoomStart + t * (mZoomEnd - mZoomStart)
            val deltaScale: Float = scale / getScale()
            onScale(deltaScale, mFocalX, mFocalY)

            // We haven't hit our target scale yet, so post ourselves again
            if (t < 1f) {
                postOnAnimation(imageView, this)
            }
        }

        private fun interpolate(): Float {
            var t =
                1f * (System.currentTimeMillis() - mStartTime) / ZOOM_DURATION
            t = Math.min(1f, t)
            t = sInterpolator.getInterpolation(t)
            return t
        }

        init {
            mStartTime = System.currentTimeMillis()
            mZoomStart = currentZoom
            mZoomEnd = targetZoom
        }
    }

    inner class FlingRunnable(private val mContext: Context) : Runnable {

        private val mScroller = ScrollerProxy.getScrller(mContext)
        private var mCurrentX: Int? = null
        private var mCurrentY: Int? = null

        fun cancelFling() {
            LogUtil.d(msg = "Cancel Fling")
            mScroller.forceFinished(true)
        }

        fun fling(viewWidth: Int, viewHeight: Int, velocityX: Int, velocityY: Int) {
            val rect = getDisplayRect()
            rect?.let {
                val startX = (-it.left).roundToInt()
                val minX: Int
                val maxX: Int
                val minY: Int
                val maxY: Int
                if (viewWidth < it.width()) {
                    minX = 0
                    maxX = (rect.width() - viewWidth).roundToInt()
                } else {
                    minX = startX
                    maxX = startX
                }

                val startY = (-rect.top).roundToInt()
                if (viewHeight < rect.height()) {
                    minY = 0
                    maxY = (rect.height() - viewHeight).roundToInt()
                } else {
                    minY = startY;
                    maxY = startY
                }

                mCurrentX = startX
                mCurrentY = startY
                LogUtil.d(
                    msg = "fling. StartX:" + startX + " StartY:" + startY
                            + " MaxX:" + maxX + " MaxY:" + maxY
                );
                if (startX != maxX || startY != maxY) {
                    mScroller.fling(
                        startX,
                        startY,
                        velocityX,
                        velocityY,
                        minX,
                        maxX,
                        minY,
                        maxY,
                        0,
                        0
                    )
                }

            }

        }

        override fun run() {
            if (mScroller.isFinished()) {
                return
            }
            val imageView = getImageView()
            if (imageView != null && mScroller.computeScrollOffset()) {
                val newX = mScroller.getCurrX()
                val newY = mScroller.getCurrY()
                LogUtil.d(
                    msg =
                    "fling run(). CurrentX:" + mCurrentX + " CurrentY:"
                            + mCurrentY + " NewX:" + newX + " NewY:"
                            + newY
                );
                mSuppMatrix.postTranslate(
                    (mCurrentX ?: 0 - newX).toFloat(),
                    (mCurrentY ?: 0 - newY).toFloat()
                )
                setImageViewMatrix(getDrawMatrix())

                mCurrentX = newX
                mCurrentY = newY
                Compat.postOnAnimation(imageView, this)
            }
        }
    }

    fun getDrawMatrix(): Matrix {
        mDrawMatrix.set(mBaseMatrix)
        mDrawMatrix.postConcat(mSuppMatrix)
        return mDrawMatrix
    }

    private fun getDisplayRect(matrix: Matrix): RectF? {
        val imageView = getImageView()
        imageView?.let { iv ->
            val d = iv.drawable
            d?.let {
                mDisplayRect.set(0F, 0F, it.intrinsicWidth.toFloat(), it.intrinsicHeight.toFloat())
                matrix.mapRect(mDisplayRect)
                return mDisplayRect
            }
        }
        return null
    }

    private fun setImageViewMatrix(matrix: Matrix) {
        val imageView = getImageView()
        imageView?.let { iv ->
            checkImageViewScaleType()
            iv.imageMatrix = matrix

            if (mMatrixChangeListener != null) {
                val displayRect = getDisplayRect(matrix);
                displayRect?.let { rectF ->
                    mMatrixChangeListener?.onMatrixChanged(rectF)
                }
            }
        }
    }

    private fun checkImageViewScaleType() {
        val imageView = getImageView()
        if (imageView != null && imageView !is IPhotoView) {
            if (!ImageView.ScaleType.MATRIX.equals(imageView.scaleType)) {
                throw IllegalStateException(
                    "The ImageView's ScaleType has been changed since attaching a PhotoViewAttacher"
                );
            }
        }
    }

}