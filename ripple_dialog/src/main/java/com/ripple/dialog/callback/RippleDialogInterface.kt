package com.ripple.dialog.callback

import android.content.Intent
import android.view.KeyEvent


/**
 * Author: fanyafeng
 * Data: 2020/6/23 18:36
 * Email: fanyafeng@live.cn
 * Description: 定义统一的dialog的回调接口
 *
 * 这里着重说明一下为什么要自定义接口而不用系统定义好的
 * 类似收缩权限，达到在这个框架内可控
 * 另一个就是有可能某个api之后官方废弃
 */
interface RippleDialogInterface {


    /**
     * dialog消失回调
     */
    interface OnDismissListener {
        fun onDismiss()
    }

    /**
     * 用户点击返回键回调
     */
    interface OnBackPressListener {
        fun onBackPress()
    }

    /**
     * 用户点击回调
     */
    interface OnKeyDownListener {
        fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean
    }

    /**
     * activity result回调
     */
    interface OnActivityResult {
        fun onActivityResult(requestCode: Int?, resultCode: Int?, data: Intent?)
    }
}