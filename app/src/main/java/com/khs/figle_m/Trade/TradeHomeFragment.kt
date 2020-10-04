package com.khs.figle_m.Trade

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.RankingRecyclerViewAdapter
import com.khs.figle_m.Response.TradeResponse
import com.khs.figle_m.SearchList.SearchDecoration
import kotlinx.android.synthetic.main.fragment_ranking.*
import kotlinx.android.synthetic.main.fragment_trade.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradeHomeFragment : BaseFragment(), TradeContract.View {
    val TAG = javaClass.name
    var mTradePresenter : TradePresenter? = null
    enum class TradeType(index:Int){
        buy(0),
        sell(1)
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
        initView()
        requestData(accessId)
    }

    fun initView() {
        val layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(SearchDecoration(10))
        recycler_view.setLayoutManager(layoutManager)
    }

    fun requestData(accessId: String) {
        mTradePresenter!!.getTradeInfoList(accessId, 0, 20)
    }

    override fun showLoading() {
        Log.v(TAG,"showLoading(...)")
    }

    override fun hideLoading() {
        Log.v(TAG,"hideLoading(...)")
    }

    override fun showTradeInfo(tradeInfoList: List<TradeResponse>) {
        Log.v(TAG,"TEST, TradeInfoList : $tradeInfoList")
        mTradePresenter.let {
            mTradePresenter!!.getTradePlayerImageUrl(tradeInfoList)
        }
    }

    override fun showTradePlayerImageUrl(tradeInfoList: List<TradeResponse>) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.v(TAG,"TEST, showTradePlayerImageUrl : $tradeInfoList")
            recycler_view.adapter = TradeRecyclerViewAdapter(context!!, tradeInfoList, {
            })
        }
    }

    override fun showError(error: Int) {

    }
}