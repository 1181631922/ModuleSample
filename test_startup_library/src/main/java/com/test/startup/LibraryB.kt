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
class LibraryB : Initializer<LibraryB.BDependency> {


    override fun create(context: Context): BDependency {
        logD("LibraryB初始化完成")
        return BDependency()
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return mutableListOf(LibraryA::class.java)
    }

    class BDependency {

    }
}