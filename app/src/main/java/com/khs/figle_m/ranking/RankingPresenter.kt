package com.khs.figle_m.ranking

import android.content.Context
import com.khs.figle_m.utils.CrawlingUtils
import com.khs.figle_m.utils.LogUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RankingPresenter : RankingContract.Presenter{
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = false
    var mRankingView: RankingContract.View? = null

    override fun getRankingList(context: Context, page: Int) {
        mRankingView?.let { rankingView ->
            rankingView.showLoading()
            runBlocking {
                launch {
                    loadRankingByCrawling(page, {
                        LogUtil.dLog(LogUtil.TAG_RANK, TAG,"getRankingList Success! $it")
                        rankingView.hideLoading()
                        rankingView.showRanking(it)

                    }, {
                        LogUtil.vLog(LogUtil.TAG_RANK, TAG,"getRankingList Failed! $it")
                        rankingView.hideLoading()
                        rankingView.showError(it)
                    })
                }
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