package com.fanyafeng.modules.testflow.result


/**
 * Author: fanyafeng
 * Date: 2020/12/18 14:57
 * Email: fanyafeng@live.cn
 * Description:
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}