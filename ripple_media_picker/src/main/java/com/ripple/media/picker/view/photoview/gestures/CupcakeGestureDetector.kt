package com.ripple.media.picker.view.photoview.gestures

import android.content.Context
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Author: fanyafeng
 * Data: 2020/4/29 16:35
 * Email: fanyafeng@live.cn
 * Description:
 */
open class CupcakeGestureDetector(private val context: Context) : GestureDetector {
    protected var mListener: OnGestureListener? = null
    var mLastTouchX: Float? = null
    var mLastTouchY: Float? = null
    val mTouchSlop: Float
    val mMinimumVelocity: Float

    private var mVelocityTracker: VelocityTracker? = null
    private var mIsDragging: Boolean? = null

    open fun getActiveX(event: MotionEvent) = event.x

    open fun getActiveY(event: MotionEvent) = event.y

    init {
        val configuration = ViewConfiguration.get(context)
        mMinimumVelocity = configuration.scaledMinimumFlingVelocity.toFloat()
        mTouchSlop = configuration.scaledTouchSlop.toFloat()
    }

    override fun isScaling() = false

    override fun isDragging() = mIsDragging ?: false

    override fun setOnGestureListener(listener: OnGestureListener) {
        this.mListener = listener
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mVelocityTracker = VelocityTracker.obtain()
                mVelocityTracker?.addMovement(event)

                mLastTouchX = getActiveX(event)
                mLastTouchY = getActiveY(event)
                mIsDragging = false
            }
            MotionEvent.ACTION_MOVE -> {
                val x = getActiveX(event)
                val y = getActiveY(event)
                val dx: Float = x - (mLastTouchX ?: 0F)
                val dy = x - (mLastTouchY ?: 0F)

                if (mIsDragging != true) {
                    mIsDragging = sqrt((dx * dx) + (dy * dy)) >= mTouchSlop
                }

                if (mIsDragging == true) {
                    mListener?.onDrag(dx, dy)
                    mLastTouchX = x
                    mLastTouchY = y

                    mVelocityTracker?.addMovement(event)
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                if (mVelocityTracker != null) {
                    mVelocityTracker?.recycle()
                    mVelocityTracker = null
                }
            }
            MotionEvent.ACTION_UP -> {
                if (mIsDragging!!) {
                    if (mVelocityTracker != null) {
                        mLastTouchX = getActiveX(event)
                        mLastTouchY = getActiveY(event)

                        // Compute velocity within the last 1000ms
                        mVelocityTracker!!.addMovement(event)
                        mVelocityTracker!!.computeCurrentVelocity(1000)
                        val vX = mVelocityTracker!!.xVelocity
                        val vY = mVelocityTracker!!
                            .yVelocity

                        // If the velocity is greater than minVelocity, call
                        // listener
                        if (abs(vX).coerceAtLeast(abs(vY)) >= mMinimumVelocity
                        ) {
                            mListener!!.onFling(
                                mLastTouchX!!, mLastTouchY!!, -vX,
                                -vY
                            )
                        }
                    }
                }

                if (null != mVelocityTracker) {
                    mVelocityTracker!!.recycle()
                    mVelocityTracker = null
                }
            }
        }

        return true
    }

}