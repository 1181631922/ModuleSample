package com.fanyafeng.modules.filetest

import com.alibaba.fastjson.JSON

/**
 * Author: fanyafeng
 * Date: 2020/11/20 16:44
 * Email: fanyafeng@live.cn
 * Description:
 */
data class WelcomePageModel(
    /*
    相当于数据表中的主键
     */
    val id: Int,
    val name: String? = null,
    val age: Int? = null,
    val address: String? = null,
    val url: String? = null
) {
    fun toJson(): String {
        return JSON.toJSONString(this)
    }
}