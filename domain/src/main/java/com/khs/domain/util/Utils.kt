package com.khs.domain.util

import com.khs.domain.entity.CommonApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

object Utils {
    fun <T : Any> asFlowApiResult(
        execute: suspend () -> T,
    ): Flow<CommonApiResult<T>> = flow {
        emit(CommonApiResult.Loading)
        try {
            emit(CommonApiResult.Success(execute()))
        } catch (e: HttpException) {
            emit(CommonApiResult.Fail.Error(resultCode = e.code(), errorMsg = e.message()))
        } catch (e: Exception) {
            emit(CommonApiResult.Fail.Exception(exception = e))
        }
    }
}