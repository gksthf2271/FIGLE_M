package com.khs.data.nexon_api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface NexonCDNService {
    @GET("live/externalAssets/common/players/p{spid}.png")
    suspend fun requestPlayerImage(
        @Header("Authorization") authorization: String,
        @Path("spid") spid: Int
    ): ResponseBody
}