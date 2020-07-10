package com.ripple.log.extend

import com.ripple.log.RippleLog

/**
 * Author: fanyafeng
 * Data: 2020/7/9 13:54
 * Email: fanyafeng@live.cn
 * Description: 日志打印关联信息辅助类
 */

/**
 * 打印堆栈信息
 */
fun getStackTraceString(tr: Throwable): String? {
    return RippleLog.getInstance().log?.getStackTraceString(tr)
}

/**
 * 打印当前log所在的方法名字
 */
fun getPrintLogMethodName(): String? {
    return RippleLog.getInstance().log?.getPrintLogMethodName()
}

/**
 * 打印当前log的行数
 */
fun getPrintLogLineNumber(): Int? {
    return RippleLog.getInstance().log?.getPrintLogLineNumber()
}

/**
 * 打印日志信息的文件名
 */
fun getPrintLogFileName(): String? {
    return RippleLog.getInstance().log?.getPrintLogFileName()
}

/**
 * 打印日志信息的类名
 */
fun getPrintLogClassName(): String? {
    return RippleLog.getInstance().log?.getPrintLogClassName()
}

/**
 * 获取打印log的头部
 */
fun getPrintLogHeader(): StringBuffer {
    val stringBuffer = StringBuffer()
    stringBuffer.append("(")
    stringBuffer.append(getPrintLogFileName())
    stringBuffer.append(":")
    stringBuffer.append(getPrintLogLineNumber())
    stringBuffer.append(") ")
    return stringBuffer
}

/**
 * 获取打印log的头部
 * 带有msg
 */
fun getPrintLogHeaderWithMsg(): StringBuffer {
    val stringBuffer = getPrintLogHeader()
    stringBuffer.append(RippleLog.getInstance().msg)
    stringBuffer.append(":")
    return stringBuffer
}

/**
 * 带有类跳转的日志信息
 * 返回值为(类名:行号)
 */
@JvmOverloads
fun withClassJump(msg: String? = null, tag: String = RippleLog.getInstance().tag): String {
    val fileNameAndLineNumber = getPrintLogHeader()
    if (msg == null) {
        logD(tag = tag, msg = fileNameAndLineNumber.toString())
    } else {
        logD(tag = tag, msg = getPrintLogHeaderWithMsg().append(msg).toString())
    }
    return fileNameAndLineNumber.toString()
}