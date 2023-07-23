package com.khs.data.nexon_api.response.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlayerDTO (
    @SerializedName("spId")
    var spId: Int,
    @SerializedName("spPosition")
    val spPosition: Int,
    @SerializedName("spGrade")
    val spGrade: Int,
    @SerializedName("status")
    val status: StatusDTO,
    var imageUrl: String?,       //custom
    var subImageUrl: String?     //custom
) : Serializable