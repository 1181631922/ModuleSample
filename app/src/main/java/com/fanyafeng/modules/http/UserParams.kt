package com.fanyafeng.modules.http

import com.ripple.http.base.IParamsBuilder
import com.ripple.http.base.IRequestParams
import com.ripple.http.base.annotation.HttpRequest
import com.ripple.http.base.impl.HttpRequestParamsImpl
import javax.net.ssl.SSLSocketFactory


/**
 * Author: fanyafeng
 * Data: 2020/7/29 19:32
 * Email: fanyafeng@live.cn
 * Description:
 */
@HttpRequest("/get/getUserById")
class UserParam : HttpRequestParamsImpl() {
    var id: Int = 666777
}

@HttpRequest("/get/getUserList")
class UserListParam : HttpRequestParamsImpl()

@HttpRequest("/post/getUserById")
class UserListPostParam : HttpRequestParamsImpl() {

    var id: Int = 666

    override fun isUseJsonFormat(): Boolean {
        return true
    }
}

@HttpRequest("/post/getUserList")
class UserListPostIdParam : HttpRequestParamsImpl() {
    var id: Int = 999
}
