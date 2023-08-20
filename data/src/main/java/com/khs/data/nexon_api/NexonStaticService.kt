package com.khs.data.nexon_api

import com.khs.data.nexon_api.response.DTO.PlayerNameDTO
import com.khs.data.nexon_api.response.DTO.SeasonDTO
import retrofit2.http.GET
import retrofit2.http.Header

interface NexonStaticService {
    @GET("latest/spid.json")
    suspend fun requestPlayerName(
        @Header("Authorization") authorization: String
    ): List<PlayerNameDTO>

    @GET("latest/seasonid.json")
    suspend fun requestSeasonIdList(
        @Header("Authorization") authorization: String
    ): List<SeasonDTO>
}