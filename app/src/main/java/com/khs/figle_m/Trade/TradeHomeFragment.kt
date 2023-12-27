package com.khs.figle_m.Trade

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.RankingRecyclerViewAdapter
import com.khs.figle_m.Response.TradeResponse
import com.khs.figle_m.SearchList.SearchDecoration
import com.khs.figle_m.Utils.LogUtil
import kotlinx.android.synthetic.main.fragment_ranking.*
import kotlinx.android.synthetic.main.fragment_trade.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradeHomeFragment : BaseFragment(), TradeContract.View {
    val TAG = javaClass.simpleName
    var mTradePresenter : TradePresenter? = null
    enum class TradeType(index:Int){
        buy(0),
        sell(1)
    }
    override fun initPresenter() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"initPresenter(...)")
        mTradePresenter = TradePresenter()
        mTradePresenter?.takeView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trade, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTradePresenter?.dropView()
    }

    override fun onStart() {
        super.onStart()
        var ouid = ""
        arguments.let {
            ouid = it!!.getString(TradeActivity().KEY_ACCESS_ID, "")
        }
        initView()
        requestData(ouid)
    }

    fun initView() {
        val layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(SearchDecoration(10))
        recycler_view.setLayoutManager(layoutManager)

        txt_title.text = "최근 거래 내역"
        btn_back.setOnClickListener { activity!!.finish() }
    }

    private fun requestData(ouid: String) {
        mTradePresenter!!.getTradeInfoList(ouid, 0, 20)
    }

    override fun showLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoading(...)")
        CoroutineScope(Dispatchers.Main).launch {
            avi_loading?.let{
                it.visibility = View.VISIBLE
                it.show(false)
            }
        }
    }

    override fun hideLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoading(...)")
        CoroutineScope(Dispatchers.Main).launch {
            avi_loading?.let {
                it.visibility = View.GONE
                it.hide()
            }
        }
    }

    override fun showTradeInfo(tradeInfoList: List<TradeResponse>) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"TEST, TradeInfoList : $tradeInfoList")
        mTradePresenter?.getTradePlayerImageUrl(tradeInfoList)
    }

    override fun showTradePlayerImageUrl(tradeInfoList: List<TradeResponse>) {
        CoroutineScope(Dispatchers.Main).launch {
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"TEST, showTradePlayerImageUrl : $tradeInfoList")
            recycler_view.addItemDecoration(SearchDecoration(10))
            recycler_view.layoutManager = LinearLayoutManager(context)
            val sortedList:List<TradeResponse> = tradeInfoList.sortedByDescending { it.tradeDateMs }
            recycler_view.adapter = TradeRecyclerViewAdapter(context!!, sortedList){ tradeResponse ->
            }
        }
    }

    override fun showError(error: Int) {

    }
}