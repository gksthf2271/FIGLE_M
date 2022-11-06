package com.khs.figle_m.BottomMenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R
import com.khs.figle_m.Utils.LogUtil

class BottomNavigationBar(context: Context?, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    private val TAG = javaClass.simpleName

    init {
        initView()
    }

    fun initView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"initView")
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_loading_view, this)
    }


}