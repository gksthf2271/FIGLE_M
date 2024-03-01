package com.khs.data.nexon_api.response.DTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class PlayerDTO (
    @Json(name = "spId") var spId: Int,
    @Json(name = "spPosition") val spPosition: Int,
    @Json(name = "spGrade") val spGrade: Int,
    @Json(name = "status") val status: StatusDTO,
    var imageUrl: String?,       //custom
    var subImageUrl: String?     //custom
) : Serializable