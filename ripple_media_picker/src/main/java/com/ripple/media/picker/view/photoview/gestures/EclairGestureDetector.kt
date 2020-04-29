package com.ripple.media.picker.view.photoview.gestures

import android.annotation.TargetApi
import android.content.Context
import android.view.MotionEvent
import com.ripple.media.picker.view.photoview.impl.Compat.getPointerIndex


/**
 * Author: fanyafeng
 * Data: 2020/4/29 17:03
 * Email: fanyafeng@live.cn
 * Description:
 */
@TargetApi(8)
open class EclairGestureDetector(private val context: Context) : CupcakeGestureDetector(context) {
    companion object {
        const val INVALID_POINTER_ID = -1
    }

    private var mActivePointerId = MotionEvent.INVALID_POINTER_ID
    private var mActivePointerIndex = 0

    override fun getActiveX(ev: MotionEvent): Float {
        return try {
            ev.getX(mActivePointerIndex)
        } catch (e: Exception) {
            ev.x
        }
    }

    override fun getActiveY(ev: MotionEvent): Float {
        return try {
            ev.getY(mActivePointerIndex)
        } catch (e: Exception) {
            ev.y
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> mActivePointerId = event.getPointerId(0)
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> mActivePointerId =
                MotionEvent.INVALID_POINTER_ID
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = getPointerIndex(event.action)
                val pointerId = event.getPointerId(pointerIndex)
                if (pointerId == mActivePointerId) {
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    mActivePointerId = event.getPointerId(newPointerIndex)
                    mLastTouchX = event.getX(newPointerIndex)
                    mLastTouchY = event.getY(newPointerIndex)
                }
            }
        }
        mActivePointerIndex = event
            .findPointerIndex(if (mActivePointerId != MotionEvent.INVALID_POINTER_ID) mActivePointerId else 0)
        return super.onTouchEvent(event)
    }
}