package com.khs.figle_m.Trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.data.nexon_api.response.TradeResponse
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.SearchList.SearchDecoration
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.databinding.FragmentTradeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradeHomeFragment : BaseFragment(), TradeContract.View {
    val TAG = javaClass.simpleName
    lateinit var mTradeBinding : FragmentTradeBinding
    var mTradePresenter : TradePresenter? = null
    enum class TradeType(index:Int){
        TYPE_BUY(0),
        TYPE_SELL(1)
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
        mTradeBinding = FragmentTradeBinding.inflate(inflater, container, false)
        return mTradeBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mTradePresenter?.dropView()
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
        mTradeBinding.recyclerView.addItemDecoration(SearchDecoration(10))
        mTradeBinding.recyclerView.layoutManager = layoutManager

        mTradeBinding.txtTitle.text = "최근 거래 내역"
        mTradeBinding.btnBack.setOnClickListener { activity?.finish() }
    }

    private fun requestData(accessId: String) {
        mTradePresenter!!.getTradeInfoList(accessId, 0, 20)
    }

    override fun showLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoading(...)")
        CoroutineScope(Dispatchers.Main).launch {
            mTradeBinding.aviLoading.let{
                it.visibility = View.VISIBLE
                it.show(false)
            }
        }
    }

    override fun hideLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoading(...)")
        CoroutineScope(Dispatchers.Main).launch {
            mTradeBinding.aviLoading.let {
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
            mTradeBinding.recyclerView.apply {
                addItemDecoration(SearchDecoration(10))
                layoutManager = LinearLayoutManager(context)
                val sortedList:List<TradeResponse> = tradeInfoList.sortedByDescending { it.tradeDateMs }
                adapter = TradeRecyclerViewAdapter(context, sortedList){ _ -> }
            }
        }
    }

    override fun showError(error: Int) {

    }
}