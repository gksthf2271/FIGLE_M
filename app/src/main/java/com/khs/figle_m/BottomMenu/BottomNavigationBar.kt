package com.khs.figle_m.BottomMenu

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R

class BottomNavigationBar(context: Context?, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    private val TAG = javaClass.simpleName

    init {
        initView()
    }

    fun initView() {
        Log.v(TAG, "initView")
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_loading_view, this)
    }


}