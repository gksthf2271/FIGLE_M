package com.khs.figle_m.Trade

import com.khs.figle_m.Data.DataManager

class TradePresenter : TradeContract.Presenter{
    var mTradeView : TradeContract.View? = null

    override fun getTradeInfoList(
        accessId : String,
        tradeType: String,
        offset: Int?,
        limit: Int?) {
        mTradeView!!.showLoading()
        DataManager.getInstance().loadTradeInfo(accessId, tradeType, offset, limit,
            {
                mTradeView!!.showTradeInfo(it)
                mTradeView!!.hideLoading()
            },
            {
                mTradeView!!.hideLoading()
            })
    }

    override fun takeView(view: TradeContract.View) {
        mTradeView = view
    }

    override fun dropView() {
        mTradeView = null
    }

}