package com.khs.figle_m.Trade

import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.TradeResponse

class TradePresenter : TradeContract.Presenter{
    var mTradeView : TradeContract.View? = null

    override fun getTradeInfoList(
        accessId : String,
        offset: Int?,
        limit: Int?) {
        mTradeView!!.showLoading()
        var responseMap = mutableMapOf<String, List<TradeResponse>>()
        for (item in TradeHomeFragment.TradeType.values()) {
            DataManager.getInstance().loadTradeInfo(accessId, item, offset, limit,
                {
                    responseMap.put(item.name, it)
                    if (responseMap.size == TradeHomeFragment.TradeType.values().size) {
                        var responseList = mutableListOf<TradeResponse>()
                        responseList.run{
                            for (item in responseMap.values){
                                responseList.addAll(responseList.size, item)
                            }
                        }
                        responseList.sortByDescending { it.tradeDateMs }
                        mTradeView!!.showTradeInfo(responseList)
                        mTradeView!!.hideLoading()
                    }
                },
                {
                    if (TradeHomeFragment.TradeType.sell.name.equals(item)){
                        mTradeView!!.hideLoading()
                    }
                })
        }
    }

    override fun takeView(view: TradeContract.View) {
        mTradeView = view
    }

    override fun dropView() {
        mTradeView = null
    }

}