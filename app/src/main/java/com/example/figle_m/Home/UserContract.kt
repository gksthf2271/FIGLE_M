package com.example.figle_m.Home

import com.example.figle_m.Response.UserResponse
import com.example.figle_m.Base.BasePresenter
import com.example.figle_m.Base.BaseView
import okhttp3.ResponseBody

interface UserContract {

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showUserList(userResponse : UserResponse?)
        fun showMatchIdList(matchDetailResponse: ResponseBody?)
    }

    interface Presenter : BasePresenter<View> {
        fun getUserDatailList(nickname: String)
        fun getMatchId(accessId: String, matchType: Int, offset: Int, limit:Int)
    }
}