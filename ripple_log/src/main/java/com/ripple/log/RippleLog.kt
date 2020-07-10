package com.ripple.log

import com.ripple.log.impl.LogImpl


/**
 * Author: fanyafeng
 * Data: 2020/7/8 19:33
 * Email: fanyafeng@live.cn
 * Description: RippleLog的统一配置类
 */
class RippleLog private constructor() {

    /**
     * 是否打印全部
     * 优先级最高，默认为true，打印所有log
     * ILog的实现类需要据此去判断
     */
    var printAll = true
        /**
         * 如果是debug模式默认关闭所有打印
         * 但是为了有更好的扩展性，需要从外部配置
         * 此处关闭默认配置
         */
//        get() =
//            if (!BuildConfig.DEBUG) {
//                false
//            } else {
//                field
//            }
        private set(value) {
            field = value
        }

    /**
     * 是否打印V级别的log
     * 默认为true
     */
    var printV = true
        private set(value) {
            field = value
        }

    /**
     * 同上
     */
    var printD = true
        private set(value) {
            field = value
        }

    /**
     * 同上
     */
    var printI = true
        private set(value) {
            field = value
        }

    /**
     * 同上
     */
    var printW = true
        private set(value) {
            field = value
        }

    /**
     * 同上
     */
    var printE = true
        private set(value) {
            field = value
        }

    /**
     * 统一的打印TAG头
     */
    var tag: String = "RippleTag"
        private set(value) {
            field = value
        }

    /**
     * 统一的打印MSG头
     */
    var msg: String = "RippleMsg"
       private set(value) {
            field = value
        }

    /**
     * 设置具体的log实现类
     */
    var log: ILog? = null
        get() =
            if (field == null) {
                LogImpl()
            } else {
                field
            }
        private set(value) {
            field = value
        }


    companion object {
        @Volatile
        private var instance: RippleLog? = null

        @JvmStatic
        fun getInstance(): RippleLog {
            if (instance == null) {
                synchronized(RippleLog::class) {
                    if (instance == null) {
                        instance = RippleLog()
                    }
                }
            }
            return instance!!
        }
    }

    /**
     * 设置外部log实现类
     */
    fun setLog(log: ILog): RippleLog {
        this.log = log
        return this
    }

    /**
     * 设置统一的TAG头部
     */
    fun setTag(tag: String): RippleLog {
        this.tag = tag
        return this
    }

    /**
     * 设置统一的MSG头部
     */
    fun setMsg(msg: String): RippleLog {
        this.msg = msg
        return this
    }

    /**
     * 设置是否打印全部
     */
    fun setPrintAll(printAll: Boolean): RippleLog {
        this.printAll = printAll
        return this
    }

    fun setPrintV(printV: Boolean): RippleLog {
        this.printV = printV
        return this
    }

    fun setPrintD(printD: Boolean): RippleLog {
        this.printD = printD
        return this
    }

    fun setPrintI(printI: Boolean): RippleLog {
        this.printI = printI
        return this
    }

    fun setPrintW(printW: Boolean): RippleLog {
        this.printW = printW
        return this
    }

    fun setPrintE(printE: Boolean): RippleLog {
        this.printE = printE
        return this
    }


}