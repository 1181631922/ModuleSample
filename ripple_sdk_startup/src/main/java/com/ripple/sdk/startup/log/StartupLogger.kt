package com.ripple.sdk.startup.log

import android.util.Log

/**
 * Author: fanyafeng
 * Date: 2020/9/25 10:11
 * Email: fanyafeng@live.cn
 * Description:
 */
/**
 * @hide
 */
internal object StartupLogger {
    /**
     * The log tag.
     */
    private const val TAG = "StartupLogger"

    /**
     * To enable logging set this to true.
     */
    const val DEBUG = false

    /**
     * Info level logging.
     *
     * @param message The message being logged
     */
    fun i(message: String) {
        Log.i(TAG, message)
    }

    /**
     * Error level logging
     *
     * @param message   The message being logged
     * @param throwable The optional [Throwable] exception
     */
    fun e(message: String, throwable: Throwable?) {
        Log.e(TAG, message, throwable)
    }
}