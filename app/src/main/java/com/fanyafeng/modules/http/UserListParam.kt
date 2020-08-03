package com.fanyafeng.modules.http

import com.ripple.http.base.annotation.HttpRequest
import com.ripple.http.base.impl.HttpRequestParamsImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Author: fanyafeng
 * Data: 2020/7/31 10:24
 * Email: fanyafeng@live.cn
 * Description:
 */


fun test() {
    GlobalScope.launch(Dispatchers.IO) {
        launch(Dispatchers.Main) {

        }
    }

}

