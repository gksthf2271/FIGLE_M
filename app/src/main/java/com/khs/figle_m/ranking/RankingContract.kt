package com.khs.figle_m.ranking

import android.content.Context
import com.khs.figle_m.base.BasePresenter
import com.khs.figle_m.base.BaseView

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