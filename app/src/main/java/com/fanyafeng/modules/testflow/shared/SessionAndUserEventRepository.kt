package com.fanyafeng.modules.testflow.shared

import com.fanyafeng.modules.testflow.model.LoadUserSessionUseCaseResult
import com.fanyafeng.modules.testflow.model.ObservableUserEvents
import kotlinx.coroutines.flow.Flow
import com.fanyafeng.modules.testflow.result.Result


/**
 * Author: fanyafeng
 * Date: 2020/12/18 09:59
 * Email: fanyafeng@live.cn
 * Description:
 */
interface SessionAndUserEventRepository {

    fun getObservableUserEvent(
        userId: String?,
        eventId: String
    ): Flow<Result<LoadUserSessionUseCaseResult>>

}