package com.fanyafeng.modules.testflow.shared

import com.fanyafeng.modules.testflow.model.UserEvent
import kotlinx.coroutines.flow.Flow


/**
 * Author: fanyafeng
 * Date: 2020/12/17 19:53
 * Email: fanyafeng@live.cn
 * Description:
 */
interface UserEventDataSource {
    fun getObservableUserEvent(userId: String, eventId: String): Flow<UserEventResult>

    fun getUserEvents(userId: String): List<UserEvent>

    fun getUserEvent(userId: String): UserEvent?
}

data class UserEventsResult(
    val userEvents: List<UserEvent>
)

data class UserEventResult(
    val userEvent: UserEvent?
)