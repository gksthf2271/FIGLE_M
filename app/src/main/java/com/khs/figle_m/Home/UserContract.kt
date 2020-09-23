package com.khs.figle_m.Home

import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Base.BaseView
import com.khs.figle_m.Response.UserResponse

interface UserContract : BaseView {

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showUserList(userResponse : UserResponse?)
    }

    interface Presenter : BasePresenter<View> {
        fun getUserDatailList(nickname: String, teamPrice: String)
    }
}