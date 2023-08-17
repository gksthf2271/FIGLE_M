package com.khs.domain.util

import com.khs.domain.nexon.entity.CommonResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

object Utils {
    fun <T : Any> asFlowResult(
        execute: suspend () -> T,
    ): Flow<CommonResult<T>> = flow {
        emit(CommonResult.Loading)
        try {
            emit(CommonResult.Success(execute()))
        } catch (e: HttpException) {
            emit(CommonResult.Fail.Error(resultCode = e.code(), errorMsg = e.message()))
        } catch (e: Exception) {
            emit(CommonResult.Fail.Exception(exception = e))
        }
    }
}