package com.khs.figle_m.SearchDetail

import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Base.BaseView
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.DTO.RankerPlayerDTO

interface SearchDetailContract :BaseView {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun showPlayerImage(accessId:String, playerDTO: PlayerDTO, size: Int)
        fun showPlayerDetailDialogFragment(playerDTO: PlayerDTO, rankerPlayerDTOList: List<RankerPlayerDTO>)
    }

    interface Presenter : BasePresenter<View> {
        fun getPlayerImage(accessId:String, playerDTO: PlayerDTO, size:Int)
        fun getRankerPlayerList(matchType: Int, playerDTO: PlayerDTO)
    }
}