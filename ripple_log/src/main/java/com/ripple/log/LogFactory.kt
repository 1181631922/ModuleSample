package com.ripple.log

import com.ripple.log.extend.logD


/**
 * Author: fanyafeng
 * Data: 2020/7/6 13:34
 * Email: fanyafeng@live.cn
 * Description: log工厂
 * 一些比较常用的log封装
 */
object LogFactory {
    @JvmOverloads
    fun d(msg: String, tag: String = RippleLog.getInstance().tag) {
        logD(msg, tag)
    }

    /**
     * 打印类名以及msg
     */
    fun d(clazz: Class<*>, msg: String) {
        logD(msg, clazz.simpleName)
    }
}