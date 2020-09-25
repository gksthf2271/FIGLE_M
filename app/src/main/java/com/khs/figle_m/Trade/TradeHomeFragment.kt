package com.khs.figle_m.Trade

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.R
import com.khs.figle_m.Response.TradeResponse

class TradeHomeFragment : BaseFragment(), TradeContract.View {
    val TAG = javaClass.name
    var mTradePresenter : TradePresenter? = null
    enum class TradeType{
        buy,
        sell
    }
    override fun initPresenter() {
        mTradePresenter = TradePresenter()
        mTradePresenter!!.takeView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_trade, container, false)
        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        mTradePresenter!!.dropView()
    }

    override fun onStart() {
        super.onStart()
        var accessId = ""
        arguments.let {
            accessId = it!!.getString(TradeActivity().KEY_ACCESS_ID, "")
        }

        //TODO : TradeType 별 호출 2번 이루어지며, List 하나로 관리하는데 이를 어떻게 처리할것인가...
        mTradePresenter!!.getTradeInfoList(accessId, TradeType.buy.name, null, null)
        mTradePresenter!!.getTradeInfoList(accessId, TradeType.sell.name, null, null)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showTradeInfo(tradeInfoList: List<TradeResponse>) {
        Log.v(TAG,"TEST, TradeInfoList : $tradeInfoList")
    }

    override fun showError(error: Int) {

    }
}