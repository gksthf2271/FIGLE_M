package com.khs.data.nexon_api.response

import com.google.gson.annotations.SerializedName
import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import java.io.Serializable

data class MatchDetailResponse(
    @SerializedName("matchId")
    val matchId: String,
    @SerializedName("matchDate")
    var matchDate: String,
    @SerializedName("matchType")
    val matchType: Int,
    @SerializedName("matchInfo")
    val matchInfo: List<MatchInfoDTO>
) : Serializable