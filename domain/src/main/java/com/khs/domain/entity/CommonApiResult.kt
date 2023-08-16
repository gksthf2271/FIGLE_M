package com.khs.domain.entity

sealed class CommonApiResult<out T> {
    object Loading : CommonApiResult<Nothing>()

    data class Success<out T>(val data: T) : CommonApiResult<T>()

    sealed class Fail : CommonApiResult<Nothing>() {
        data class Error(val resultCode: Int, val errorMsg: String?) : Fail()
        data class Exception(val exception: Throwable) : Fail()
    }
}