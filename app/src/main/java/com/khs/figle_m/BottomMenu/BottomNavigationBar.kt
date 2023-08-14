package com.khs.figle_m.BottomMenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.databinding.CviewLoadingViewBinding

class BottomNavigationBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val TAG = javaClass.simpleName
    lateinit var mBinding : CviewLoadingViewBinding

    init {
        initView()
    }

    fun initView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"initView")
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewLoadingViewBinding.inflate(inflater, this, true)
    }
}