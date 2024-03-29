package com.khs.figle_m.SearchList

import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Base.BaseView
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.UserHighRankResponse
import okhttp3.ResponseBody

interface SearchContract: BaseView {

    interface View : BaseView{
        fun showLoading()
        fun hideLoading(isError: Boolean)

        fun showHighRank(userHighRankResponse: List<UserHighRankResponse>)
        fun showOfficialGameMatchIdList(matchDetailResponse: ResponseBody?)
        fun showCoachModeMatchIdList(matchDetailResponse: ResponseBody?)
        fun showAnalysisInfo(ouid: String, matchIdList: List<String>)
    }

    interface Presenter : BasePresenter<View> {
        fun getUserHighRank(ouid: String)
        fun getMatchId(ouid: String, matchType: DataManager.matchType, offset: Int, limit:Int)
        fun getMatchAnalysisByMatchId(ouid: String, matchIdList: List<String>)
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