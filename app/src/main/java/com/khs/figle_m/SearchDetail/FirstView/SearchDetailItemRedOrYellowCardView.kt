package com.khs.figle_m.SearchDetail.FirstView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R

class SearchDetailItemRedOrYellowCardView : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_detail_card_info, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun addLeftCard(redCount:Int, yellowCount: Int) {
        val rootView = findViewById<LinearLayout>(R.id.group_Left) as LinearLayout
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (redCount == 0 && yellowCount == 0) {
            val textView: TextView = inflater.inflate(R.layout.cview_detail_card_empty,rootView,false) as TextView
            rootView.addView(textView)
            return
        }
        for(i in 1..redCount) {
            val imageView: ImageView = inflater.inflate(R.layout.cview_card,rootView,false) as ImageView
            imageView.background = context.getDrawable(R.mipmap.red)
            rootView.addView(imageView)
        }

        for(i in 1..yellowCount) {
            val imageView: ImageView = inflater.inflate(R.layout.cview_card,rootView,false) as ImageView
            imageView.background = context.getDrawable(R.mipmap.yellow)
            rootView.addView(imageView)
        }
    }

    fun addRightCard(redCount:Int, yellowCount:Int) {
        val rootView = findViewById<LinearLayout>(R.id.group_Right)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (redCount == 0 && yellowCount == 0) {
            val textView: TextView = inflater.inflate(R.layout.cview_detail_card_empty,rootView,false) as TextView
            rootView.addView(textView)
            return
        }
        for(i in 1..redCount) {
            val imageView: ImageView = inflater.inflate(R.layout.cview_card,rootView,false) as ImageView
            imageView.background = context.getDrawable(R.mipmap.red)
            rootView.addView(imageView)
        }

        for(i in 1..yellowCount) {
            val imageView: ImageView = inflater.inflate(R.layout.cview_card,rootView,false) as ImageView
            imageView.background = context.getDrawable(R.mipmap.yellow)
            rootView.addView(imageView)
        }
    }

    fun setTitleText(text:String) {
        findViewById<TextView>(R.id.txt_title).text = text
    }
}