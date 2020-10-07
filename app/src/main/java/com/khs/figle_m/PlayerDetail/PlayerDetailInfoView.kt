package com.khs.figle_m.PlayerDetail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R
import kotlinx.android.synthetic.main.cview_player_detail_bottom.view.*

class PlayerDetailInfoView  : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    val TAG = javaClass.name


    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_player_detail_bottom, this)
    }

    fun setTitleList(titleList: List<String>) {
        if (titleList.size != 4) return
        txt_title_1.text = titleList.get(0)
        txt_title_2.text = titleList.get(1)
        txt_title_3.text = titleList.get(2)
        txt_title_4.text = titleList.get(3)
    }

    fun setDataList(dataList: List<String>) {
        if (dataList.size != 4) return
        txt_value_1.text = dataList.get(0)
        txt_value_2.text = dataList.get(1)
        txt_value_3.text = dataList.get(2)
        txt_value_4.text = dataList.get(3)
    }
}