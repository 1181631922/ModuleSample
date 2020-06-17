package com.ripple.dialog.widget

/**
 * Author： fanyafeng
 * Date： 2019-12-27 16:31
 * Email: fanyafeng@live.cn
 */
interface IRippleDialog {
    /**
     * 显示dialog
     */
    fun show()

    /**
     * 隐藏dialog
     */
    fun hide()

    /**
     * 消失dialog
     */
    fun dismiss()

    /**
     * 取消dialog
     */
    fun cancel()

    /**
     * dialog是否显示
     */
    fun isShowing(): Boolean

    /**
     * 是否拦截返回事件
     */
    fun setResponseBackPress(isResponseBackPress: Boolean)
}