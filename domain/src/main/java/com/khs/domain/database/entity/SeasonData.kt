package com.khs.domain.database.entity

data class SeasonData(
    val dataVersion : String,
    val lastModified : String,
    val contentsLength : Int,
    val seeasonList : List<Season>
)
