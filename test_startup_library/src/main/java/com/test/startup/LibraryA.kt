package com.test.startup

import android.content.Context
import com.ripple.log.extend.logD
import com.ripple.sdk.startup.Initializer


/**
 * Author: fanyafeng
 * Date: 2020/9/25 14:45
 * Email: fanyafeng@live.cn
 * Description:
 */
class LibraryA : Initializer<LibraryA.ADependency> {

    override fun create(context: Context): ADependency {
        logD("LibraryA初始化完成")
        return ADependency()
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }


    class ADependency {

    }
}