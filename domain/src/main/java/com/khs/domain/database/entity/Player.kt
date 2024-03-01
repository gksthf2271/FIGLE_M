package com.khs.domain.database.entity

data class Player(val id: Long?, val playerId: String?, val playerName: String?)

data class PlayerData(
    var dataVersion : String = "",
    var lastModified : Long = 0,
    var contentsLength : Int = 0,
    var playerList : List<Player> = listOf()
)