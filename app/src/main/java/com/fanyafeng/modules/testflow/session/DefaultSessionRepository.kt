package com.fanyafeng.modules.testflow.session

import com.fanyafeng.modules.testflow.model.Session
import com.fanyafeng.modules.testflow.test.TestData
import com.fanyafeng.modules.testflow.test.TestSessionData


/**
 * Author: fanyafeng
 * Date: 2020/12/21 10:32
 * Email: fanyafeng@live.cn
 * Description:
 */
class DefaultSessionRepository constructor(
) : SessionRepository {
    override fun getSessions(): List<Session> = TestSessionData.sessionList

    override fun getSession(eventId: String): Session? =
        TestSessionData.sessionList.find { it.eventId == eventId }
}