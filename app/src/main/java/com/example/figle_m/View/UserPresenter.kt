package com.example.figle_m.View

import com.example.figle_m.Data.DataManager
import com.example.figle_m.Response.UserResponse
import com.example.figle_m.UserContract

class UserPresenter: UserContract.Presenter{

    var  mUserView: UserContract.View? = null

    override fun getUserDataList(nickname: String) {
        mUserView?.showLoading()
        var userList: UserResponse? = null
        Thread(Runnable {
            userList = DataManager.getInstance().loadUserData(nickname)
            mUserView?.showUserList(userList)
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