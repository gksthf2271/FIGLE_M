package com.khs.figle_m.SearchList.SearchDetailView

import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Base.BaseView
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import okhttp3.HttpUrl

interface SearchDetailContract :BaseView {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun showPlayerImage(spId:Int, url: HttpUrl)
        fun showPlayerDetailDialogFragment(playerDTO: PlayerDTO, rankerPlayerDTOList: List<RankerPlayerDTO>)
    }

    interface Presenter : BasePresenter<View> {
        fun getPlayerImage(spid: Int)
        fun getRankerPlayerList(matchType: Int, playerDTO: PlayerDTO)
    }
}