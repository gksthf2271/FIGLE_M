package com.example.figle_m.SearchList

import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.View.BasePresenter
import com.example.figle_m.View.BaseView

interface SearchContract {
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showSearchList(userResponse : MatchDetailResponse?)
    }

    interface Presenter : BasePresenter<View> {
        fun getUserDatailList(nickname: String)
        fun getMatchDetailList(matchId: String)
        fun getMatchId(accessId: String, matchType: Int, offset: Int, limit:Int)
    }
}