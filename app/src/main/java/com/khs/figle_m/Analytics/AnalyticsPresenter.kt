package com.khs.figle_m.Analytics

import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.utils.PositionEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AnalyticsPresenter : AnalyticsContract.Presenter{
    val TAG: String = javaClass.name
    val DEBUG: Boolean = false
    var mAnalyticsView: AnalyticsContract.View? = null
    var mFailedResponseQ = PriorityQueue<String>()

    override fun loadMatchDetail(matchIdList: List<String>) {
        mAnalyticsView.let{
            mAnalyticsView!!.showLoading()
            mFailedResponseQ.clear()
            var resultList = mutableListOf<MatchDetailResponse>()
            CoroutineScope(Dispatchers.Default).launch {
                for (matchId in matchIdList) {
                    DataManager.getInstance().loadMatchDetail(matchId, {
                        resultList.add(it)
                        if (resultList.size + mFailedResponseQ.size == matchIdList.size) {
                            mAnalyticsView!!.showMatchDetail(resultList)
                        }
                    }, {
                        mFailedResponseQ.add(it.toString())
                        if (resultList.size + mFailedResponseQ.size == matchIdList.size) {
                            mAnalyticsView!!.showMatchDetail(resultList)
                        }
                    })
                }
            }
        }
    }

    override fun loadPlayerList(accessId: String, matchDetailList: List<MatchDetailResponse>) {
        var playerMap = hashMapOf<Int, ArrayList<PlayerDTO>>()
        for(match in matchDetailList) {
            var matchInfo : MatchInfoDTO? = null
            if (match.matchInfo.size != 2) continue
            if (accessId.equals(match.matchInfo.get(0).accessId)) {
                matchInfo = match.matchInfo.get(0)
            } else {
                matchInfo = match.matchInfo.get(1)
            }

            for (player in matchInfo.player) {
                if (player.spPosition == PositionEnum.SUB.spposition) continue
                var playerList: ArrayList<PlayerDTO>? = playerMap.get(player.spId)
                if (playerList == null) playerList = arrayListOf()
                playerList.add(player)
                playerMap.put(player.spId, playerList)
            }
        }
        mAnalyticsView.let {
            mAnalyticsView!!.hideLoading(false)
            mAnalyticsView!!.showPlayerList(playerMap)
        }
    }

    override fun takeView(view: AnalyticsContract.View) {
        mAnalyticsView = view
    }

    override fun dropView() {
        mAnalyticsView = null
    }

}