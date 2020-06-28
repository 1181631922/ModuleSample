package com.ripple.dialog.widget.impl

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.ripple.tool.judge.checkNotNullRipple

/**
 * Author： fanyafeng
 * Date： 2019-12-27 17:02
 * Email: fanyafeng@live.cn
 */
object RippleToast {
    var toast: Toast? = null

    const val GRAVITY_DEFAULT = -100

    /**
     *
     * context:上下文对象，非内部匿名工具类可直接使用this
     * charSequence:和用户交互的文字
     * view:自定义toast的view，如果使用自定义view，charSequence失效，可以设置为空串
     * duration:toast显示时间
     * gravity:toast显示位置
     * xOffset:toast x轴偏移量
     * yOffset:toast y轴偏移量
     */
    @JvmStatic
    @JvmOverloads
    fun show(context: Context, charSequence: CharSequence = "", view: View? = null, duration: Int = Toast.LENGTH_SHORT, gravity: Int = GRAVITY_DEFAULT, xOffset: Int = 0, yOffset: Int = 0) {
        toast?.cancel()
        checkNotNullRipple(charSequence, "charSequence is null")
        toast = Toast.makeText(context, charSequence, duration)
        if (gravity != GRAVITY_DEFAULT) {
            toast!!.setGravity(gravity, xOffset, yOffset)
        }
        if (view != null) {
            toast!!.view = view
        }
        toast!!.show()
    }

    @JvmStatic
    fun show(context: Context, view: View?) {
        show(context, "", view)
    }

    @JvmStatic
    fun showCenter(context: Context, charSequence: CharSequence = "") {
        show(context, charSequence, null, Toast.LENGTH_SHORT, Gravity.CENTER)
    }

}