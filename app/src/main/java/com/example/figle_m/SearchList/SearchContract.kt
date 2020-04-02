package com.example.figle_m.SearchList

import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserHighRankResponse
import com.example.figle_m.Base.BasePresenter
import com.example.figle_m.Base.BaseView
import okhttp3.ResponseBody

interface SearchContract: BaseView {

    interface View : BaseView{
        fun showLoading()
        fun hideLoading(isError: Boolean)

        fun showOfficialGameList(searchResponse : MatchDetailResponse?)
        fun showCoachModeList(searchResponse : MatchDetailResponse?)
        fun showHighRank(userHighRankResponse: List<UserHighRankResponse>)

        fun showOfficialGameMatchIdList(matchDetailResponse: ResponseBody?)
        fun showCoachModeMatchIdList(matchDetailResponse: ResponseBody?)
    }

    interface Presenter : BasePresenter<View> {
        fun getMatchDetailList(isOfficialGame: Boolean, matchId: String)
        fun getUserHighRank(accessId: String)
        fun getMatchId(accessId: String, matchType: Int, offset: Int, limit:Int)
    }
}