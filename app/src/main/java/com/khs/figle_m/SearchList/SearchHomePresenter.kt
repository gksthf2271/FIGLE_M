package com.khs.figle_m.SearchList

import android.util.Log
import com.khs.figle_m.Data.DataManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchHomePresenter: SearchContract.Presenter {

    val TAG:String = javaClass.simpleName
    val DEBUG:Boolean = false
    var  mSearchListView: SearchContract.View? = null
    val KEY_ANALYSIS_INFO = "KEY_ANALYSIS_INFO"

    open val ERROR_EMPTY = 0

    override fun takeView(view: SearchContract.View) {
        mSearchListView = view
    }
    override fun dropView() {
        mSearchListView = null
    }

    override fun getMatchId(accessId: String, matchType: DataManager.matchType, offset: Int, limit: Int) {
        mSearchListView?.showLoading()
        Thread(Runnable {
            DataManager.getInstance().loadMatchId(accessId, matchType.matchType, offset, limit,
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
                mSearchListView.let {
                    mSearchListView!!.showAnalysisInfo(accessId, matchIdList)
                }
            }
        }
    }


    override fun getUserHighRank(accessId: String) {
        mSearchListView?.showLoading()
        runBlocking {
            launch {
                DataManager.getInstance().loadUserHighRank(accessId,
                    {
                        Log.v(TAG, "getUserHighRank Success! $it")
//                        mSearchListView?.hideLoading(false)
                        mSearchListView?.showHighRank(it)
                    }, {
                        Log.v(TAG, "getUserHighRank Failed! $it")
                        mSearchListView?.showError(it)
                    })
            }
        }
    }
}