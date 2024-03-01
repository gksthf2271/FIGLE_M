package com.khs.figle_m.home

import com.khs.figle_m.data.DataManager
import com.khs.figle_m.utils.LogUtil

class UserPresenter : UserContract.Presenter {
    val TAG: String = javaClass.simpleName
    var mUserView: UserContract.View? = null

    override fun getUserDatailList(nickname: String, teamPrice: String) {
        mUserView?.let { userView ->
            userView.showLoading()
            Thread(Runnable {
                DataManager.loadUserData(nickname,
                    {
                        userView.hideLoading()
                        userView.showUserList(it.apply { it!!.teamPrice = teamPrice })
                    }, {
                        LogUtil.vLog(LogUtil.TAG_SEARCH, TAG,"getUserDatailList $it")
                        userView.hideLoading()
                        userView.showError(it)
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