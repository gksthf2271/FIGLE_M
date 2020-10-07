package com.khs.figle_m.Analytics

import android.util.Log
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.utils.CrawlingUtils
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
            mAnalyticsView!!.showPlayerMap(playerMap)
        }
    }

    override fun loadPlayerInfoList(playerMap: Map<Int, List<PlayerDTO>>) {
        var playerInfoList = mutableListOf<AnalyticsPlayer>()
        for (key in playerMap.keys){
            var playerInfo = AnalyticsPlayer()
            for (playerDto in playerMap.get(key)!!.toList()){
                playerInfo.totalData.totalShoot += playerDto.status.shoot
                playerInfo.totalData.totalEffectiveShoot += playerDto.status.effectiveShoot
                playerInfo.totalData.totalAssist += playerDto.status.assist
                playerInfo.totalData.totalGoal += playerDto.status.goal
                playerInfo.totalData.totalDribble += playerDto.status.dribble
                playerInfo.totalData.totalPassTry += playerDto.status.passTry
                playerInfo.totalData.totalPassSuccess += playerDto.status.passSuccess
                playerInfo.totalData.totalBlock += playerDto.status.block
                playerInfo.totalData.totalTackle += playerDto.status.tackle
                playerInfo.totalData.totalSpRating += playerDto.status.spRating
                playerInfo.spId = playerDto.spId
                when (playerDto.spPosition) {
                    in 1 .. 8 -> {
                        playerInfo.position = ParentPositionEnum.D
                    }
                    in 9 .. 19 -> {
                        playerInfo.position = ParentPositionEnum.M
                    }
                    in 20 .. 27 -> {
                        playerInfo.position = ParentPositionEnum.F
                    }
                    0 -> {
                        playerInfo.position = ParentPositionEnum.GK
                    }
                }
            }
            playerInfo.playerDataList = playerMap.get(key)!!
            playerInfoList.add(playerInfo)
        }
        mAnalyticsView.let {
            mAnalyticsView!!.showPlayerInfoList(playerInfoList)
        }
    }

    override fun loadPlayerImageUrl(playerInfoList: List<AnalyticsPlayer>) {
        Log.v(TAG,"requestSize : ${playerInfoList.size}")
        var responseQ = PriorityQueue<String>()
        CoroutineScope(Dispatchers.Default).launch {
            for (item in playerInfoList) {
                var spId = item.spId
                var grade = 1
                CrawlingUtils().getPlayerImg(spId, grade, {
                    item.imageResUrl = it
                    responseQ.add(it)
                    if (responseQ.size == playerInfoList.size)
                        mAnalyticsView.let {
                            mAnalyticsView!!.showPlayerImage(playerInfoList)
                        }
                }, {
                    responseQ.add("")
                    if (responseQ.size == playerInfoList.size)
                        mAnalyticsView.let {
                            mAnalyticsView!!.showPlayerImage(playerInfoList)
                        }
                })
            }
        }
    }

    override fun takeView(view: AnalyticsContract.View) {
        mAnalyticsView = view
    }

    override fun dropView() {
        mAnalyticsView = null
    }

}