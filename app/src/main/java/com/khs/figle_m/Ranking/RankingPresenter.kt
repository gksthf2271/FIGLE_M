package com.khs.figle_m.Ranking

import android.content.Context
import android.util.Log
import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Data.DataManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RankingPresenter : RankingContract.Presenter{
    val TAG: String = javaClass.name
    val isDebug: Boolean = false
    var mRankingView: RankingContract.View? = null

    override fun getRankingList(context: Context, page: Int) {
        mRankingView?.showLoading()
        runBlocking {
            launch {
                DataManager.getInstance().loadRaking(page,{
                    if (isDebug) Log.v(TAG, "getSeasonIdList Success! $it")
                    mRankingView?.showRanking()

                }, {
                    Log.v(TAG, "getSeasonIdList Failed! $it")
                    mRankingView?.showError(it)
                })
            }
        }
    }

    override fun takeView(view: RankingContract.View) {
        mRankingView = view
    }

    override fun dropView() {
        mRankingView = null
    }

}