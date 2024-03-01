package com.khs.figle_m.home

import com.khs.data.nexon_api.response.UserResponse
import com.khs.figle_m.base.BasePresenter
import com.khs.figle_m.base.BaseView

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