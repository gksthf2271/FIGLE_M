package com.khs.domain.entity

data class User(
    val accessId: String,
    val nickname: String,
    val level: String,
    var teamPrice : String
)