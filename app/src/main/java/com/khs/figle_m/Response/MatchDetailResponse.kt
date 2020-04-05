package com.khs.figle_m.Response

import android.os.Parcelable
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchDetailResponse(
    val matchId: String,
    var matchDate: String,
    val matchType: Int,
    val matchInfo: List<MatchInfoDTO>
) : Parcelable