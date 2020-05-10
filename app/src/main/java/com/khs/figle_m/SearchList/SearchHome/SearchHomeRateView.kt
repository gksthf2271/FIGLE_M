package com.khs.figle_m.SearchList.SearchHome

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.data.PieEntry
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.MatchDetailResponse
import kotlinx.android.synthetic.main.cview_match_type_view.view.txt_title
import kotlinx.android.synthetic.main.cview_search_home_pie_chart.view.*

class SearchHomeRateView : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    val TAG = javaClass.name

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_search_home_pie_chart, this)
    }

    fun updateView(accessId : String, matchType: DataManager.matchType, arrayList: List<MatchDetailResponse>) {
        hideEmptyView()

        if(!arrayList.isEmpty()) initRate(accessId, arrayList)

        when (matchType.name) {
            DataManager.matchType.normalMatch.name -> {
                txt_title.text = "1 ON 1"
            }
            DataManager.matchType.coachMatch.name -> {
                txt_title.text = "감독모드"
            }
        }
    }

    fun updateEmptyView() {
        showEmptyView()
    }

    fun showEmptyView() {
        Log.v(TAG,"showEmptyView(...)")
        search_home_group_view.visibility = View.GONE
        search_home_empty_view.visibility = View.VISIBLE
    }

    fun hideEmptyView() {
        Log.v(TAG,"hideEmptyView(...)")
        search_home_empty_view.visibility = View.GONE
        search_home_group_view.visibility = View.VISIBLE
    }

    fun initRate(accessId : String, arrayList: List<MatchDetailResponse>) {
        var win = 0
        var draw = 0
        var lose = 0

        for (item in arrayList) {
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
