package com.fanyafeng.modules.testflow.session

import com.fanyafeng.modules.testflow.model.Session


/**
 * Author: fanyafeng
 * Date: 2020/12/18 14:37
 * Email: fanyafeng@live.cn
 * Description:
 */
interface SessionRepository {
    fun getSessions(): List<Session>
    fun getSession(eventId: String): Session?
}