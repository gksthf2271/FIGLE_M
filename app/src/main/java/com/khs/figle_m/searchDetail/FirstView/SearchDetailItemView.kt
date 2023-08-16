package com.khs.figle_m.searchDetail.FirstView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R
import com.khs.figle_m.databinding.CviewDetailInfoBinding

class SearchDetailItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var mBinding : CviewDetailInfoBinding
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewDetailInfoBinding.inflate(inflater, this, true)
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