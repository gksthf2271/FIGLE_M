package com.khs.figle_m.searchList

import com.khs.figle_m.base.BasePresenter
import com.khs.figle_m.base.BaseView
import com.khs.figle_m.data.DataManager
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.data.nexon_api.response.UserHighRankResponse
import okhttp3.ResponseBody

interface SearchContract: BaseView {

    interface View : BaseView{
        fun showLoading()
        fun hideLoading(isError: Boolean)

        fun showHighRank(userHighRankResponse: List<UserHighRankResponse>)
        fun showOfficialGameMatchIdList(matchDetailResponse: ResponseBody?)
        fun showCoachModeMatchIdList(matchDetailResponse: ResponseBody?)
        fun showAnalysisInfo(accessId: String, matchIdList: List<String>)
    }

    interface Presenter : BasePresenter<View> {
        fun getUserHighRank(accessId: String)
        fun getMatchId(accessId: String, matchType: DataManager.matchType, offset: Int, limit:Int)
        fun getMatchAnalysisByMatchId(accessId: String, matchIdList: List<String>)
    }

    interface SearchListPresenter : BasePresenter<SearchListView> {
        fun getMatchDetailList(isOfficialGame: Boolean, matchId: String)
    }

    interface SearchListView : BaseView {
        fun showLoading()
        fun hideLoading(isError: Boolean)

        fun showGameList(searchResponse : MatchDetailResponse?)
    }
}