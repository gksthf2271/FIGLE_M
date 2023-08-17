package com.khs.data.database

import com.khs.data.database.entity.PlayerEntity
import com.khs.data.database.entity.SeasonEntity
import com.khs.domain.database.entity.Player
import com.khs.domain.database.entity.Season

fun SeasonEntity.asSeason(): Season = Season(
    id = id,
    seasonId = seasonId,
    className = className,
    seasonImg = seasonImg
)

fun List<SeasonEntity>.asSeasons() : List<Season> = map {
    it.asSeason()
}

fun PlayerEntity.asPlayer(): Player = Player(
    id = id,
    playerId = playerId,
    playerName = playerName
)

fun List<PlayerEntity>.asPlayers(): List<Player> = map {
    it.asPlayer()
}