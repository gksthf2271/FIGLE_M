package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.figle_m.R
import com.example.figle_m.databinding.CviewDetailInfoBinding

class SearchDetailItemView : ConstraintLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }
    var mDataBinding: CviewDetailInfoBinding? = null


    fun initView(context: Context) {
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