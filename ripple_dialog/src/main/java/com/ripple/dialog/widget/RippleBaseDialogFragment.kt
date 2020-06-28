package com.ripple.dialog.widget

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ripple.dialog.config.RippleDialogConfig


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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return dialogConfig.contentView
    }
}