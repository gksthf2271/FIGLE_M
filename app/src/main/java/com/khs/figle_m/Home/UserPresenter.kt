package com.khs.figle_m.Home

import android.util.Log
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.UserResponse

class UserPresenter : UserContract.Presenter {
    val TAG: String = javaClass.simpleName
    var mUserView: UserContract.View? = null

    override fun getUserDatailList(nickname: String, teamPrice: String) {
        mUserView.let {
            mUserView?.showLoading()
            Thread(Runnable {
                DataManager.getInstance().loadUserData(nickname,
                    {
                        mUserView?.hideLoading()
                        mUserView?.showUserList(it.apply { it!!.teamPrice = teamPrice })
                    }, {
                        Log.v(TAG, "getUserDatailList $it")
                        mUserView?.hideLoading()
                        mUserView?.showError(it)
                    })
            }).start()
        }
    }

    override fun takeView(view: UserContract.View) {
        mUserView = view
    }

    override fun dropView() {
        mUserView = null
    }

}