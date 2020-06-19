package com.ripple.tool.page

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Author: fanyafeng
 * Data: 2020/6/19 15:05
 * Email: fanyafeng@live.cn
 * Description:
 */
object PageUtil {
    /**
     * 隐藏键盘
     */
    fun hideSoftInput(context: Context?, view: View?) {
        view?.postDelayed({
            context?.let {
                val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
                }
            }
        }, 100)
    }
}