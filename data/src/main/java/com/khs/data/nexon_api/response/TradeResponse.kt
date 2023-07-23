package com.khs.data.nexon_api.response

import com.google.gson.annotations.SerializedName

//    데이터	         유형	         설명
//    tradeDate	    String	    거래일자 (ex. 2019-05-13T18:03:10) 구매(buy)일	경우	구매 등록 시점 , 판매(sell)일	경우	판매 완료 시점
//    saleSn	    String	    거래 고유 식별자
//    spid	        Integer	    선수 고유 식별자 (/metadata/spid API 참고)
//    grade	        Integer	    거래 선수 강화 등급
//    value	        Integer	    거래 선수 가치(BP)

data class TradeResponse(
    @SerializedName("tradeDate")
    val tradeDate: String,
    @SerializedName("tradeDateMs")
    var tradeDateMs: Long,
    @SerializedName("saleSn")
    val saleSn: String,
    @SerializedName("spid")
    val spid: Int,
    @SerializedName("grade")
    var grade : Int,
    @SerializedName("value")
    var value : Long,       //가격 값이 Integer 범주보다 큼 고로 Long을 사용
    @SerializedName("tradeType")
    var tradeType : Int,
    @SerializedName("imageResUrl")
    var imageResUrl : String // custom
)