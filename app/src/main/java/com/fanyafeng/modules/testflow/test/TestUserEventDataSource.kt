package com.fanyafeng.modules.testflow.test

import com.fanyafeng.modules.testflow.model.UserEvent
import com.fanyafeng.modules.testflow.shared.UserEventDataSource
import com.fanyafeng.modules.testflow.shared.UserEventResult
import com.fanyafeng.modules.testflow.shared.UserEventsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


/**
 * Author: fanyafeng
 * Date: 2020/12/21 10:14
 * Email: fanyafeng@live.cn
 * Description:
 */
class TestUserEventDataSource : UserEventDataSource {
    override fun getObservableUserEvent(userId: String, eventId: String): Flow<UserEventResult> =
        flow {
            emit(UserEventResult(TestData.userEvent0))
        }

    override fun getUserEvents(userId: String): List<UserEvent> = TestData.userEvents

    override fun getUserEvent(userId: String): UserEvent? =
        TestData.userEvents.find { it.id == userId }
}