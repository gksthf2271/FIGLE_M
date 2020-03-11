package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.figle_m.R
import com.example.figle_m.databinding.CviewDetailInfoBinding

class SearchDetailItemView(context: Context?) : ConstraintLayout(context) {
    var mDataBinding: CviewDetailInfoBinding? = null
    init {
     initView()
    }

    fun initView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.cview_detail_info, this)
        mDataBinding = DataBindingUtil.findBinding(v)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun setLeftText(text:String) {
        mDataBinding!!.txtLeftInfo.text = text
    }

    fun setRightText(text:String) {
        mDataBinding!!.txtRightInfo.text = text
    }

    fun setTitleText(text:String) {
        mDataBinding!!.txtTitle.text = text
    }
}