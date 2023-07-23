package com.khs.figle_m.Trade

import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Base.BaseView
import com.khs.data.nexon_api.response.TradeResponse


//accessid  string		Y	128	유저 고유 식별자
//tradetype string		Y	10	거래 종류 (구입 : buy, 판매 : sell)
//offset    integer	0	N	4	리스트에서 가져올 시작 위치
//limit     integer	100	N	4	리스트에서 가져올 갯수(최대 100 개)

interface TradeContract : BaseView {
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showTradeInfo(tradeInfoList: List<TradeResponse>)
        fun showTradePlayerImageUrl(tradeInfoList: List<TradeResponse>)
    }

    interface Presenter : BasePresenter<View> {
        fun getTradeInfoList(accessId : String, offset : Int?, limit: Int?)
        fun getTradePlayerImageUrl(tradeInfoList: List<TradeResponse>)
    }
}