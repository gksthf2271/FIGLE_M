package com.khs.figle_m

import android.content.Context
import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Base.BaseView
import okhttp3.ResponseBody

interface InitContract : BaseView {

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showMainActivity(responseBody: ResponseBody)
        fun updateProgress(progress: Int)
        fun setProgressMax(max: Int)

        fun showNetworkError()
    }

    interface Presenter : BasePresenter<View> {
        fun getPlayerNameList(context: Context)
        fun getSeasonIdList(context: Context)
    }
}