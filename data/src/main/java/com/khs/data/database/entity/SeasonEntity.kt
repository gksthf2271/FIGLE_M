package com.khs.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Season")
open class SeasonEntity(@PrimaryKey(autoGenerate = true)@ColumnInfo(name = "Id") val id:Long?,
                        @ColumnInfo(name = "seasonId") var seasonId: Long?,
                        @ColumnInfo(name = "className") var className: String?,
                        @ColumnInfo(name = "seasonImg") var seasonImg: String?)