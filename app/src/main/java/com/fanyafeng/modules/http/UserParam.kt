package com.fanyafeng.modules.http

import com.ripple.http.base.annotation.HttpRequest
import com.ripple.http.base.impl.HttpRequestParamsImpl


/**
 * Author: fanyafeng
 * Data: 2020/7/29 19:32
 * Email: fanyafeng@live.cn
 * Description:
 */
@HttpRequest("/test/test",host = "https://www.taobo.com")
class UserParam : HttpRequestParamsImpl()