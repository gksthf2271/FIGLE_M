package com.khs.figle_m.Home

import android.util.Log
import com.khs.figle_m.Data.DataManager

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
                    Log.v(TAG,"getUserDatailList $it")
                    mUserView?.hideLoading()
                    mUserView?.showError(it)
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