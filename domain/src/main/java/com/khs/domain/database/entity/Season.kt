package com.khs.domain.database.entity

data class Season(val id : Long? = null, val seasonId: Int, val className: String, val seasonImg: String)

data class SeasonData(
    val dataVersion : String = "",
    val lastModified : Long = 0,
    val contentsLength : Int = 0,
    val seeasonList : List<Season> = emptyList()
)

