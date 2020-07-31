package com.fanyafeng.modules.http

import com.fanyafeng.modules.BaseActivity
import com.ripple.http.base.annotation.HttpRequest
import com.ripple.http.base.impl.HttpRequestParamsImpl
import com.ripple.tool.extend.forEachAnchor
import com.ripple.tool.kttypelians.QuadraLambda


/**
 * Author: fanyafeng
 * Data: 2020/7/29 19:32
 * Email: fanyafeng@live.cn
 * Description:
 */
@HttpRequest("/get/getUserById")
class UserParam : HttpRequestParamsImpl() {
    var id: Int = 666
}

@HttpRequest("/get/getUserList")
class UserListParam : HttpRequestParamsImpl()