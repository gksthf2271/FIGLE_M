package com.example.figle_m

import com.example.figle_m.Response.UserResponse
import com.example.figle_m.View.BasePresenter
import com.example.figle_m.View.BaseView

interface UserContract {

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showUserList(userResponse : UserResponse?)
    }

    interface Presenter : BasePresenter<View> {
        fun getUserDataList(nickname: String)
    }
}