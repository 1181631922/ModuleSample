package com.ripple.tool.judge


/**
 * Author: fanyafeng
 * Data: 2020/6/23 16:58
 * Email: fanyafeng@live.cn
 * Description:
 */
object Preconditions {
    fun <T> checkNotNullRipple(reference: T?, errorMessage: Any = "引用对象为空"): T {
        if (reference == null) {
            throw NullPointerException(errorMessage.toString())
        }
        return reference
    }


    fun <T> checkArgumentRipple(expression: Boolean, errorMessage: Any) {
        if (!expression) {
            throw IllegalArgumentException(errorMessage.toString())
        }
    }
}