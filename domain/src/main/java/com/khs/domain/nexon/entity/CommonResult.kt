package com.khs.domain.nexon.entity

sealed class CommonResult<out T> {
    object Loading : CommonResult<Nothing>()

    data class Success<out T>(val data: T) : CommonResult<T>()

    sealed class Fail : CommonResult<Nothing>() {
        data class Error(val resultCode: Int, val errorMsg: String?) : Fail()
        data class Exception(val exception: Throwable) : Fail()
    }
}