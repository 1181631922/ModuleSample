package com.ripple.log


/**
 * Author: fanyafeng
 * Data: 2020/7/8 19:22
 * Email: fanyafeng@live.cn
 * Description: log统一接口
 */
interface ILog {
    /**
     * [android.util.Log.v]
     */
    fun v(tag: String, msg: String)

    fun v(tag: String, msg: String, tr: Throwable)

    /**
     * [android.util.Log.d]
     */
    fun d(tag: String, msg: String)

    fun d(tag: String, msg: String, tr: Throwable)

    /**
     * [android.util.Log.i]
     */
    fun i(tag: String, msg: String)

    fun i(tag: String, msg: String, tr: Throwable)

    /**
     * [android.util.Log.w]
     */
    fun w(tag: String, msg: String)

    fun w(tag: String, tr: Throwable)

    fun w(tag: String, msg: String, tr: Throwable)

    /**
     * [android.util.Log.e]
     */
    fun e(tag: String, msg: String)

    fun e(tag: String, msg: String, tr: Throwable)

    fun getStackTraceString(tr: Throwable): String

    /**
     * 获取当前打印日志信息的方法名
     */
    fun getPrintLogMethodName(): String

    /**
     * 以下三个结合可以打印蓝色log双击直接定位到具体类
     * 具体使用如下：
     * java中：
     * 注：只能在java类中使用，并且跳转到相应位置，kotlin不适用
     * (getPrintLogClassName():getPrintLogLineNumber())
     *
     * java&kotlin:
     * (getPrintLogFileName():getPrintLogLineNumber())
     */

    /**
     * 获取当前打印日志信息的行号
     */
    fun getPrintLogLineNumber(): Int

    /**
     * 获取当前打印日志信息的文件名
     */
    fun getPrintLogFileName(): String

    /**
     * 获取当前打印日志信息的类名
     */
    fun getPrintLogClassName(): String


    //----------------------------------------------------------
}