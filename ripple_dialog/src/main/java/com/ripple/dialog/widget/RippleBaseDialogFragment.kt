package com.ripple.dialog.widget

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.ripple.dialog.callback.RippleDialogInterface
import com.ripple.dialog.config.RippleDialogConfig
import com.ripple.tool.judge.checkNotNullRipple
import com.ripple.tool.kttypelians.TripleLambda
import com.ripple.tool.kttypelians.UnitLambda


/**
 * Author: fanyafeng
 * Data: 2020/6/23 15:50
 * Email: fanyafeng@live.cn
 * Description: 封装系统的FragmentDialog
 *
 * 系统的dialog是不包含onActivityResult回调的
 * 但是，有时候又必须用到这个回调，虽然机会很少
 * 鉴于此情况，对FragmentDialog做进一步封装
 *
 */

class RippleBaseDialogFragment(private var dialogConfig: RippleDialogConfig) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (null != dialogConfig?.themeResId) {
            setStyle(DialogFragment.STYLE_NORMAL, dialogConfig?.themeResId!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return dialogConfig.contentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkNotNullRipple(dialogConfig, "dialog dialogConfig is null")
        checkNotNullRipple(dialogConfig.contentView, "dialog contentView is null")
        checkNotNullRipple(dialogConfig.context, "dialog context is null")
        val window = dialog?.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (dialogConfig.gravity != null) {
            window?.setGravity(dialogConfig.gravity!!)
        }
        if (dialogConfig.animation != null) {
            window?.setWindowAnimations(dialogConfig.animation!!)
        }
        dialog?.setCanceledOnTouchOutside(dialogConfig.isCancel)
        this.dialog?.setOnKeyListener { _, keyCode, event ->

            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                onBackPressListener?.invoke()
                true
            } else {
                false
            }
        }
    }

    var onBackPressListener: UnitLambda = null
    var onDismissListener: UnitLambda = null


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
    }

    var onActivityResult: TripleLambda<Int, Int, Intent?> = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResult?.invoke(requestCode, resultCode, data)
    }


}