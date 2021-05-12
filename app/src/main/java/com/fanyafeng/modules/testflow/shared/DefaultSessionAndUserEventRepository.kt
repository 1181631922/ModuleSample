package com.fanyafeng.modules.testflow.shared

import androidx.annotation.WorkerThread
import com.fanyafeng.modules.testflow.model.*
import com.fanyafeng.modules.testflow.session.SessionRepository
import kotlinx.coroutines.flow.*
import com.fanyafeng.modules.testflow.result.Result
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Author: fanyafeng
 * Date: 2020/12/17 20:02
 * Email: fanyafeng@live.cn
 * Description:
 */
@Singleton
class DefaultSessionAndUserEventRepository @Inject constructor(
    private val userEventDataSource: UserEventDataSource,
    private val sessionRepository: SessionRepository
) : SessionAndUserEventRepository {

    @WorkerThread
    override fun getObservableUserEvent(
        userId: String?,
        eventId: String
    ): Flow<Result<LoadUserSessionUseCaseResult>> {
        if (userId == null) {
            val session = sessionRepository.getSession(eventId)
            return flow {
                emit(
                    if (session != null) {
                        Result.Success(
                            LoadUserSessionUseCaseResult(
                                userSession = UserSession(session, createDefaultUserEvent(session))
                            )
                        )
                    } else {
                        Result.Error(Exception("获取结果为空"))
                    }

                )
            }
        }

        return userEventDataSource.getObservableUserEvent(userId, eventId).map { userEventResult ->
            val event = sessionRepository.getSession(eventId)

            if (event != null) {
                val userSession = UserSession(
                    event,
                    userEventResult.userEvent ?: createDefaultUserEvent(event)
                )

                Result.Success(LoadUserSessionUseCaseResult(userSession = userSession))
            } else {
                Result.Error(Exception("获取结果为空"))
            }
        }
    }

    private fun createDefaultUserEvent(session: Session): UserEvent {
        return UserEvent(id = session.id)
    }
}