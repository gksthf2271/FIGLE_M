package com.example.figle_m.SearchList

import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserHighRankResponse
import com.example.figle_m.Base.BasePresenter
import com.example.figle_m.Base.BaseView

interface SearchContract: BaseView {

    interface View : BaseView{
        fun showLoading()
        fun hideLoading(isError: Boolean)
        fun showSearchList(searchResponse : MatchDetailResponse?)
        fun showHighRank(userHighRankResponse: List<UserHighRankResponse>)
    }

    interface Presenter : BasePresenter<View> {
        fun getMatchDetailList(matchId: String)
        fun getUserHighRank(accessId: String)
    }
}