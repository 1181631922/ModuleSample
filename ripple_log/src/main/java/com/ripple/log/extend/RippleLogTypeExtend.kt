package com.ripple.log.extend

import com.ripple.log.RippleLog
import java.lang.StringBuilder

/**
 * Author: fanyafeng
 * Data: 2020/7/9 14:11
 * Email: fanyafeng@live.cn
 * Description: 常用数据类型的log扩展类
 */
@JvmOverloads
fun <T> T.logV(tag: String = RippleLog.getInstance().tag): T {
    logV(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.logVWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logV(
        stringBuffer.toString(),
        tag
    )
    return this
}

@JvmOverloads
fun <T> T.logD(tag: String = RippleLog.getInstance().tag): T {
    logD(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.logDWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logD(
        stringBuffer.toString(),
        tag
    )
    return this
}

@JvmOverloads
fun <T> T.logI(tag: String = RippleLog.getInstance().tag): T {
    logI(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.logIWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logI(
        stringBuffer.toString(),
        tag
    )
    return this
}

@JvmOverloads
fun <T> T.logW(tag: String = RippleLog.getInstance().tag): T {
    logW(this.toString(), tag = tag)
    return this
}

@JvmOverloads
fun <T> T.logWWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logW(
        stringBuffer.toString(),
        tag = tag
    )
    return this
}

@JvmOverloads
fun <T> T.logE(tag: String = RippleLog.getInstance().tag): T {
    logE(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.logEWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logE(
        stringBuffer.toString(),
        tag
    )
    return this
}

