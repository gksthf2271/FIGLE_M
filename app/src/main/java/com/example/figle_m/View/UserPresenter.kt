package com.example.figle_m.View

import android.util.Log
import com.example.figle_m.Data.DataManager
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.UserContract
import kotlinx.coroutines.*
import java.lang.Runnable

class UserPresenter: UserContract.Presenter{
    val TAG:String = javaClass.name
    var  mUserView: UserContract.View? = null

    override fun getUserDatailList(nickname: String) {
        mUserView?.showLoading()
        Thread(Runnable {
            DataManager.getInstance().loadUserData(nickname,
                {
                    mUserView?.hideLoading()
                    mUserView?.showUserList(it)
                }, {
                    Log.v("getUserDatailList", it)
                    mUserView?.hideLoading()
                })
        }).start()
    }

    override fun getMatchDetailList(matchIdList: List<String>) {
        mUserView?.showLoading()
        val matchDetailList: MutableList<MatchDetailResponse> = mutableListOf()
        runBlocking {
            Log.v(TAG, "TEST, Coroutine Start!")

            launch {
                for (matchId in matchIdList) {
                    Log.v("TEST", "TEST, matchId ${matchId}")
                    getMatchDetail(matchId, {
                        matchDetailList.add(it)
                        if (matchDetailList.size == DataManager.getInstance().SEARCH_LIMIT) {
                            mUserView?.hideLoading()
                            mUserView?.showMatchDetailList(matchDetailList)
                        }
                    }, {
                        Log.v(TAG, "Result : getMatchDetailList response : $it")
                    })
                }
            }
        }
        Log.v(TAG, "TEST, Coroutine End!")
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

    override fun getMatchId(accessId: String, matchType: Int, offset: Int, limit: Int) {
        mUserView?.showLoading()
        Thread(Runnable {
                DataManager.getInstance().loadMatchId(accessId, matchType, offset, limit,
                    {
                        mUserView?.hideLoading()
                        mUserView?.showMatchIdList(it)
                    }, {
                        Log.v("getMatchId", it)
                        mUserView?.hideLoading()
                    })
        }).start()
    }

    override fun takeView(view: UserContract.View) {
        mUserView = view
    }

    override fun dropView() {
        mUserView = null
    }

}