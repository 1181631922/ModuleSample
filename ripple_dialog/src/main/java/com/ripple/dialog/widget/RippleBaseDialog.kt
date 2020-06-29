package com.ripple.dialog.widget

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import com.ripple.dialog.config.RippleDialogConfig
import com.ripple.tool.judge.checkNotNullRipple
import com.ripple.tool.kttypelians.PairReturnLambda
import com.ripple.tool.kttypelians.SuccessLambda

/**
 * Author： fanyafeng
 * Date： 2019-12-27 16:35
 * Email: fanyafeng@live.cn
 */
class RippleBaseDialog(private var dialogConfig: RippleDialogConfig) :
    Dialog(dialogConfig.context!!, dialogConfig.themeResId!!) {

    /**
     * 拦截返回键
     */
    var onBackPressListener: SuccessLambda<Unit> = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotNullRipple(dialogConfig, "dialog dialogConfig is null")
        checkNotNullRipple(dialogConfig.contentView, "dialog contentView is null")
        dialogConfig.contentView?.let { this.setContentView(it) }
        val window = this.window
        val attributes = window?.attributes
        attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
        attributes?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        if (dialogConfig.gravity != null) {
            this.window?.setGravity(dialogConfig.gravity!!)
        }
        if (dialogConfig.animation != null) {
            this.window?.setWindowAnimations(dialogConfig.animation!!)
        }
        this@RippleBaseDialog.setCanceledOnTouchOutside(dialogConfig.isCancel)
    }

    override fun onBackPressed() {
        if (onBackPressListener != null) {
            onBackPressListener?.invoke(Unit)
        } else {
            super.onBackPressed()
        }
    }
}