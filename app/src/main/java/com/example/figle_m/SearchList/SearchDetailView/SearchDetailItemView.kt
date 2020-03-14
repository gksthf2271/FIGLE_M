package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.figle_m.R

class SearchDetailItemView : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
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