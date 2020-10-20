package com.test.startup

import android.content.Context
import com.ripple.log.extend.logD
import com.ripple.sdk.startup.Initializer


/**
 * Author: fanyafeng
 * Date: 2020/9/25 14:48
 * Email: fanyafeng@live.cn
 * Description:
 */
class LibraryD : Initializer<LibraryD.DDependency> {

    override fun create(context: Context): DDependency {
        logD("LibraryD初始化完成")
        return DDependency()
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }

    class DDependency {
        init {
            initializer = true
        }

        companion object {
            var initializer = false
        }
    }
}