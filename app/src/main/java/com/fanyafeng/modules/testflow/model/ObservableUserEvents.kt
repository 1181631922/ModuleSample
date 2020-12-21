package com.fanyafeng.modules.testflow.model


/**
 * Author: fanyafeng
 * Date: 2020/12/18 11:10
 * Email: fanyafeng@live.cn
 * Description:
 */
data class ObservableUserEvents(
    val userSessions: List<UserSession>,
    val userMessage: UserEventMessage? = null,
    val userMessageSession: Session? = null
) {
}