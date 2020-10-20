package com.ripple.sdk.startup.exception

import androidx.annotation.RestrictTo

/**
 * Author: fanyafeng
 * Date: 2020/9/25 10:10
 * Email: fanyafeng@live.cn
 * Description:
 */
/**
 * The Runtime Exception thrown by the android.startup library.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
class StartupException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(throwable: Throwable) : super(throwable)
    constructor(message: String, throwable: Throwable) : super(message, throwable)
}