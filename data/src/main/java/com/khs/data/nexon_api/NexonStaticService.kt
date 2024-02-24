package com.khs.data.nexon_api

import com.khs.data.nexon_api.response.DTO.PlayerModel
import com.khs.data.nexon_api.response.DTO.SeasonModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming

interface NexonStaticService {
    @GET("static/fconline/meta/spid.json")
    @Streaming
    fun downloadPlayerNames(
        @Header("x-nxopen-api-key") authorization: String
    ): Call<List<PlayerModel>>

    @GET("static/fconline/meta/seasonid.json")
    fun requestSeasonIdList(
        @Header("x-nxopen-api-key") authorization: String
    ): Call<List<SeasonModel>>
}