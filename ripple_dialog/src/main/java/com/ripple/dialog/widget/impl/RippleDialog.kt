package com.ripple.dialog.widget.impl

import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import com.ripple.dialog.R
import com.ripple.dialog.callback.RippleDialogInterface
import com.ripple.dialog.config.RippleDialogConfig
import com.ripple.dialog.widget.IRippleBaseDialog
import com.ripple.dialog.widget.RippleBaseDialog
import com.ripple.dialog.widget.IRippleDialog
import com.ripple.tool.kttypelians.PairReturnLambda

/**
 * Author： fanyafeng
 * Date： 2019-12-27 16:45
 * Email: fanyafeng@live.cn
 */
class RippleDialog : IRippleDialog {
    private var baseDialog: RippleBaseDialog? = null
    private var dialogConfig: RippleDialogConfig? = null

    private var onBackPressListener: RippleDialogInterface.OnBackPressListener? = null

    var contentView: View? = null
        private set(value) {
            field = value
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

    override fun setGravity(gravity: Int): IRippleBaseDialog {
        dialogConfig?.gravity = gravity
        return this
    }

    override fun setCancel(isCancel: Boolean): IRippleBaseDialog {
        dialogConfig?.isCancel = isCancel
        return this
    }

    override fun setAnimation(animation: Int): IRippleBaseDialog {
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

    override fun showCenter() {
        dialogConfig?.gravity = Gravity.CENTER
        show()
    }

    override fun showBottom() {
        dialogConfig?.gravity = Gravity.BOTTOM
        dialogConfig?.animation = R.style.RippleMenuAnimation
        show()
    }

    override fun interceptBackPressed(interceptBackPressed: Boolean) {
        if (interceptBackPressed) {
            baseDialog?.onBackPressListener = {
                onBackPressListener?.let {
                    setOnBackPressListener(it)
                }
            }
        }
    }

    override fun setResponseBackPress(isResponseBackPress: Boolean) {
        interceptBackPressed(isResponseBackPress)
    }

    override fun setOnBackPressListener(listener: RippleDialogInterface.OnBackPressListener?) {
        onBackPressListener = listener
        baseDialog?.onBackPressListener = {
            onBackPressListener?.onBackPress()
        }
    }

    override fun setOnDismissListener(listener: RippleDialogInterface.OnDismissListener?) {
        baseDialog?.setOnDismissListener {
            listener?.onDismiss()
        }
    }

}