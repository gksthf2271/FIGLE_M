package com.example.figle_m.View

import android.util.Log
import com.example.figle_m.Data.DataManager
import com.example.figle_m.UserContract

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