package com.khs.domain.nexon.entity

data class Player(
    var spId: Int,
    val spPosition: Int,
    val spGrade: Int,
    val status: StatusInfo,
    var imageUrl: String?,       //custom
    var subImageUrl: String?     //custom
)