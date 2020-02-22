package com.example.figle_m.View

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.figle_m.Response.BaseResponse

class SearchItemView(context: Context?) : ConstraintLayout(context) {

    var mContext: Context?
    lateinit var mResponse: BaseResponse

    init {
        mContext = context
    }

    constructor(context: Context?, response: BaseResponse) : this(context) {
        mContext = context
        mResponse = response
        updateView()
    }

    fun updateView() {
        mResponse.let { return }


    }
}