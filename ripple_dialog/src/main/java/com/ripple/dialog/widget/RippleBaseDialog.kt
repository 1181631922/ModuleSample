package com.ripple.dialog.widget

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import com.ripple.dialog.config.RippleDialogConfig
import com.ripple.dialog.util.Preconditions

/**
 * Author： fanyafeng
 * Date： 2019-12-27 16:35
 * Email: fanyafeng@live.cn
 */
class RippleBaseDialog(private var dialogConfig: RippleDialogConfig) : Dialog(dialogConfig.context!!, dialogConfig.themeResId!!) {
    var isResponseBackPress: Boolean = false

    private var onBackPressListener: OnBackPressListener? = null

    fun setOnBackPressListener(onBackPressListener: OnBackPressListener) {
        this.onBackPressListener = onBackPressListener
    }

    interface OnBackPressListener {
        fun onBackPress()
    }

    private var onKeyDownListener: OnKeyDownListener? = null

    fun setOnKeyDownListener(onKeyDownListener: OnKeyDownListener) {
        this.onKeyDownListener = onKeyDownListener
    }

    interface OnKeyDownListener {
        fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Preconditions.checkNotNull(dialogConfig, "dialog dialogConfig is null")
        Preconditions.checkNotNull(dialogConfig.contentView, "dialog contentView is null")
        dialogConfig.contentView?.let { this.setContentView(it) }
        val windowManager = this.window?.attributes
        windowManager?.width = ViewGroup.LayoutParams.MATCH_PARENT
        windowManager?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        if (dialogConfig.gravity != null) {
            this.window?.setGravity(dialogConfig.gravity!!)
        }
        if (dialogConfig.animation != null) {
            this.window?.setWindowAnimations(dialogConfig.animation!!)
        }
        this@RippleBaseDialog.setCanceledOnTouchOutside(dialogConfig.isCancel)
    }

    override fun onBackPressed() {
        if (isResponseBackPress) {
            onBackPressListener?.onBackPress()
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (onKeyDownListener != null) {
            return onKeyDownListener!!.onKeyDown(keyCode, event)
        }
        return super.onKeyDown(keyCode, event)
    }
}