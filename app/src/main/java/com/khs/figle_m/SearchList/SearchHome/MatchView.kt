package com.khs.figle_m.SearchList.SearchHome

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import kotlinx.android.synthetic.main.cview_match_type_view.view.*

class MatchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    init {
        initView(context)
    }

    val TAG = javaClass.simpleName

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_match_type_view, this)
    }

    fun updateView(matchType: Int) {
        Log.v(TAG,"updateView : ${matchType}")
        hideEmptyView()
        when(matchType) {
            DataManager.matchType.normalMatch.matchType -> {
                img_icon.setImageResource(R.drawable.analytics)
                txt_title.text = "1 ON 1\n전적 검색"
            }
            DataManager.matchType.coachMatch.matchType -> {
                img_icon.setImageResource(R.drawable.strategy)
                txt_title.text = "감독 모드\n전적 검색"
            }
        }
    }

    fun showEmptyView() {
        Log.v(TAG,"showEmptyView(...)")
        group_view.visibility = View.GONE
        empty_view.visibility = View.VISIBLE
    }

    fun hideEmptyView() {
        Log.v(TAG,"hideEmptyView(...)")
        empty_view.visibility = View.GONE
        group_view.visibility = View.VISIBLE
    }
}
