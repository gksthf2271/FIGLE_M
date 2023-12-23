package com.khs.domain.database.entity

data class PlayerData(
    var dataVersion : String = "",
    var lastModified : String = "",
    var contentsLength : String = "",
    var playerList : List<Player> = listOf()
)