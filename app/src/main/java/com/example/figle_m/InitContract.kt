package com.example.figle_m

import android.content.Context
import com.example.figle_m.Base.BasePresenter
import com.example.figle_m.Base.BaseView
import okhttp3.ResponseBody

interface InitContract: BaseView {

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showMainActivity(responseBody : ResponseBody)
    }

    interface Presenter : BasePresenter<View> {
        fun getPlayerNameList(context: Context)
    }
}