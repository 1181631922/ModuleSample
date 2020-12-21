package com.fanyafeng.modules.testflow.test

import com.fanyafeng.modules.testflow.model.UserEvent

/**
 * Author: fanyafeng
 * Date: 2020/12/21 10:17
 * Email: fanyafeng@live.cn
 * Description:
 */
object TestData {
    val userEvent0 = UserEvent("fanyafeng")
    val userEvent1 = UserEvent("xiaofan")
    val userEvent2 = UserEvent("xiaofeng")

    val userEvents = listOf(userEvent0, userEvent1, userEvent2)
}