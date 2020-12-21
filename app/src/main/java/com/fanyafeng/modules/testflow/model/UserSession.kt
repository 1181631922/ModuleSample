package com.fanyafeng.modules.testflow.model


/**
 * Author: fanyafeng
 * Date: 2020/12/18 11:11
 * Email: fanyafeng@live.cn
 * Description:
 */
data class UserSession(val session: Session, val userEvent: UserEvent) {
    fun isPostSessionNotificationRequired(): Boolean {
        return !userEvent.isReviewed
    }
}