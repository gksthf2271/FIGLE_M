package com.khs.data.nexon_api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface NexonDataCenterService {
//    "n4ovrmin=0&n4ovrmax=150&n4salarymin=4&n4salarymax=34&n8playergrade1min=0&n8playergrade1max=1000&n1ability1min=40&n1ability1max=150&n1ability2min=40&n1ability2max=150&n1ability3min=40&n1ability3max=150&n4birthyearmin=1900&n4birthyearmax=2010&n4heightmin=156&n4heightmax=208&n4weightmin=50&n4weightmax=110&n4avgpointmin=0&n4avgpointmax=10&spid=214003647&n1Strong=1"
//    1confederation=0&n4leagueid=0&strseason=&strposition=&strphysical=&n1leftfootablity=0&n1rightfootablity=0&n1skillmove=0&n1interationalrep=0&n4birthmonth=0&n4birthday=0&n4teamid=0&n4nationid=0&strability1=&strability2=&strability3=&strtrait1=&strtrait2=&strtrait3=&strtraitnon1=&strtraitnon2=&strtraitnon3=&n1grow=0&n1teamcolor=0&strskill1=sprintspeed&strskill2=acceleration&strskill3=strength&strskill4=stamina&strsearchstatus=off&strorderby=&teamcolorid=0&strteamcolorcategory=&n1history=0&n4playyear=0&strplayername=%eb%b0%95%ec%a7%80%ec%84%b1&strteamname=&strnationname=&strteamcolorname=&n4ovrmin=0&n4ovrmax=200&n4salarymin=4&n4salarymax=99&n8playergrade1min=0&n8playergrade1max=99999&n1ability1min=40&n1ability1max=200&n1ability2min=40&n1ability2max=200&n1ability3min=40&n1ability3max=200&n4birthyearmin=1900&n4birthyearmax=2010&n4heightmin=140&n4heightmax=208&n4weightmin=50&n4weightmax=110&n4avgpointmin=0&n4avgpointmax=10&spid=101039943&n1Strong=1
//    @GET("PlayerInfo?n4ovrmin=0&n4ovrmax=150&n4salarymin=4&n4salarymax=34&n8playergrade1min=0&n8playergrade1max=1000&n1ability1min=40&n1ability1max=150&n1ability2min=40&n1ability2max=150&n1ability3min=40&n1ability3max=150&n4birthyearmin=1900&n4birthyearmax=2010&n4heightmin=156&n4heightmax=208&n4weightmin=50&n4weightmax=110&n4avgpointmin=0&n4avgpointmax=10")
    @GET("PlayerInfo?1confederation=0&n4leagueid=0&strseason=&strposition=&strphysical=&n1leftfootablity=0&n1rightfootablity=0&n1skillmove=0&n1interationalrep=0&n4birthmonth=0&n4birthday=0&n4teamid=0&n4nationid=0&strability1=&strability2=&strability3=&strtrait1=&strtrait2=&strtrait3=&strtraitnon1=&strtraitnon2=&strtraitnon3=&n1grow=0&n1teamcolor=0&strskill1=sprintspeed&strskill2=acceleration&strskill3=strength&strskill4=stamina&strsearchstatus=off&strorderby=&teamcolorid=0&strteamcolorcategory=&n1history=0&n4playyear=0&strplayername=%eb%b0%95%ec%a7%80%ec%84%b1&strteamname=&strnationname=&strteamcolorname=&n4ovrmin=0&n4ovrmax=200&n4salarymin=4&n4salarymax=99&n8playergrade1min=0&n8playergrade1max=99999&n1ability1min=40&n1ability1max=200&n1ability2min=40&n1ability2max=200&n1ability3min=40&n1ability3max=200&n4birthyearmin=1900&n4birthyearmax=2010&n4heightmin=140&n4heightmax=208&n4weightmin=50&n4weightmax=110&n4avgpointmin=0&n4avgpointmax=10")
    suspend fun requestPlayerInfo(
        @Query("spId") spId: Int,
        @Query("n1Strong") strong: Int
    ): ResponseBody

    //    http://fifaonline4.nexon.com/datacenter/rank?n4pageno=3
    @GET("rank")
    suspend fun requestRank(
        @Query("n4pageno") page: Int
    ): ResponseBody
}