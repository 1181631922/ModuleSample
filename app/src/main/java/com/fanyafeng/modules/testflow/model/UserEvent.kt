package com.fanyafeng.modules.testflow.model


/**
 * Author: fanyafeng
 * Date: 2020/12/17 19:52
 * Email: fanyafeng@live.cn
 * Description:
 */
data class UserEvent(
    val id: String,
    /** Tracks whether the user has starred the event. */
    val isStarred: Boolean = false,
    /** Tracks whether the user has provided feedback for the event. */
    val isReviewed: Boolean = false
)