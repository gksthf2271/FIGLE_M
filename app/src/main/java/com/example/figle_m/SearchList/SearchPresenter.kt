package com.example.figle_m.SearchList

import android.util.Log
import com.example.figle_m.Data.DataManager
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.utils.DateUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchPresenter: SearchContract.Presenter {
    val TAG:String = javaClass.name
    var  mSearchListView: SearchContract.View? = null

    override fun takeView(view: SearchContract.View) {
        mSearchListView = view
    }
    override fun dropView() {
        mSearchListView = null
    }

    override fun getMatchDetailList(matchId: String) {
        mSearchListView?.showLoading()
        runBlocking {
            launch {
                getMatchDetail(matchId, {
                        Log.v(TAG, "SearchPresenter getMatchDetailList: ${it.matchId}")
                        it.matchDate = DateUtils().getDate(it.matchDate).toString()
                        mSearchListView?.showSearchList(it)
                }, {
                    Log.v(TAG, "Result : getMatchDetailList response : $it")
                })

            }
        }
    }

    fun getMatchDetail(matchId: String, onSuccess: ((MatchDetailResponse) -> Unit), onFailed: (String) -> Unit) {
        DataManager.getInstance().loadMatchDetail(matchId,
            {
                Log.v(TAG,"requestSuccess! ${it.matchId} + ${it.matchDate}")
                onSuccess(it)
            }, {
                Log.v(TAG,"requestFailed! $it")
                onFailed(it)
            })
    }
}