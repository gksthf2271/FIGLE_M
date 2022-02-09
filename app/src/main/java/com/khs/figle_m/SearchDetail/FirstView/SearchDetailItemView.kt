package com.khs.figle_m.SearchDetail.FirstView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R

class SearchDetailItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    init {
        initView(context)
    }
    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_detail_info, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun setLeftText(text:String) {
        findViewById<TextView>(R.id.txt_LeftInfo).text = text
    }

    fun setRightText(text:String) {
        findViewById<TextView>(R.id.txt_RightInfo).text = text
    }

    fun setTitleText(text:String) {
        findViewById<TextView>(R.id.txt_title).text = text
    }
}