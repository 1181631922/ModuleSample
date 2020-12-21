package com.fanyafeng.modules.testflow.model

/**
 * Author: fanyafeng
 * Date: 2020/12/18 14:48
 * Email: fanyafeng@live.cn
 * Description:
 */
data class LoadUserSessionUseCaseResult(
    val userSession: UserSession,
    val userMessage: UserEventMessage? = null
)
