package com.khs.figle_m.ranking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ranker(
    var rank_no : String?,               //순위
    var rank_icon_url : String?,         //랭크 아이콘 주소
    var level : String?,                 //레벨
    var level_gage : String?,            //레벨 경험치 퍼센트
    var name : String?,                  //이름
    var price : String?,                 //구단가치
    var win : String?,         //승무패
    var draw : String?,         //승무패
    var lose : String?,         //승무패
    var rank_rate : String?,             //승률
    var rank_point : String?,            //랭크점수
    var rank_percent : String?,          //상위 랭크 퍼센트
    var best_rank_icon_url : String?,    //역대 랭크 아이콘 주소
    var pre_rank_icon_url : String?,     //이전 랭크 아이콘 주소
    var cur_rank_icon_url : String?      //현재 랭크 아이콘 주소
) : Parcelable