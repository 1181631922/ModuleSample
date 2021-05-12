package com.fanyafeng.modules.testflow.test

import com.fanyafeng.modules.testflow.session.DefaultSessionRepository
import com.fanyafeng.modules.testflow.shared.DefaultSessionAndUserEventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test


/**
 * Author: fanyafeng
 * Date: 2020/12/21 10:09
 * Email: fanyafeng@live.cn
 * Description:
 */
class DefaultSessionAndUserEventRepositoryTest {
    @Test
    fun observableUserEvents_areMappedCorrectly() = runBlocking {

        val repository = DefaultSessionAndUserEventRepository(
            TestUserEventDataSource(),
            DefaultSessionRepository()
        )
        val userEvents = repository.getObservableUserEvent("fanyafeng", "event0")
        userEvents.collect {
            println(it.toString())
        }

    }

}