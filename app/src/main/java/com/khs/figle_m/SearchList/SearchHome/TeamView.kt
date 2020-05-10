package com.khs.figle_m.SearchList.SearchHome

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R
import kotlinx.android.synthetic.main.cview_match_type_view.view.*

class TeamView : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    val TAG = javaClass.name

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_match_type_view, this)
    }

    fun updateView(teamType: Int) {
        Log.v(TAG,"updateView : ${teamType}")
        hideEmptyView()
        var layoutParams = img_icon.layoutParams
        layoutParams.width = 300
        layoutParams.height = 300
        img_icon.layoutParams = layoutParams
        img_icon.setImageResource(R.drawable.lock)
        txt_title.text = "대표팀 : 맨체스터 유나이티드"
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
