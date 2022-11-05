package com.khs.figle_m.Trade

import android.util.Log
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.TradeResponse
import com.khs.figle_m.Utils.CrawlingUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradePresenter : TradeContract.Presenter{
    var mTradeView : TradeContract.View? = null

    override fun getTradeInfoList(
        accessId : String,
        offset: Int?,
        limit: Int?) {
        if (mTradeView == null) return
        mTradeView!!.showLoading()
        var responseMap = mutableMapOf<String, List<TradeResponse>>()
        for (item in TradeHomeFragment.TradeType.values()) {
            DataManager.getInstance().loadTradeInfo(accessId, item, offset, limit,
                {
                    Log.v(this.javaClass.simpleName,"loadTradeInfo response(...) : ${it.first().tradeType}")
                    responseMap.put(it.first().tradeType.toString(), it)
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
                    Log.v(this.javaClass.simpleName,"load failed : $it")
                    responseMap.put(it.toString(), emptyList())
                    if (responseMap.values.size >= 2){
                        mTradeView!!.hideLoading()
                    }
                })
        }
    }

    override fun getTradePlayerImageUrl(tradeInfoList: List<TradeResponse>) {
        var requestMap = hashMapOf<String, TradeResponse>()
        Log.v(this.javaClass.simpleName,"requestSize : ${tradeInfoList.size}")
        var index = 0
        CoroutineScope(Dispatchers.Default).launch {
            for (item in tradeInfoList) {
                var spId = item.spid.toInt()
                var grade = item.grade.toInt()
                Log.v(this.javaClass.simpleName,"requestCall : $spId, index : $index")
                CrawlingUtils().getPlayerImg(spId, grade, {
                    item.imageResUrl = it
                    requestMap.put(item.saleSn, item)
                    Log.v(this.javaClass.simpleName, "S, input RequestMap : ${requestMap.values.size}")
                    if (requestMap.values.size == tradeInfoList.size) {
                        mTradeView?.apply {
                            showTradePlayerImageUrl(requestMap.values.toList())
                            hideLoading()
                        }
                    }
                }, {
                    item.imageResUrl = ""
                    requestMap.put(item.saleSn, item)
                    Log.v(this.javaClass.simpleName, "F, input RequestMap : ${requestMap.values.size} ? $it")
                    if (requestMap.values.size == tradeInfoList.size) {
                        mTradeView?.apply {
                            showTradePlayerImageUrl(requestMap.values.toList())
                            hideLoading()
                        }
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