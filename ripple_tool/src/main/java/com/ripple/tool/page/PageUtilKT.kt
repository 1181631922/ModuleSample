package com.ripple.tool.page

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Author: fanyafeng
 * Data: 2020/6/19 15:07
 * Email: fanyafeng@live.cn
 * Description: 页面通用Util工具类
 */

/**
 * 隐藏软键盘
 */
fun View.hideSoftInput(context: Context?) {
    this.postDelayed({
        context?.let {
            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.hideSoftInputFromWindow(this.applicationWindowToken, 0)
            }
        }
    }, 100)
}