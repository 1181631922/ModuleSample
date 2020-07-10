package com.ripple.log.impl

import android.util.Log
import com.ripple.log.ILog
import com.ripple.log.RippleLog


/**
 * Author: fanyafeng
 * Data: 2020/7/8 19:28
 * Email: fanyafeng@live.cn
 * Description: ILog的实现类
 * 内部实现，方便RippleLog的使用
 */
class LogImpl : ILog {

    private val flag: String = LogImpl::class.java.simpleName + ".kt"
    private val ripplePrintLogExtendFlag = "RipplePrintLogExtend.kt"
    private val rippleLogExtendFLag = "RippleLogExtend.kt"
    private val rippleLogTypeExtendFlag = "RippleLogTypeExtend.kt"

    override fun v(tag: String, msg: String) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printV) {
                Log.v(tag, msg)
            }
        }
    }

    override fun v(tag: String, msg: String, tr: Throwable) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printV) {
                Log.v(tag, msg, tr)
            }
        }

    }

    override fun d(tag: String, msg: String) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printD) {
                Log.d(tag, msg)
            }
        }

    }

    override fun d(tag: String, msg: String, tr: Throwable) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printD) {
                Log.d(tag, msg, tr)
            }
        }

    }

    override fun i(tag: String, msg: String) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printI) {
                Log.i(tag, msg)
            }
        }

    }

    override fun i(tag: String, msg: String, tr: Throwable) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printI) {
                Log.i(tag, msg, tr)
            }
        }

    }

    override fun w(tag: String, msg: String) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printW) {
                Log.w(tag, msg)
            }
        }

    }

    override fun w(tag: String, tr: Throwable) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printW) {
                Log.w(tag, tr)
            }
        }

    }

    override fun w(tag: String, msg: String, tr: Throwable) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printW) {
                Log.w(tag, msg, tr)
            }
        }

    }

    override fun e(tag: String, msg: String) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printE) {
                Log.e(tag, msg)
            }
        }

    }

    override fun e(tag: String, msg: String, tr: Throwable) {
        if (RippleLog.getInstance().printAll) {
            if (RippleLog.getInstance().printE) {
                Log.e(tag, msg, tr)
            }
        }

    }

    override fun getStackTraceString(tr: Throwable): String {
        return Log.getStackTraceString(tr)
    }

    override fun getPrintLogMethodName(): String {
        return getStackTraceElement().methodName
    }

    override fun getPrintLogLineNumber(): Int {
        return getStackTraceElement().lineNumber
    }

    override fun getPrintLogFileName(): String {
        /**
         * 一般只是java的话用
         * [getStackTraceElement().className]
         * 就可以了，但是因为需要兼容kotlin
         * 这时候就需要使用fileName
         * 否则只能在java类中可以打印出可跳转的log不能在kotlin类中使用
         */
        return getStackTraceElement().fileName
    }

    /**
     * 看上面方法具体描述
     */
    override fun getPrintLogClassName(): String {
        return getStackTraceElement().className
    }

    private fun getStackTraceElement(): StackTraceElement {
        /**
         * 以下筛选去除工具类
         * 方式打印类打印到工具类中
         */
        return Throwable().stackTrace.first {
            flag != it.fileName
                    && ripplePrintLogExtendFlag != it.fileName
                    && rippleLogExtendFLag != it.fileName
                    && rippleLogTypeExtendFlag != it.fileName
        }
    }
}