package com.khs.figle_m.data

import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.data.nexon_api.response.TradeResponse
import com.khs.data.nexon_api.response.UserCareerHighResponse
import com.khs.data.nexon_api.response.UserResponse
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
    fun requestHighRanker(
        @Header("Authorization") authorization: String,
        @Path("accessid") accessid: String
    ): Call<List<UserCareerHighResponse>>

    @GET("v1.0/users/{accessid}/markets")
    fun requestTradeInfo(
        @Header("Authorization") authorization: String,
        @Path("accessid") accessid: String,
        @Query("tradeType") tradeType: String,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): Call<List<TradeResponse>>

    @GET("live/externalAssets/common/players/p{spid}.png")
    fun requestPlayerImage(
        @Header("Authorization") authorization: String,
        @Path("spid") spid: Int
    ): Call<ResponseBody>

    @GET("latest/spid.json")
    fun requestPlayerName(
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>

    @GET("latest/seasonid.json")
    fun requestSeasonIdList(
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>

    @GET("v1.0/rankers/status")
    fun requestRankerPlayerAverList(
        @Header("Authorization") authorization: String,
        @Query("matchtype") matchType: Int,
        @Query("players") players: String
    ): Call<List<RankerPlayerDTO>>

//    "n4ovrmin=0&n4ovrmax=150&n4salarymin=4&n4salarymax=34&n8playergrade1min=0&n8playergrade1max=1000&n1ability1min=40&n1ability1max=150&n1ability2min=40&n1ability2max=150&n1ability3min=40&n1ability3max=150&n4birthyearmin=1900&n4birthyearmax=2010&n4heightmin=156&n4heightmax=208&n4weightmin=50&n4weightmax=110&n4avgpointmin=0&n4avgpointmax=10&spid=214003647&n1Strong=1"
//    1confederation=0&n4leagueid=0&strseason=&strposition=&strphysical=&n1leftfootablity=0&n1rightfootablity=0&n1skillmove=0&n1interationalrep=0&n4birthmonth=0&n4birthday=0&n4teamid=0&n4nationid=0&strability1=&strability2=&strability3=&strtrait1=&strtrait2=&strtrait3=&strtraitnon1=&strtraitnon2=&strtraitnon3=&n1grow=0&n1teamcolor=0&strskill1=sprintspeed&strskill2=acceleration&strskill3=strength&strskill4=stamina&strsearchstatus=off&strorderby=&teamcolorid=0&strteamcolorcategory=&n1history=0&n4playyear=0&strplayername=%eb%b0%95%ec%a7%80%ec%84%b1&strteamname=&strnationname=&strteamcolorname=&n4ovrmin=0&n4ovrmax=200&n4salarymin=4&n4salarymax=99&n8playergrade1min=0&n8playergrade1max=99999&n1ability1min=40&n1ability1max=200&n1ability2min=40&n1ability2max=200&n1ability3min=40&n1ability3max=200&n4birthyearmin=1900&n4birthyearmax=2010&n4heightmin=140&n4heightmax=208&n4weightmin=50&n4weightmax=110&n4avgpointmin=0&n4avgpointmax=10&spid=101039943&n1Strong=1
//    @GET("PlayerInfo?n4ovrmin=0&n4ovrmax=150&n4salarymin=4&n4salarymax=34&n8playergrade1min=0&n8playergrade1max=1000&n1ability1min=40&n1ability1max=150&n1ability2min=40&n1ability2max=150&n1ability3min=40&n1ability3max=150&n4birthyearmin=1900&n4birthyearmax=2010&n4heightmin=156&n4heightmax=208&n4weightmin=50&n4weightmax=110&n4avgpointmin=0&n4avgpointmax=10")
    @GET("PlayerInfo?1confederation=0&n4leagueid=0&strseason=&strposition=&strphysical=&n1leftfootablity=0&n1rightfootablity=0&n1skillmove=0&n1interationalrep=0&n4birthmonth=0&n4birthday=0&n4teamid=0&n4nationid=0&strability1=&strability2=&strability3=&strtrait1=&strtrait2=&strtrait3=&strtraitnon1=&strtraitnon2=&strtraitnon3=&n1grow=0&n1teamcolor=0&strskill1=sprintspeed&strskill2=acceleration&strskill3=strength&strskill4=stamina&strsearchstatus=off&strorderby=&teamcolorid=0&strteamcolorcategory=&n1history=0&n4playyear=0&strplayername=%eb%b0%95%ec%a7%80%ec%84%b1&strteamname=&strnationname=&strteamcolorname=&n4ovrmin=0&n4ovrmax=200&n4salarymin=4&n4salarymax=99&n8playergrade1min=0&n8playergrade1max=99999&n1ability1min=40&n1ability1max=200&n1ability2min=40&n1ability2max=200&n1ability3min=40&n1ability3max=200&n4birthyearmin=1900&n4birthyearmax=2010&n4heightmin=140&n4heightmax=208&n4weightmin=50&n4weightmax=110&n4avgpointmin=0&n4avgpointmax=10")
    fun requestPlayerInfo(
        @Query("spId") spId: Int,
        @Query("n1Strong") strong: Int
    ): Call<ResponseBody>

    //    http://fifaonline4.nexon.com/datacenter/rank?n4pageno=3
    @GET("rank")
    fun requestRaking(
        @Query("n4pageno") page: Int
    ): Call<ResponseBody>
}