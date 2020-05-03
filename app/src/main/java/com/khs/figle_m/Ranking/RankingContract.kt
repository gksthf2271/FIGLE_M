package com.khs.figle_m.Ranking

import android.content.Context
import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Base.BaseView

interface RankingContract : BaseView {
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showRanking(rankerList : List<Ranker>)
        fun showNetworkError()
    }

    interface Presenter : BasePresenter<View> {
        fun getRankingList(context: Context, page: Int)
    }
}