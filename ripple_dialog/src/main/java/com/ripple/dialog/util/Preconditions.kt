package com.ripple.dialog.util

/**
 * Author： fanyafeng
 * Date： 2019-12-27 16:26
 * Email: fanyafeng@live.cn
 */
internal object Preconditions {
    @JvmStatic
    fun <T> checkNotNull(reference: T?, errorMessage: Any): T {
        if (reference == null) {
            throw NullPointerException(errorMessage.toString())
        }
        return reference
    }

}