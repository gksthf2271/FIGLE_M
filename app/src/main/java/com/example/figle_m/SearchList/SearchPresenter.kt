package com.example.figle_m.SearchList

import android.util.Log
import com.example.figle_m.Data.DataManager
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.utils.DateUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

    override fun getUserHighRank(accessId: String) {
        mSearchListView?.showLoading()
        runBlocking {
            launch {
                DataManager.getInstance().loadUserHighRank(accessId,
                    {
                        Log.v(TAG, "getUserHighRank Success! ${it}")
                        mSearchListView?.showHighRank(it)
                    }, {
                        Log.v(TAG, "getUserHighRank Failed! $it")
                    })
            }
        }
    }

    override fun getMatchDetailList(matchId: String) {
        mSearchListView?.showLoading()
        runBlocking {
            launch {
                getMatchDetail(matchId, {
                        if (DEBUG) Log.v(TAG, "SearchPresenter getMatchDetailList: ${it.matchId}")
                        it.matchDate = DateUtils().getDate(it.matchDate).toString()
                        mSearchListView?.showSearchList(it)
                }, {
                    Log.v(TAG, "Result : getMatchDetailList response : $it")
                    mSearchListView?.showError(ERROR_EMPTY)
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
}