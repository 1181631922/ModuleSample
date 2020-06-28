package com.ripple.dialog.widget.impl

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.ripple.dialog.R
import com.ripple.dialog.callback.RippleDialogInterface
import com.ripple.dialog.config.RippleDialogConfig
import com.ripple.dialog.widget.IRippleBaseDialog
import com.ripple.dialog.widget.IRippleDialogFragment
import com.ripple.dialog.widget.RippleBaseDialog
import com.ripple.dialog.widget.RippleBaseDialogFragment


/**
 * Author: fanyafeng
 * Data: 2020/6/23 17:20
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleDialogFragment : IRippleDialogFragment {

    private var dialogConfig: RippleDialogConfig? = null
    private var baseDialogFragment: RippleBaseDialogFragment? = null
    private var contentView: View? = null
    private var context: Context? = null

    constructor(context: Context, view: View) {
        this.context = context
        this.dialogConfig = RippleDialogConfig.Builder()
            .setContext(context)
            .setThemeResId(R.style.RippleDialogStyle)
            .setContentView(view)
            .setCancel(true)
            .build()
        baseDialogFragment = RippleBaseDialogFragment(dialogConfig!!)
        contentView = dialogConfig!!.contentView
    }

    constructor(dialogConfig: RippleDialogConfig) {
        this.dialogConfig = dialogConfig
        baseDialogFragment = RippleBaseDialogFragment(this.dialogConfig!!)
        contentView = dialogConfig.contentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun getId(): Int {
        return 0
    }

    override fun show() {
        baseDialogFragment?.show((context as FragmentActivity).supportFragmentManager, "tag")
    }

    override fun showCenter() {
        dialogConfig?.gravity = Gravity.CENTER
        show()
    }

    override fun showBottom() {
        dialogConfig?.gravity = Gravity.BOTTOM
        show()
    }


    override fun dismiss() {
        baseDialogFragment?.dismiss()
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

    override fun setOnDismissListener(listener: RippleDialogInterface.OnDismissListener?) {
    }


    override fun isShowing(): Boolean {
        return baseDialogFragment?.isVisible ?: false
    }

    override fun interceptBackPressed(interceptBackPressed: Boolean) {
    }

    override fun setOnBackPressListener(listener: RippleDialogInterface.OnBackPressListener?) {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }

}