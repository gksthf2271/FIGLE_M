package com.example.figle_m.View

import com.example.figle_m.Data.DataManager
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserResponse
import com.example.figle_m.UserContract

class UserPresenter: UserContract.Presenter{

    var  mUserView: UserContract.View? = null

    override fun getUserDatailList(nickname: String) {
        mUserView?.showLoading()
        var userList: UserResponse? = null
        Thread(Runnable {
            userList = DataManager.getInstance().loadUserData(nickname)
            mUserView?.showUserList(userList)
        }).start()
        mUserView?.hideLoading()
    }

    override fun getMatchDetailList(matchId: String) {
        mUserView?.showLoading()
        var matchResponse: MatchDetailResponse? = null
        Thread(Runnable {
            matchResponse = DataManager.getInstance().loadMatchDetail(matchId)
            mUserView?.showMatchDetailList(matchResponse)
        }).start()
        mUserView?.hideLoading()
    }

    override fun takeView(view: UserContract.View) {
        mUserView = view
    }

    override fun dropView() {
        mUserView = null
    }

}