package com.khs.data.nexon_api

import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.data.nexon_api.response.TradeResponse
import com.khs.data.nexon_api.response.UserCareerHighResponse
import com.khs.data.nexon_api.response.UserResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NexonAPIService {
    @GET("fconline/v1/id")
    suspend fun requestUserOuid(
        @Header("x-nxopen-api-key") authorization: String,
        @Query("nickname") nickname: String
    ): UserResponse     // 닉네임을 통한 사용자 OUID(계정 식별자) 조회

    @GET("fconline/v1/user/basic")
    suspend fun requestUser(
        @Header("x-nxopen-api-key") authorization: String,
        @Query("ouid") ouid: String
    ): UserResponse     // OUID(계정 식별자)를 통한 사용자 정보 조회

    @GET("fconline/v1/user/match")
    suspend fun requestMatchIds(
        @Header("x-nxopen-api-key") authorization: String,
        @Query("ouid") ouid: String,
        @Query("matchtype") matchtype: Int,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): ResponseBody

    @GET("fconline/v1/user/maxdivision")
    suspend fun requestCareerHigh(
        @Header("x-nxopen-api-key") authorization: String,
        @Query("ouid") ouid: String
    ): List<UserCareerHighResponse>


    @Deprecated("본인 거래 정보만 조회가능하여 서비스 불가한 API")
    @GET("v1.0/users/{accessid}/markets")
    suspend fun requestTradeInfo(
        @Header("x-nxopen-api-key") authorization: String,
        @Path("accessid") accessid: String,
        @Query("tradeType") tradeType: String,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): List<TradeResponse>

    @GET("fconline/v1/ranker-stats")
    suspend fun requestRankerPlayerAverList(
        @Header("x-nxopen-api-key") authorization: String,
        @Query("matchtype") matchType: Int,
        @Query("players") players: String
    ): List<RankerPlayerDTO>

    @GET("fconline/v1/match-detail")
    suspend fun requestMatchDetail(
        @Header("x-nxopen-api-key") authorization: String,
        @Path("matchid") matchid: String
    ): MatchDetailResponse
}