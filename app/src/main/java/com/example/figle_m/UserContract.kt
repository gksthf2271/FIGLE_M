package com.example.figle_m

import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserMatchIdResponse
import com.example.figle_m.Response.UserResponse
import com.example.figle_m.View.BasePresenter
import com.example.figle_m.View.BaseView
import okhttp3.ResponseBody
import org.json.JSONArray
import java.util.*

interface UserContract {

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showUserList(userResponse : UserResponse?)
        fun showMatchDetailList(matchDetailResponse: MatchDetailResponse?)
        fun showMatchIdList(matchDetailResponse: ResponseBody?)
    }

    interface Presenter : BasePresenter<View> {
        fun getUserDatailList(nickname: String)
        fun getMatchDetailList(matchId: String)
        fun getMatchId(accessId: String, matchType: Int, offset: Int, limit:Int)
    }
}