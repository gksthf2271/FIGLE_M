package com.khs.figle_m.Trade

import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.TradeResponse
import com.khs.figle_m.Utils.CrawlingUtils
import com.khs.figle_m.Utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradePresenter : TradeContract.Presenter{
    var mTradeView : TradeContract.View? = null
    val TAG = javaClass.simpleName

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
                    LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"loadTradeInfo response(...) : ${it.first().tradeType}")
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
                    LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"load failed : $it")
                    responseMap.put(it.toString(), emptyList())
                    if (responseMap.values.size >= 2){
                        mTradeView!!.hideLoading()
                    }
                })
        }
    }

    override fun getTradePlayerImageUrl(tradeInfoList: List<TradeResponse>) {
        var requestMap = hashMapOf<String, TradeResponse>()
        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"requestSize : ${tradeInfoList.size}")
        var index = 0
        CoroutineScope(Dispatchers.Default).launch {
            for (item in tradeInfoList) {
                var spId = item.spid.toInt()
                var grade = item.grade.toInt()
                LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"requestCall : $spId, index : $index")
                CrawlingUtils().getPlayerImg(spId, grade, {
                    item.imageResUrl = it
                    requestMap.put(item.saleSn, item)
                    LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"S, input RequestMap : ${requestMap.values.size}")
                    if (requestMap.values.size == tradeInfoList.size) {
                        mTradeView?.apply {
                            showTradePlayerImageUrl(requestMap.values.toList())
                            hideLoading()
                        }
                    }
                }, {
                    item.imageResUrl = ""
                    requestMap.put(item.saleSn, item)
                    LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"F, input RequestMap : ${requestMap.values.size} ? $it")
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