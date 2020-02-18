package com.example.figle_m.View

import com.example.figle_m.Data.DataManager
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserMatchIdResponse
import com.example.figle_m.Response.UserResponse
import com.example.figle_m.UserContract
import okhttp3.ResponseBody
import org.json.JSONArray
import java.util.*

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

    override fun getMatchId(accessId: String, matchType: Int, offset: Int, limit: Int) {
        mUserView?.showLoading()
        var userMatchIdResponse: ResponseBody? = null
        Thread(Runnable {
            userMatchIdResponse = DataManager.getInstance().loadMatchId(
                accessId,
                matchType,
                offset,
                limit)
            mUserView?.showMatchIdList(userMatchIdResponse)
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