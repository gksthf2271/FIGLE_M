package com.example.figle_m.SearchList

import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserHighRankResponse
import com.example.figle_m.UserContract
import com.example.figle_m.View.BasePresenter
import com.example.figle_m.View.BaseView
import okhttp3.ResponseBody

interface SearchContract: BaseView {

    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun showSearchList(searchResponse : MatchDetailResponse?)
        fun showHighRank(userHighRankResponse: List<UserHighRankResponse>)
    }

    interface Presenter : BasePresenter<View> {
        fun getMatchDetailList(matchId: String)
        fun getUserHighRank(accessId: String)
    }
}