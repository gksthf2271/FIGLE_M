package com.khs.data.nexon_api

import com.khs.data.nexon_api.response.DTO.PlayerNameDTO
import com.khs.data.nexon_api.response.DTO.SeasonDTO
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NexonStaticService {
    @GET("latest/spid.json")
    suspend fun requestPlayerName(
        @Header("Authorization") authorization: String
    ): List<PlayerNameDTO>

    @GET("latest/spid.json")
    suspend fun requestPlayerNameAsResponseBody(
        @Header("Authorization") authorization: String
    ): Flow<Call<ResponseBody>>

    @GET("latest/seasonid.json")
    suspend fun requestSeasonIdList(
        @Header("Authorization") authorization: String
    ): List<SeasonDTO>
}