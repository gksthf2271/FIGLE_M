package com.khs.figle_m.Analytics.Squad

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R

//TODO : 최근 경기 분석화면에서 필드와 포지션, 선수를 출력할 때 사용될 클래스

class SquadPositionView : ConstraintLayout{
    val TAG: String = javaClass.simpleName

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_analytics_position, this)
    }
}