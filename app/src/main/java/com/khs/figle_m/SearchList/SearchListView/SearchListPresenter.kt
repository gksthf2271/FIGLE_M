package com.khs.figle_m.SearchList.SearchListView

import android.util.Log
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.SearchList.SearchContract
import com.khs.figle_m.Utils.DateUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchListPresenter : SearchContract.SearchListPresenter {
    val TAG:String = javaClass.name
    val DEBUG:Boolean = true
    var  mSearchListView: SearchContract.SearchListView? = null
    val ERROR_EMPTY = 0

    override fun takeView(view: SearchContract.SearchListView) {
        mSearchListView = view
    }
    override fun dropView() {
        mSearchListView = null
    }

    override fun getMatchDetailList(isOfficialGame: Boolean, matchId: String) {
        runBlocking {
            launch {
                getMatchDetail(matchId, {
                    if (DEBUG) Log.v(TAG, "SearchPresenter getMatchDetailList: ${it.matchId}")
                    it.matchDate = DateUtils().getDate(it.matchDate).toString()
                    mSearchListView?.showGameList(it)
                }, {
                    Log.v(TAG, "Result : getMatchDetailList response : $it")
                    mSearchListView?.showError(it)
                    mSearchListView?.hideLoading(true)
                })

            }
        }
    }

    private fun getMatchDetail(matchId: String, onSuccess: ((MatchDetailResponse) -> Unit), onFailed: (Int) -> Unit) {
        DataManager.getInstance().loadMatchDetail(matchId,
            {
                if (DEBUG) Log.v(TAG,"getMatchDetail Success! ${it.matchId} + ${it.matchDate}")
                onSuccess(it)
            }, {
                Log.v(TAG,"getMatchDetail Failed! $it")
                onFailed(it)
            })
    }
}