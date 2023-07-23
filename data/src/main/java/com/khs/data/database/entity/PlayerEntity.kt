package com.khs.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Player")
open class PlayerEntity(@PrimaryKey(autoGenerate = true)@ColumnInfo(name = "Id") val id:Long?,
                   @ColumnInfo(name = "playerId") var playerId: String?,
                   @ColumnInfo(name = "playerName") var playerName: String?)