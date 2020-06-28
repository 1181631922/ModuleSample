package com.ripple.dialog.widget

/**
 * Author： fanyafeng
 * Date： 2019-12-27 16:31
 * Email: fanyafeng@live.cn
 */
interface IRippleDialog : IRippleBaseDialog {

    /**
     * 隐藏dialog
     */
    fun hide()

    /**
     * 取消dialog
     */
    fun cancel()

    /**
     * 是否拦截返回事件
     * 此处方法将会被新的api代替
     */
    @Deprecated(message = "此方法将会被替换", replaceWith = ReplaceWith("interceptBackPressed"))
    fun setResponseBackPress(isResponseBackPress: Boolean)
}