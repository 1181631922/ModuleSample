package com.fanyafeng.modules.test

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log

/**
 * Author: fanyafeng
 * Data: 2020/7/9 10:23
 * Email: fanyafeng@live.cn
 * Description:
 */
object LogHelper {
    private var isDebuggable: Boolean = false

    private val flag = "${LogHelper::class.java.simpleName}.kt"

    @JvmStatic
    fun initDebuggable(context: Context) {
        context.applicationInfo?.let {
            isDebuggable = it.flags and ApplicationInfo.FLAG_DEBUGGABLE > 0
        }
    }

    private fun createLog(log: String): Pair<String, String> {
        val element: StackTraceElement = Throwable().stackTrace.first {
            flag != it.fileName
        }
        return Pair(
            "[${element.fileName}#${element.methodName}]",
            "(${element.fileName}:${element.lineNumber}) log:$log"
        )
    }

    @JvmStatic
    fun log(type: Int, msg: String) {

        if (!isDebuggable) return

        val pair = createLog(msg)
        val tag = pair.first
        val logMsg = pair.second
        when (type) {
            Log.VERBOSE -> Log.v(tag, logMsg)
            Log.DEBUG -> Log.d(tag, logMsg)
            Log.INFO -> Log.i(tag, logMsg)
            Log.WARN -> Log.w(tag, logMsg)
            Log.ERROR -> Log.e(tag, logMsg)
            else -> {
            }
        }
    }
}
