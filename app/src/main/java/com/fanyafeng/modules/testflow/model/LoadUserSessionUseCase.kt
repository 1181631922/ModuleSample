package com.fanyafeng.modules.testflow.model

import com.fanyafeng.modules.testflow.domain.FlowUseCase
import com.fanyafeng.modules.testflow.result.Result
import com.fanyafeng.modules.testflow.shared.DefaultSessionAndUserEventRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * Author: fanyafeng
 * Date: 2020/12/18 14:50
 * Email: fanyafeng@live.cn
 * Description:
 */
class LoadUserSessionUseCase constructor(
    private val userEventRepository: DefaultSessionAndUserEventRepository,
    ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Pair<String?, String>, LoadUserSessionUseCaseResult>(ioDispatcher) {


    override fun execute(parameters: Pair<String?, String>): Flow<Result<LoadUserSessionUseCaseResult>> {
        val (userId, eventId) = parameters
        return userEventRepository.getObservableUserEvent(userId, eventId).map { it ->
            if (it is Result.Success) {
                Result.Success(LoadUserSessionUseCaseResult(userSession = it.data.userSession))
            } else {
                it
            }
        }
    }

}