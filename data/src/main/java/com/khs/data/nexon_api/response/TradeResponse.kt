package com.khs.data.nexon_api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//    데이터	         유형	         설명
//    tradeDate	    String	    거래일자 (ex. 2019-05-13T18:03:10) 구매(buy)일	경우	구매 등록 시점 , 판매(sell)일	경우	판매 완료 시점
//    saleSn	    String	    거래 고유 식별자
//    spid	        Integer	    선수 고유 식별자 (/metadata/spid API 참고)
//    grade	        Integer	    거래 선수 강화 등급
//    value	        Integer	    거래 선수 가치(BP)

@JsonClass(generateAdapter = true)
data class TradeResponse(
    @Json(name = "tradeDate") val tradeDate: String,
    @Json(name = "tradeDateMs") var tradeDateMs: Long,
    @Json(name = "saleSn") val saleSn: String,
    @Json(name = "spid") val spid: Int,
    @Json(name = "grade") var grade : Int,
    @Json(name = "value") var value : Long,       //가격 값이 Integer 범주보다 큼 고로 Long을 사용
    @Json(name = "tradeType") var tradeType : Int,
    @Json(name = "imageResUrl") var imageResUrl : String // custom
)