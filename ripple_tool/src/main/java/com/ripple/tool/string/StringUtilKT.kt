package com.ripple.tool.string

import com.ripple.tool.kttypelians.SuccessLambda

/**
 * Author: fanyafeng
 * Data: 2020/6/19 15:11
 * Email: fanyafeng@live.cn
 * Description:
 */

fun String?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}