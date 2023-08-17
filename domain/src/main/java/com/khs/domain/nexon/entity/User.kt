package com.khs.domain.nexon.entity

data class User(
    val accessId: String,
    val nickname: String,
    val level: String,
    var teamPrice : String
)