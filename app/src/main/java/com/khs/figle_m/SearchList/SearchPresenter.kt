package com.khs.figle_m.SearchList

import android.util.Log
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.utils.DateUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody

class SearchPresenter: SearchContract.Presenter {

    val TAG:String = javaClass.name
    val DEBUG:Boolean = false
    var  mSearchListView: SearchContract.View? = null

    open val ERROR_EMPTY = "EMPTY"

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
//                    mSearchListView?.hideLoading(false)
                    when (matchType.name) {
                        DataManager.matchType.normalMatch.name -> {
                            mSearchListView?.showOfficialGameMatchIdList(it)
                        }
                        DataManager.matchType.coachMatch.name -> {
                            mSearchListView?.showCoachModeMatchIdList(it)
                        }
                    }
                }, {
                    Log.v("getMatchId", it)
                    mSearchListView?.hideLoading(true)
                })
        }).start()
    }

    override fun getUserHighRank(accessId: String) {
        mSearchListView?.showLoading()
        runBlocking {
            launch {
                DataManager.getInstance().loadUserHighRank(accessId,
                    {
                        Log.v(TAG, "getUserHighRank Success! ${it}")
                        mSearchListView?.showHighRank(it)
//                        mSearchListView?.hideLoading(false)
                    }, {
                        Log.v(TAG, "getUserHighRank Failed! $it")
                        mSearchListView?.hideLoading(true)
                    })
            }
        }
    }

    override fun getMatchDetailList(isOfficialGame: Boolean, matchId: String) {
        mSearchListView?.showLoading()
        runBlocking {
            launch {
                getMatchDetail(matchId, {
                    if (DEBUG) Log.v(TAG, "SearchPresenter getMatchDetailList: ${it.matchId}")
                    it.matchDate = DateUtils().getDate(it.matchDate).toString()
                    if (isOfficialGame) {
                        mSearchListView?.showOfficialGameList(it)
                    } else {
                        mSearchListView?.showCoachModeList(it)
                    }
//                    mSearchListView?.hideLoading(false)
                }, {
                    Log.v(TAG, "Result : getMatchDetailList response : $it")
                    mSearchListView?.showError(ERROR_EMPTY)
                    mSearchListView?.hideLoading(true)
                })

            }
        }
    }

    fun getMatchDetail(matchId: String, onSuccess: ((MatchDetailResponse) -> Unit), onFailed: (String) -> Unit) {
        DataManager.getInstance().loadMatchDetail(matchId,
            {
                if (DEBUG) Log.v(TAG,"getMatchDetail Success! ${it.matchId} + ${it.matchDate}")
                onSuccess(it)
            }, {
                Log.v(TAG,"getMatchDetail Failed! $it")
                onFailed(it)
            })
    }

    fun getPlayerNameList(onSuccess: (ResponseBody) -> Unit, onFailed: (String) -> Unit) {
        DataManager.getInstance().loadPlayerName({
            if (DEBUG) Log.v(TAG,"getMatchDetail Success! $it")
            onSuccess(it)
        },{
            Log.v(TAG,"getMatchDetail Failed! $it")
            onFailed(it)
        })
    }
}