package com.fanyafeng.modules.testflow.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * Author: fanyafeng
 * Date: 2020/12/21 09:43
 * Email: fanyafeng@live.cn
 * Description:
 */
class SessionDetailViewModel(
    private val loadUserSessionUseCase: LoadUserSessionUseCase,
    private val userId: String
) : ViewModel() {

    private fun listenForUserSessionChanges(sessionId: String) {
        viewModelScope.launch {
            loadUserSessionUseCase(userId to sessionId).collect { loadResult ->

            }
        }
    }
}