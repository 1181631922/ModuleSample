package com.ripple.log.tpyeextend

import com.ripple.log.RippleLog
import com.ripple.log.extend.getPrintLogHeaderWithMsg

/**
 * Author: fanyafeng
 * Data: 2020/7/9 14:11
 * Email: fanyafeng@live.cn
 * Description: 常用数据类型的log扩展类
 */
@JvmOverloads
fun <T> T.toLogV(tag: String = RippleLog.getInstance().tag): T {
    com.ripple.log.extend.logV(toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.toLogVWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    com.ripple.log.extend.logV(
        stringBuffer.toString(),
        tag
    )
    return this
}

@JvmOverloads
fun <T> T.toLogD(tag: String = RippleLog.getInstance().tag): T {
    com.ripple.log.extend.logD(toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.toLogDWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    com.ripple.log.extend.logD(
        stringBuffer.toString(),
        tag
    )
    return this
}

@JvmOverloads
fun <T> T.toLogI(tag: String = RippleLog.getInstance().tag): T {
    com.ripple.log.extend.logI(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.toLogIWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    com.ripple.log.extend.logI(
        stringBuffer.toString(),
        tag
    )
    return this
}

@JvmOverloads
fun <T> T.toLogW(tag: String = RippleLog.getInstance().tag): T {
    com.ripple.log.extend.logW(this.toString(), tag = tag)
    return this
}

@JvmOverloads
fun <T> T.toLogWWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    com.ripple.log.extend.logW(
        stringBuffer.toString(),
        tag = tag
    )
    return this
}

@JvmOverloads
fun <T> T.toLogE(tag: String = RippleLog.getInstance().tag): T {
    com.ripple.log.extend.logE(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.toLogEWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    com.ripple.log.extend.logE(
        stringBuffer.toString(),
        tag
    )
    return this
}

