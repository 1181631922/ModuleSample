package com.fanyafeng.modules.testflow.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData


/**
 * Author: fanyafeng
 * Date: 2020/12/21 09:53
 * Email: fanyafeng@live.cn
 * Description:
 */
class SimplifiedSessionDetailViewModel(
    private val userId: String,
    private val sessionId: String,
    private val loadUserSessionUseCase: LoadUserSessionUseCase
) : ViewModel() {
    val sessions = loadUserSessionUseCase(userId to sessionId).asLiveData()
}