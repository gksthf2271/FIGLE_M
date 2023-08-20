package com.khs.data.nexon_api

import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.data.nexon_api.response.TradeResponse
import com.khs.data.nexon_api.response.UserHighRankResponse
import com.khs.data.nexon_api.response.UserResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NexonAPIService {
    @GET("v1.0/users")
    suspend fun requestUser(
        @Header("Authorization") authorization: String,
        @Query("nickname") nickname: String
    ): UserResponse

    @GET("v1.0/matches/{matchid}")
    suspend fun requestMatchDetail(
        @Header("Authorization") authorization: String,
        @Path("matchid") matchid: String
    ): MatchDetailResponse

    @GET("v1.0/users/{accessid}/matches")
    suspend fun requestMatchIds(
        @Header("Authorization") authorization: String,
        @Path("accessid") accessid: String,
        @Query("matchtype") matchtype: Int,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): ResponseBody

    @GET("v1.0/users/{accessid}/maxdivision")
    suspend fun requestHighRanker(
        @Header("Authorization") authorization: String,
        @Path("accessid") accessid: String
    ): List<UserHighRankResponse>

    @GET("v1.0/users/{accessid}/markets")
    suspend fun requestTradeInfo(
        @Header("Authorization") authorization: String,
        @Path("accessid") accessid: String,
        @Query("tradeType") tradeType: String,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): List<TradeResponse>

    @GET("v1.0/rankers/status")
    suspend fun requestRankerPlayerAverList(
        @Header("Authorization") authorization: String,
        @Query("matchtype") matchType: Int,
        @Query("players") players: String
    ): List<RankerPlayerDTO>
}