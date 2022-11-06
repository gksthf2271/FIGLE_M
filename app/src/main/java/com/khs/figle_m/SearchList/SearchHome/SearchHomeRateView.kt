package com.khs.figle_m.SearchList.SearchHome

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.data.PieEntry
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Utils.LogUtil
import kotlinx.android.synthetic.main.cview_match_type_view.view.txt_title
import kotlinx.android.synthetic.main.cview_search_home_pie_chart.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SearchHomeRateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.simpleName
    lateinit var mMatchDetailList: ArrayList<MatchDetailResponse>
    lateinit var mFailedRequestQ : Queue<String>

    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_search_home_pie_chart, this)
    }

    fun updateView(accessId : String, matchType: DataManager.matchType, arrayList: List<String>) {
        mMatchDetailList = arrayListOf()
        mFailedRequestQ = PriorityQueue<String>()
        if(!arrayList.isEmpty()) {
            showLoadingView()
            initRate(accessId, arrayList)
        }

        when (matchType.name) {
            DataManager.matchType.normalMatch.name -> {
                txt_title.text = "1 ON 1"
            }
            DataManager.matchType.coachMatch.name -> {
                txt_title.text = "감독모드"
            }
        }
    }

    private fun showLoadingView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoadingView(...)")
        search_home_group_view.visibility = View.GONE
        search_home_empty_view.visibility = View.VISIBLE
        loading_view.visibility = View.VISIBLE
        loading_view.show()
    }

    private fun hideLoadingView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoadingView(...)")
        search_home_empty_view.visibility = View.GONE
        search_home_group_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        loading_view.hide()
    }

    private fun initRate(accessId: String, arrayList: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            mMatchDetailList.clear()
            var searchSize = DataManager().SEARCH_PAGE_SIZE
            if (arrayList.size < DataManager().SEARCH_PAGE_SIZE) {
                searchSize = arrayList.size
            }
            for (index in 0 .. searchSize-1) {
                if (arrayList.size <= index) break
                DataManager.getInstance().loadMatchDetailWrapper(arrayList.get(index)
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
            var myInfo: MatchInfoDTO? = null
            if (accessId == item.matchInfo[0].accessId) {
                myInfo = item.matchInfo[0]
            } else {
                myInfo = item.matchInfo[1]
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
        pie_chart_view.setData(pieEntryList, win + draw + lose)
    }
}
