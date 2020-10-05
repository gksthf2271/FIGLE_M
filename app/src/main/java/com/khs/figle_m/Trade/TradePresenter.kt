package com.khs.figle_m.Trade

import android.util.Log
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.TradeResponse
import com.khs.figle_m.utils.CrawlingUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    Log.v(this.javaClass.name,"loadTradeInfo response(...) : $item")
                    responseMap.put(item.name, it)
                    if (responseMap.size == TradeHomeFragment.TradeType.values().size) {
                        var responseList = mutableListOf<TradeResponse>()
                        responseList.run{
                            for (item in responseMap.values){
                                responseList.addAll(responseList.size, item)
                            }
                        }
                        mTradeView!!.showTradeInfo(responseList)
                    }
                },
                {
                    Log.v(this.javaClass.name,"load failed : $it")
                    if (TradeHomeFragment.TradeType.sell.name.equals(item)){
                        mTradeView!!.hideLoading()
                    }
                })
        }
    }

    override fun getTradePlayerImageUrl(tradeInfoList: List<TradeResponse>) {
        var requestMap = hashMapOf<String, TradeResponse>()
        Log.v(this.javaClass.name,"requestSize : ${tradeInfoList.size}")
        var index = 0
        CoroutineScope(Dispatchers.Default).launch {
            for (item in tradeInfoList) {
                var spId = item.spid.toInt()
                var grade = item.grade.toInt()
                Log.v(this.javaClass.name,"requestCall : $spId, index : $index")
                CrawlingUtils().getPlayerImg(spId, grade, {
                    item.imageResUrl = it
                    requestMap.put(item.saleSn, item)
                    Log.v(this.javaClass.name, "S, input RequestMap : ${requestMap.values.size}")
                    if (requestMap.values.size == tradeInfoList.size) {
                        mTradeView!!.showTradePlayerImageUrl(requestMap.values.toList())
                        mTradeView!!.hideLoading()
                    }
                }, {
                    item.imageResUrl = ""
                    requestMap.put(item.saleSn, item)
                    Log.v(this.javaClass.name, "F, input RequestMap : ${requestMap.values.size} ? $it")
                    if (requestMap.values.size == tradeInfoList.size) {
                        mTradeView!!.showTradePlayerImageUrl(requestMap.values.toList())
                        mTradeView!!.hideLoading()
                    }
                })
                index++
            }
        }
    }

    override fun takeView(view: TradeContract.View) {
        mTradeView = view
    }

    override fun dropView() {
        mTradeView = null
    }
}