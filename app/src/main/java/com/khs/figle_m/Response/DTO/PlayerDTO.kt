package com.khs.figle_m.Response.DTO

import android.os.Parcelable
import com.khs.data.nexon_api.response.DTO.StatusDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerDTO (
    var spId: Int,
    val spPosition: Int,
    val spGrade: Int,
    val status: StatusDTO,
    var imageUrl: String?,       //custom
    var subImageUrl: String?     //custom
) : Parcelable