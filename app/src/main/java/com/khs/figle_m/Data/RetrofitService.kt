package com.khs.figle_m.Data

import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.UserHighRankResponse
import com.khs.figle_m.Response.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
//    https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname=아무거나다잘함
//    @Headers("Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTI0MTUyOTI2NCIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTQ4MSIsIlgtQXBwLVJhdGUtTGltaXQiOiIyMDAwMDoxMCIsIm5iZiI6MTU3NjkxNjU0MSwiZXhwIjoxNjM5OTg4NTQxLCJpYXQiOjE1NzY5MTY1NDF9.emF4Bd9O7zbC1giC4s3IrZ4S8Oax6-5IhDe3nZ0gCi4")
    @GET("v1.0/users")
    fun requestUser(
        @Header("Authorization") authorization: String,
        @Query("nickname") nickname: String
    ): Call<UserResponse>


    @GET("v1.0/matches/{matchid}")
    fun requestMatchDetail(
        @Header("Authorization") authorization: String,
        @Path("matchid") matchid: String
    ): Call<MatchDetailResponse>

    @GET("v1.0/users/{accessid}/matches")
    fun requestMatchId(
        @Header("Authorization") authorization: String,
        @Path("accessid") accessid: String,
        @Query("matchtype") matchtype: Int,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): Call<ResponseBody>

    @GET("v1.0/users/{accessid}/maxdivision")
    fun requestUserHighRank(
        @Header("Authorization") authorization: String,
        @Path("accessid") accessid: String
    ): Call<List<UserHighRankResponse>>

    @GET("live/externalAssets/common/players/p{spid}.png")
    fun requestPlayerImage(
        @Header("Authorization") authorization: String,
        @Path("spid") spid: Int
    ): Call<ResponseBody>

    @GET("latest/spid.json")
    fun requestPlayerName(
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>

}