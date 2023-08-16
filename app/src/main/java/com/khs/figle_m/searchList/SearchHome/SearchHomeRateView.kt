package com.khs.figle_m.searchList.SearchHome

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.data.PieEntry
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.data.DataManager
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.databinding.CviewSearchHomePieChartBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.PriorityQueue
import java.util.Queue

class SearchHomeRateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.simpleName
    lateinit var mBinding : CviewSearchHomePieChartBinding
    lateinit var mMatchDetailList: ArrayList<MatchDetailResponse>
    lateinit var mFailedRequestQ : Queue<String>

    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewSearchHomePieChartBinding.inflate(inflater, this, true)
    }

    fun updateView(accessId : String, matchType: DataManager.matchType, arrayList: List<String>) {
        mMatchDetailList = arrayListOf()
        mFailedRequestQ = PriorityQueue<String>()
        if(arrayList.isNotEmpty()) {
            showLoadingView()
            initRate(accessId, arrayList)
        }

        when (matchType.name) {
            DataManager.matchType.normalMatch.name -> {
                mBinding.txtTitle.text = "1 ON 1"
            }
            DataManager.matchType.coachMatch.name -> {
                mBinding.txtTitle.text = "감독모드"
            }
        }
    }

    private fun showLoadingView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoadingView(...)")
        mBinding.searchHomeGroupView.visibility = View.GONE
        mBinding.searchHomeEmptyView.visibility = View.VISIBLE
        mBinding.loadingView.visibility = View.VISIBLE
        mBinding.loadingView.show()
    }

    private fun hideLoadingView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoadingView(...)")
        mBinding.searchHomeEmptyView.visibility = View.GONE
        mBinding.searchHomeGroupView.visibility = View.VISIBLE
        mBinding.loadingView.visibility = View.GONE
        mBinding.loadingView.hide()
    }

    private fun initRate(accessId: String, arrayList: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            mMatchDetailList.clear()
            var searchSize = DataManager.SEARCH_PAGE_SIZE
            if (arrayList.size < DataManager.SEARCH_PAGE_SIZE) {
                searchSize = arrayList.size
            }
            for (index in 0 until searchSize) {
                if (arrayList.size <= index) break
                DataManager.loadMatchDetailWrapper(arrayList.get(index)
                    ,{
                        mMatchDetailList.add(it)
                        LogUtil.vLog(LogUtil.TAG_UI, TAG,"${mMatchDetailList.size} Success Request : ${it.matchId}")
                        if (searchSize == mMatchDetailList.size + mFailedRequestQ.size) {
                            LogUtil.vLog(LogUtil.TAG_UI, TAG,"TEST, KHS : ${mMatchDetailList.size}, ${mFailedRequestQ.size}")
                            CoroutineScope(Dispatchers.Main).launch {
                                updateView(accessId, mMatchDetailList)
                                hideLoadingView()
                            }
                        }
                    }, {
                        LogUtil.eLog(LogUtil.TAG_UI, TAG,"Failed Request : $it")
                        mFailedRequestQ.add(it)
                    })
            }
        }
    }

    private fun updateView(accessId: String, matchInfoList: List<MatchDetailResponse>) {
        var win = 0
        var draw = 0
        var lose = 0

        for (item in matchInfoList) {
            val myInfo = if (accessId == item.matchInfo[0].accessId) {
                item.matchInfo[0]
            } else {
                item.matchInfo[1]
            }
            myInfo.matchDetail.matchResult ?: continue
            when (myInfo.matchDetail.matchResult) {
                "승" -> win++
                "무" -> draw++
                "패" -> lose++
            }
        }

        val pieEntryList = arrayListOf(
            PieEntry(win.toFloat(), "승 : $win", null, null),
            PieEntry(draw.toFloat(), "무 : $draw", null, null),
            PieEntry(lose.toFloat(), "패 : $lose", null, null)
        )
        mBinding.pieChartView.setData(pieEntryList, win + draw + lose)
    }
}
