package com.khs.figle_m.Analytics

import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Utils.CrawlingUtils
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.Utils.PositionEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class AnalyticsPresenter : AnalyticsContract.Presenter{
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = false
    var mAnalyticsView: AnalyticsContract.View? = null
    var mFailedResponseQ = PriorityQueue<String>()

    override fun loadMatchDetail(matchIdList: List<String>) {
        mAnalyticsView?.showLoading()
        mFailedResponseQ.clear()
        val resultList = mutableListOf<MatchDetailResponse>()
        CoroutineScope(Dispatchers.Default).launch {
            for (matchId in matchIdList) {
                DataManager.getInstance().loadMatchDetail(matchId, {
                    resultList.add(it)
                    if (resultList.size + mFailedResponseQ.size == matchIdList.size) {
                        mAnalyticsView?.showMatchDetail(resultList)
                    }
                }, {
                    mFailedResponseQ.add(it.toString())
                    if (resultList.size + mFailedResponseQ.size == matchIdList.size) {
                        mAnalyticsView?.showMatchDetail(resultList)
                    }
                })
            }
        }
    }

    override fun loadPlayerList(ouid: String, matchDetailList: List<MatchDetailResponse>) {
        val playerMap = hashMapOf<Int, ArrayList<PlayerDTO>>()
        for(match in matchDetailList) {
            var matchInfo : MatchInfoDTO? = null
            if (match.matchInfo.size != 2) continue
            if (ouid.equals(match.matchInfo[0].ouid)) {
                matchInfo = match.matchInfo[0]
            } else {
                matchInfo = match.matchInfo[1]
            }

            for (player in matchInfo.player) {
                if (player.spPosition == PositionEnum.SUB.spposition) continue
                var playerList: ArrayList<PlayerDTO>? = playerMap.get(player.spId)
                if (playerList == null) playerList = arrayListOf()
                playerList.add(player)
                playerMap[player.spId] = playerList
            }
        }
        mAnalyticsView?.showPlayerMap(playerMap)
    }

    override fun loadPlayerInfoList(playerMap: Map<Int, List<PlayerDTO>>) {
        val playerInfoList = mutableListOf<AnalyticsPlayer>()
        for (key in playerMap.keys){
            val playerInfo = AnalyticsPlayer()
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
        mAnalyticsView?.showPlayerInfoList(playerInfoList)
    }

    override fun loadPlayerImageUrl(playerInfoList: List<AnalyticsPlayer>) {
        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"requestSize : ${playerInfoList.size}")
        val responseQ = PriorityQueue<String>()
        CoroutineScope(Dispatchers.Default).launch {
            for (item in playerInfoList) {
                val spId = item.spId
                val grade = 1
                CrawlingUtils().getPlayerImg(spId, grade, {
                    item.imageResUrl = it
                    responseQ.add(it)
                    if (responseQ.size == playerInfoList.size)
                        mAnalyticsView?.showPlayerImage(playerInfoList)
                }, {
                    responseQ.add("")
                    if (responseQ.size == playerInfoList.size)
                        mAnalyticsView?.showPlayerImage(playerInfoList)
                })
            }
            mAnalyticsView?.hideLoading(false)
        }
    }

    override fun loadRankerPlayerList(matchType: Int, playerDTO: PlayerDTO) {
        mAnalyticsView?.showLoading()
        runBlocking {
            launch {
                DataManager.getInstance().loadRankerPlayerAverData(matchType,
                    getPlayerObject(playerDTO),
                    {
                        mAnalyticsView?.hideLoading(false)
                        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"loadRankerPlayerList success!")
                        mAnalyticsView?.showPlayerDetailDialogFragment(playerDTO,it)
                    },{
                        mAnalyticsView?.hideLoading(true)
                        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"loadRankerPlayerList failed!")
                        mAnalyticsView?.showPlayerDetailDialogFragment(playerDTO, emptyList())
                    })
            }
        }
    }

    private fun getPlayerObject(playerDTO: PlayerDTO) : String {
        val jsonObject = JSONObject().apply {
            put("id", playerDTO.spId)
            put("po", playerDTO.spPosition)
        }

        return JSONArray().put(jsonObject).toString()
    }

    override fun takeView(view: AnalyticsContract.View) {
        mAnalyticsView = view
    }

    override fun dropView() {
        mAnalyticsView = null
    }

}