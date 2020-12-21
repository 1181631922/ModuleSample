package com.fanyafeng.modules.testflow.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import com.fanyafeng.modules.testflow.result.Result
import kotlinx.coroutines.flow.flowOn


/**
 * Author: fanyafeng
 * Date: 2020/12/18 14:53
 * Email: fanyafeng@live.cn
 * Description:
 */
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<Result<R>> = execute(parameters)
        .catch { e -> emit(Result.Error(Exception(e))) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<Result<R>>
}