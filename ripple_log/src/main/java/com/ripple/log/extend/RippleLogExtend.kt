package com.ripple.log.extend

import com.ripple.log.RippleLog

/**
 * Author: fanyafeng
 * Data: 2020/7/8 19:45
 * Email: fanyafeng@live.cn
 * Description: Log扩展工具类，主要用于KT类调用
 */

/**
 * 以下为Log.v的工具类，等同调用：
 * Log.v()
 * 因为tag经常是固定的并且非空，而且一般意义不太大
 * 把tag放在第二个参数中，这样就能更好地利用msg这个参数
 */
@JvmOverloads
fun logV(msg: String, tag: String = RippleLog.getInstance().tag, tr: Throwable? = null) {
    if (tr == null) {
        RippleLog.getInstance().log?.v(tag, msg)
    } else {
        RippleLog.getInstance().log?.v(tag, msg, tr)
    }
}

/**
 * 以下为Log.d的工具类，等同调用：
 * Log.d()
 * 因为tag经常是固定的并且非空，而且一般意义不太大
 * 把tag放在第二个参数中，这样就能更好地利用msg这个参数
 */
@JvmOverloads
fun logD(msg: String, tag: String = RippleLog.getInstance().tag, tr: Throwable? = null) {
    if (tr == null) {
        RippleLog.getInstance().log?.d(tag, msg)
    } else {
        RippleLog.getInstance().log?.d(tag, msg, tr)
    }
}

@JvmOverloads
fun logI(msg: String, tag: String = RippleLog.getInstance().tag, tr: Throwable? = null) {
    if (tr == null) {
        RippleLog.getInstance().log?.i(tag, msg)
    } else {
        RippleLog.getInstance().log?.i(tag, msg, tr)
    }
}

@JvmOverloads
fun logW(msg: String? = null, tr: Throwable? = null, tag: String = RippleLog.getInstance().tag) {
    if (msg != null) {
        if (tr != null) {
            RippleLog.getInstance().log?.w(tag, msg, tr)
        } else {
            RippleLog.getInstance().log?.w(tag, msg)
        }
    } else {
        if (tr != null) {
            RippleLog.getInstance().log?.w(tag, tr)
        } else {
            logE("msg和Throwable不能同时为空")
        }
    }
}

@JvmOverloads
fun logE(msg: String, tag: String = RippleLog.getInstance().tag, tr: Throwable? = null) {
    if (tr == null) {
        RippleLog.getInstance().log?.e(tag, msg)
    } else {
        RippleLog.getInstance().log?.e(tag, msg, tr)
    }
}




