@file:JvmName("DMDialogKT")

package com.ripple.dialog.extend

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.ripple.dialog.R
import com.ripple.dialog.callback.RippleDialogInterface
import com.ripple.dialog.config.RippleDialogConfig
import com.ripple.dialog.widget.impl.RippleDialog
import com.ripple.tool.kttypelians.SuccessLambda

/**
 * Author： fanyafeng
 * Date： 2020-01-02 09:55
 * Email: fanyafeng@live.cn
 * 打包，tinker，webview
 */

@JvmOverloads
fun View.showBottomDialog(
    layoutId: Int,
    dismissOutside: Boolean = true,
    onDismiss: SuccessLambda<RippleDialog> = null
): Pair<View, RippleDialog> {
    val mContext = context
    return mContext.showBottomDialog(layoutId, dismissOutside, onDismiss)
}

@JvmOverloads
fun Activity.showBottomDialog(
    layoutId: Int,
    dismissOutside: Boolean = true,
    onDismiss: SuccessLambda<RippleDialog> = null
): Pair<View, RippleDialog> {
    val context = baseContext
    return context.showBottomDialog(layoutId, dismissOutside, onDismiss)
}

@JvmOverloads
fun Context.showBottomDialog(
    layoutId: Int,
    dismissOutside: Boolean = true,
    onDismiss: SuccessLambda<RippleDialog> = null
): Pair<View, RippleDialog> {
    val inflating = LayoutInflater.from(this).inflate(layoutId, null)
    val showDialog = showBottomDialog(inflating, dismissOutside, onDismiss)
    return Pair(inflating, showDialog)
}

@JvmOverloads
fun Context.showBottomDialog(
    contentView: View,
    dismissOutside: Boolean = true,
    onDismiss: SuccessLambda<RippleDialog> = null
): RippleDialog {
    val dialog = RippleDialog(
        RippleDialogConfig.Builder()
            .setContext(this)
            .setGravity(Gravity.BOTTOM)
            .setContentView(contentView)
            .setCancel(dismissOutside)
            .setThemeResId(R.style.RippleDialogStyle)
            .setThemeResId(R.style.RippleMenuAnimation)
            .build()
    )
    if (onDismiss != null) {
        dialog.setOnDismissListener(object : RippleDialogInterface.OnDismissListener {
            override fun onDismiss() {
                onDismiss.invoke(dialog)
            }
        })
    }
    dialog.show()
    return dialog
}