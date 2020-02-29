package com.example.figle_m.View

import android.util.Log
import com.example.figle_m.Data.DataManager
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.UserContract
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

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
        val matchDetailList : MutableList<MatchDetailResponse> = mutableListOf()
        CoroutineScope(Dispatchers.Default).launch {
            for (matchId in matchIdList) {
                DataManager.getInstance().loadMatchDetail(matchId,
                    {
                        Log.v("TEST", "TEST, return ${it}")
                        matchDetailList.add(it)
                        if (DataManager.getInstance().SEARCH_LIMIT == matchDetailList.size){
                            mUserView?.hideLoading()
                            mUserView?.showMatchDetailList(matchDetailList)
                        }
                    }, {
                        Log.v(TAG,"requestFailed! $it")
                    })
            }
        }
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