package com.khs.figle_m.PlayerDetail

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R
import kotlinx.android.synthetic.main.cview_player_detail_bottom.view.*

class PlayerDetailInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.name

    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_player_detail_bottom, this)
    }

    fun setTitleList(titleList: List<String>) {
        if (titleList.size != 4) return
        txt_title_1.text = titleList[0]
        txt_title_2.text = titleList[1]
        txt_title_3.text = titleList[2]
        txt_title_4.text = titleList[3]
    }

    fun setDataList(dataList: List<String>) {
        if (dataList.size != 4) return
        txt_value_1.text = dataList[0]
        txt_value_2.text = dataList[1]
        txt_value_3.text = dataList[2]
        txt_value_4.text = dataList[3]
    }

    fun setValueTextSize(size:Float) {
        txt_value_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        txt_value_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        txt_value_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        txt_value_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)

    }

    fun setTitleTextSize(size:Float) {
        txt_title_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        txt_title_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        txt_title_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        txt_title_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
    }
}