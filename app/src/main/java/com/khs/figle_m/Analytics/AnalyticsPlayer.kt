package com.khs.figle_m.Analytics

import android.os.Parcelable
import com.khs.figle_m.Response.DTO.PlayerDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnalyticsPlayer(
    var spId : Int = 0,
    var position : ParentPositionEnum = ParentPositionEnum.NONE,
    var playerDataList: List<PlayerDTO> = emptyList(),
    var totalData : TotalStatus = TotalStatus(),
    var imageResUrl : String = ""
) : Parcelable

@Parcelize
data class TotalStatus(
    var totalShoot: Int = 0,
    var totalEffectiveShoot: Int = 0,
    var totalAssist: Int = 0,
    var totalGoal: Int = 0,
    var totalDribble: Int = 0,
    var totalPassTry: Int = 0,
    var totalPassSuccess: Int = 0,
    var totalBlock: Int = 0,
    var totalTackle: Int = 0,
    var totalSpRating: Float = 0f
) : Parcelable

enum class ParentPositionEnum(val spposition:Int, val description:String){
    F(0,"Forward"),
    M(1, "Midfielder"),
    D(2, "Defencer"),
    GK(3, "GK"),
    NONE(4,"NONE")
}
