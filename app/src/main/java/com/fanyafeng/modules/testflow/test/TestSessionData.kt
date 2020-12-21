package com.fanyafeng.modules.testflow.test

import com.fanyafeng.modules.testflow.model.Session

/**
 * Author: fanyafeng
 * Date: 2020/12/21 10:35
 * Email: fanyafeng@live.cn
 * Description:
 */
object TestSessionData {
    val session0 = Session("fanyafeng", "fanyafeng title", "event0")
    val session1 = Session("xiaofan", "xiaofan title", "event1")
    val session2 = Session("tongxue", "tongxue title", "event2")

    val sessionList = listOf(session0, session1, session2)
}