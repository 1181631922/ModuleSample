package com.test.startup

import android.content.Context
import com.ripple.log.extend.logD
import com.ripple.sdk.startup.Initializer


/**
 * Author: fanyafeng
 * Date: 2020/9/25 14:47
 * Email: fanyafeng@live.cn
 * Description:
 */
class LibraryC : Initializer<LibraryC.CDependency> {


    override fun create(context: Context): CDependency {
        logD("LibraryC初始化完成")
        Thread.sleep(500)
        return CDependency()
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return mutableListOf(LibraryB::class.java)
    }

    class CDependency {

    }
}