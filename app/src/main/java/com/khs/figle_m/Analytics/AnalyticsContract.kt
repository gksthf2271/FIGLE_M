package com.khs.figle_m.Analytics

import com.khs.figle_m.Base.BasePresenter
import com.khs.figle_m.Base.BaseView
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse

interface AnalyticsContract : BaseView {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading(isError: Boolean)

        fun showMatchDetail(matchDetailList: List<MatchDetailResponse>)
        fun showPlayerMap(playerMap: Map<Int, List<PlayerDTO>>)
        fun showPlayerInfoList(playerInfoList: List<AnalyticsPlayer>)
        fun showPlayerImage(playerInfoList: List<AnalyticsPlayer>)
    }

    interface Presenter : BasePresenter<View> {
        fun loadMatchDetail(matchIdList: List<String>)
        fun loadPlayerList(accessId: String, matchDetailList: List<MatchDetailResponse>)
        fun loadPlayerInfoList(playerMap: Map<Int, List<PlayerDTO>>)
        fun loadPlayerImageUrl(playerInfoList: List<AnalyticsPlayer>)
    }

    //TODO :  MatchInfoDTO List ->


    /*TODO, 1) 100경기 선수들 정보만 Map에 담고있으며, 최다 출전 선수를 뽑는다.
            2) Map<Player>(PlayerID, PlayerDTO) 형태를 가지고 있으며, 해당 선수 클릭 시 FIELD IMG에 골 위치를 출력한다.
            3) 골위치를 출력 할 때 가지고 있을 META DATA로는 골 유형(헤딩, 발, 반칙 패널티(프리킥, 패널티킥)을 가지며, 구별하여 출력
            3-1) 대체 선수 유형을 API를 통해 받을 수 있는지 확인
            3-2)

    */
}