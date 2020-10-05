package com.khs.figle_m.Ranking

import android.content.Context
import android.util.Log
import com.khs.figle_m.utils.CrawlingUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RankingPresenter : RankingContract.Presenter{
    val TAG: String = javaClass.name
    val DEBUG: Boolean = false
    var mRankingView: RankingContract.View? = null

    override fun getRankingList(context: Context, page: Int) {
        mRankingView?.showLoading()
        runBlocking {
            launch {
                loadRankingByCrawling(page,{
                    if (DEBUG) Log.v(TAG, "getRankingList Success! $it")
                    mRankingView?.hideLoading()
                    mRankingView?.showRanking(it)

                }, {
                    Log.v(TAG, "getRankingList Failed! $it")
                    mRankingView?.hideLoading()
                    mRankingView?.showError(it)
                })
            }
        }
    }

    fun loadRankingByCrawling(page:Int, onSuccess:(List<Ranker>) -> Unit, onFailed:(Int) -> Unit) {
        CrawlingUtils().getRanking(1,
            {
                onSuccess(it)
            },
            {
                onFailed(it)
            })
    }

    override fun takeView(view: RankingContract.View) {
        mRankingView = view
    }

    override fun dropView() {
        mRankingView = null
    }

}