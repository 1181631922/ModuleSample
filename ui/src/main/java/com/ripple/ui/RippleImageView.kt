package com.ripple.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

open class RippleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        private val TAG = RippleImageView::class.java.simpleName
    }

    interface OnRippleImageViewListener {
        fun onDetach()

        fun onAttach()

        fun onDraw(canvas: Canvas?)

        fun verifyDrawable(who: Drawable): Boolean

        fun onTouchEvent(event: MotionEvent?): Boolean
    }


    /**
     * 兼容Fresco的SimpleDrawView
     * https://www.fresco-cn.org/docs/writing-custom-views.html
     */
    private var onRippleImageViewListener: OnRippleImageViewListener? = null

    fun setOnRippleImageViewListener(onRippleImageViewListener: OnRippleImageViewListener?) {
        this.onRippleImageViewListener = onRippleImageViewListener
    }

    init {

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onRippleImageViewListener?.onDetach()
        drawable?.setVisible(false, false)
    }

    override fun onStartTemporaryDetach() {
        super.onStartTemporaryDetach()
        onRippleImageViewListener?.onDetach()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onRippleImageViewListener?.onAttach()
        drawable?.setVisible(visibility == View.VISIBLE, false)
    }

    override fun onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach()
        onRippleImageViewListener?.onAttach()
    }

    override fun verifyDrawable(dr: Drawable): Boolean {
        if (onRippleImageViewListener?.verifyDrawable(dr) == true) {
            return true
        }
        return super.verifyDrawable(dr)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        onRippleImageViewListener?.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return onRippleImageViewListener?.onTouchEvent(event) ?: false || super.onTouchEvent(event)
    }

}