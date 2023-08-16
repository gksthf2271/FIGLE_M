package com.khs.figle_m.utils

import android.content.Context
import com.khs.data.database.PlayerDataBase
import com.khs.data.database.entity.SeasonEntity

object SeasonManager {
    val gSeasonMap : HashMap<String, Season> = hashMapOf()

    fun updateSeason(context: Context) {
        val seasonDao = PlayerDataBase.getInstance(context)?.seasonDao()
        seasonDao?.let { dao ->
            val seasonInfoList : List<SeasonEntity> = dao.getAll()
            for (seasonEntity in seasonInfoList) {
                val seasonId = seasonEntity.seasonId.toString()
                var seasonClassId = ""
                val seasonClassName = seasonEntity.className
                val seasonImgUrl = seasonEntity.seasonImg
                seasonImgUrl?.let {
                    val list = it.split("https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/")
                    if (list.isNotEmpty() && list.size > 1) {
                        seasonClassId = list[1].replace(".png", "")
                    }
                }
                gSeasonMap[seasonId] = Season(
                    seasonId = seasonId,
                    classId = seasonClassId,
                    className = seasonClassName ?: "",
                    imgUrl = seasonImgUrl ?: ""
                )
            }
        }
    }

    fun loadSeason() : List<Season> {
        return if (gSeasonMap.values.isEmpty()) {
            val seasonEnumArray = SeasonEnum.values()
            val seasonList = arrayListOf<Season>()
            for (seasonEnum in seasonEnumArray) {
                seasonList.add(
                    Season(
                        seasonId = seasonEnum.seasonId.toString(),
                        classId = seasonEnum.className
                    )
                )
            }
            seasonList
        } else {
            gSeasonMap.values.toList()
        }
    }
}

data class Season(
    val seasonId: String,
    var classId: String,
    var className: String? = "",
    var imgUrl: String? = ""
)