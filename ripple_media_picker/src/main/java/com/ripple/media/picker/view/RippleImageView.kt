package com.ripple.media.picker.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Author: fanyafeng
 * Data: 2020/4/22 09:59
 * Email: fanyafeng@live.cn
 * Description:
 */
open class RippleImageView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        private val TAG = RippleImageView::class.java
    }

    /**
     * 兼容fresco
     */
    interface OnGAImageViewListener {
        fun onDetach()

        fun onAttach()

        fun onDraw(canvas: Canvas?)

        fun verifyDrawable(who: Drawable): Boolean

        fun onTouchEvent(event: MotionEvent?): Boolean
    }

    private var onGAImageViewListener: OnGAImageViewListener? = null

    fun setOnGAImageViewListener(onGAImageViewListener: OnGAImageViewListener?) {
        this.onGAImageViewListener = onGAImageViewListener
    }

    init {

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onGAImageViewListener?.onDetach()
    }

    override fun onStartTemporaryDetach() {
        super.onStartTemporaryDetach()
        onGAImageViewListener?.onDetach()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onGAImageViewListener?.onAttach()
        drawable?.setVisible(visibility == View.VISIBLE, false)
    }

    override fun onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach()
        onGAImageViewListener?.onAttach()
    }

    override fun verifyDrawable(dr: Drawable): Boolean {
        if (onGAImageViewListener?.verifyDrawable(dr) == true) {
            return true
        }
        return super.verifyDrawable(dr)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        onGAImageViewListener?.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return onGAImageViewListener?.onTouchEvent(event) ?: false || super.onTouchEvent(event)
    }
}