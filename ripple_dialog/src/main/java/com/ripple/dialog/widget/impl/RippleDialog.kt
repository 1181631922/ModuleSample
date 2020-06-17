package com.ripple.dialog.widget.impl

import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import com.ripple.dialog.R
import com.ripple.dialog.config.RippleDialogConfig
import com.ripple.dialog.widget.RippleBaseDialog
import com.ripple.dialog.widget.IRippleDialog

/**
 * Author： fanyafeng
 * Date： 2019-12-27 16:45
 * Email: fanyafeng@live.cn
 */
class RippleDialog : IRippleDialog {
    private var baseDialog: RippleBaseDialog? = null
    private var dialogConfig: RippleDialogConfig? = null

    var contentView: View? = null
        private set(value) {
            field = value
        }

    private var onBackPressListener: OnBackPressListener? = null
        private set(value) {
            field = value
        }

    private var ondissmissListener: OnDismissListener? = null
        private set(value) {
            field = value
        }

    fun setOnBackpressListener(onBackPressListener: OnBackPressListener) {
        this.onBackPressListener = onBackPressListener
    }

    interface OnDismissListener {
        fun onDismiss()
    }

    interface OnBackPressListener {
        fun onBackPress()
    }

    private var onKeyDownListener: OnKeyDownListener? = null

    interface OnKeyDownListener {
        fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean
    }

    constructor(context: Context, view: View) {
        this.dialogConfig = RippleDialogConfig.Builder()
                .setContext(context)
                .setThemeResId(R.style.RippleDialogStyle)
                .setContentView(view)
                .setCancel(true)
                .build()
        baseDialog = RippleBaseDialog(dialogConfig!!)
        contentView = dialogConfig!!.contentView
    }

    constructor(dialogConfig: RippleDialogConfig) {
        this.dialogConfig = dialogConfig
        baseDialog = RippleBaseDialog(this.dialogConfig!!)
        contentView = dialogConfig.contentView
    }

    fun setGravity(gravity: Int): RippleDialog {
        dialogConfig?.gravity = gravity
        return this
    }

    fun setCancel(isCancel: Boolean): RippleDialog {
        dialogConfig?.isCancel = isCancel
        return this
    }

    fun setAnimation(animation: Int): RippleDialog {
        dialogConfig?.animation = animation
        return this
    }

    override fun show() {
        baseDialog?.show()
    }

    override fun hide() {
        baseDialog?.hide()
    }

    override fun dismiss() {
        baseDialog?.dismiss()
    }

    override fun cancel() {
        baseDialog?.cancel()
    }

    override fun isShowing(): Boolean {
        return baseDialog!!.isShowing
    }

    override fun setResponseBackPress(isResponseBackPress: Boolean) {
        baseDialog!!.isResponseBackPress = isResponseBackPress

        baseDialog!!.setOnBackPressListener(object : RippleBaseDialog.OnBackPressListener {
            override fun onBackPress() {
                onBackPressListener?.onBackPress()
            }
        })
    }

    fun showCenter() {
        dialogConfig?.gravity = Gravity.CENTER
        dialogConfig?.isCancel = false
        show()
    }

    fun showBottom() {
        dialogConfig?.gravity = Gravity.BOTTOM
        dialogConfig?.isCancel = true
        dialogConfig?.animation = R.style.RippleMenuAnimation
        show()
    }

    fun setOnKeyDownListener(onKeyDownListener: OnKeyDownListener) {
        this.onKeyDownListener = onKeyDownListener

        baseDialog!!.setOnKeyDownListener(object : RippleBaseDialog.OnKeyDownListener {
            override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
                return onKeyDownListener.onKeyDown(keyCode, event)
            }
        })
    }

    fun setOnDismissListener(onDismissListener: OnDismissListener) {
        this.ondissmissListener = onDismissListener
        baseDialog!!.setOnDismissListener {
            if (ondissmissListener != null) {
                ondissmissListener!!.onDismiss()
            }
        }
    }

}