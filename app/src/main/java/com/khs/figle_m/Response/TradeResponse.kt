package com.khs.figle_m.Response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//    데이터	         유형	         설명
//    tradeDate	    String	    거래일자 (ex. 2019-05-13T18:03:10) 구매(buy)일	경우	구매 등록 시점 , 판매(sell)일	경우	판매 완료 시점
//    saleSn	    String	    거래 고유 식별자
//    spid	        Integer	    선수 고유 식별자 (/metadata/spid API 참고)
//    grade	        Integer	    거래 선수 강화 등급
//    value	        Integer	    거래 선수 가치(BP)

@Parcelize
data class TradeResponse(
    val tradeDate: String,
    val saleSn: String,
    val spid: Integer,
    var grade : Integer,
    var value : Integer
) : Parcelable