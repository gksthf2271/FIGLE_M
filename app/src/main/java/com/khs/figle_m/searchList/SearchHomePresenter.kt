package com.khs.figle_m.searchList

import com.khs.figle_m.data.DataManager
import com.khs.figle_m.utils.LogUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchHomePresenter: SearchContract.Presenter {

    val TAG:String = javaClass.simpleName
    val DEBUG:Boolean = false
    var  mSearchListView: SearchContract.View? = null
    val KEY_ANALYSIS_INFO = "KEY_ANALYSIS_INFO"

    val ERROR_EMPTY = 0

    override fun takeView(view: SearchContract.View) {
        mSearchListView = view
    }
    override fun dropView() {
        mSearchListView = null
    }

    override fun getMatchId(accessId: String, matchType: DataManager.matchType, offset: Int, limit: Int) {
        mSearchListView?.showLoading()
        Thread(Runnable {
            DataManager.loadMatchId(accessId, matchType.matchType, offset, limit,
                {
                    when (matchType.name) {
                        DataManager.matchType.normalMatch.name -> {
                            mSearchListView?.showOfficialGameMatchIdList(it)
                        }
                        DataManager.matchType.coachMatch.name -> {
                            mSearchListView?.showCoachModeMatchIdList(it)
                        }
                    }
                }, {
                    mSearchListView?.showError(it)
                })
        }).start()
    }

    override fun getMatchAnalysisByMatchId(accessId: String, matchIdList: List<String>) {
        runBlocking {
            launch {
                mSearchListView?.showAnalysisInfo(accessId, matchIdList)
            }
        }
    }


    override fun getUserHighRank(accessId: String) {
        mSearchListView?.showLoading()
        runBlocking {
            launch {
                DataManager.loadUserHighRank(accessId,
                    {
                        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getUserHighRank Success! $it")
//                        mSearchListView?.hideLoading(false)
                        mSearchListView?.showHighRank(it)
                    }, {
                        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getUserHighRank Failed! $it")
                        mSearchListView?.showError(it)
                    })
            }
        }
    }
}