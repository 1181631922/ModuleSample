package com.ripple.dialog.widget

import android.view.KeyEvent
import com.ripple.dialog.callback.RippleDialogInterface


/**
 * Author: fanyafeng
 * Data: 2020/6/23 17:44
 * Email: fanyafeng@live.cn
 * Description: 此类为所有dialog都具有的行为
 *
 * 因为Dialog和DialogFragment的行为是有区别的
 * 但是只要是dialog都会有的行为便封装在此接口中
 * 一方面为了调用方式的统一
 * 另一方面是为了将用户常用的抽象到此处
 *
 * eg1:此为dialog在中间显示
 *
 *  |---------------------------|
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |     ----------------      |
 *  |     |              |      |
 *  |     |    dialog    |      |
 *  |     |              |      |
 *  |     ----------------      |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |---------------------------|
 *
 *
 *  eg2:此为dialog在底部显示
 *
 *  |---------------------------|
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *  |     ----------------      |
 *  |     |              |      |
 *  |     |    dialog    |      |
 *  |     |              |      |
 *  |     ----------------      |
 *  |---------------------------|
 *
 *
 *  eg3:还可以通过自定义view的方式进行固定位置的显示
 *
 */
interface IRippleBaseDialog {


    /**
     * dialog最基本的行为就是显示到window上
     */
    fun show()

    /**
     * 为了方便使用者的调用
     * 扩展一下两个方法
     * 在窗口的中间显示
     */
    fun showCenter()

    /**
     * 在窗口的底部显示
     */
    fun showBottom()

    /**
     * dialog有显示肯定会有消失
     * 显示和消失成对出现
     */
    fun dismiss()

    /**
     * 设置dialog显示位置
     */
    fun setGravity(gravity: Int): IRippleBaseDialog

    /**
     * 设置点击dialog外围是否消失
     */
    fun setCancel(isCancel: Boolean): IRippleBaseDialog

    /**
     * 设置dialog消失和出现动画
     */
    fun setAnimation(animation: Int): IRippleBaseDialog

    /**
     * 有可能使用者还需要监听dialog的消失状态
     */
    fun setOnDismissListener(listener: RippleDialogInterface.OnDismissListener?)

    /**
     * 用户有时需要去判断dialog是否正在显示
     * 用来做一些操作
     */
    fun isShowing(): Boolean

    /**
     * 是否允许用户拦截返回键
     * 但是因为拦截返回键是常用的这个将会单独拿出来处理
     *
     * 默认为false 不会拦截用户返回
     *
     * 如果拦截则dialog不会消失，需要强制用户处理某些行为才可以消失
     * 这也是拦截返回键的意义所在
     */
    fun interceptBackPressed(interceptBackPressed: Boolean)

    /**
     * 这里还需要监听用户的返回键
     * 很有可能开发者需要在此处进行相应的操作
     * 监听返回键默认拦截返回操作
     */
    fun setOnBackPressListener(listener: RippleDialogInterface.OnBackPressListener?)


}