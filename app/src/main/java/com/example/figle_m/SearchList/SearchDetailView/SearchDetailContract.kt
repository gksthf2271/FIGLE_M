package com.example.figle_m.SearchList.SearchDetailView

import com.example.figle_m.Base.BasePresenter
import com.example.figle_m.Base.BaseView
import okhttp3.HttpUrl

interface SearchDetailContract :BaseView {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun showPlayerImage(url : HttpUrl)
    }

    interface Presenter : BasePresenter<View> {
        fun getPlayerImage(spid: Int)
    }
}