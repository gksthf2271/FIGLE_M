package com.khs.data.nexon_api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface NexonCDNService {
    @GET("live/externalAssets/common/players/p{spid}.png")
    suspend fun requestPlayerImageBySpid(
        @Header("x-nxopen-api-key") authorization: String,
        @Path("spid") spid: Int
    ): ResponseBody

    @GET("live/externalAssets/common/players/p{pid}.png")
    suspend fun requestPlayerImageByPid(
        @Header("x-nxopen-api-key") authorization: String,
        @Path("pid") pid: Int
    ): ResponseBody
}