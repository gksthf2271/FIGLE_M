package com.khs.figle_m.SearchDetail

import android.util.Log
import com.khs.figle_m.DB.PlayerDao
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.Utils.SeasonEnum
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup

class SearchDetailPresenter: SearchDetailContract.Presenter {
    val TAG:String = javaClass.simpleName
    val DEBUG:Boolean = true
    var  mDetailListView: SearchDetailContract.View? = null

    val ERROR_EMPTY = "EMPTY"

    override fun dropView() {
        mDetailListView = null
    }

    override fun takeView(view: SearchDetailContract.View) {
        mDetailListView = view
    }
    override fun getPlayerImage(accessId: String, playerDTO: PlayerDTO, size:Int) {
        mDetailListView ?: return
        mDetailListView!!.showLoading()
        runBlocking {
            launch {
                getPlayerImage(playerDTO, {
                    LogUtil.dLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > getMatchDetailList: $it")
                    playerDTO.imageUrl = it
                    mDetailListView ?: return@getPlayerImage
                    mDetailListView!!.showPlayerImage(accessId, playerDTO, size)
                }, {
                    LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > Result : getMatchDetailList response : $it")
                    mDetailListView ?: return@getPlayerImage
                    playerDTO.imageUrl = it.toString()
                    if (it == 0) {
                        mDetailListView!!.showPlayerImage(accessId, playerDTO, size)
                    } else {
                        mDetailListView!!.showError(it)
                    }
                    mDetailListView!!.hideLoading()
                })
//                getPlayerImageWithSpid(playerDTO, {
//                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"SearchPresenter getMatchDetailList: $it")
//                    playerDTO.imageUrl = it
//                    mDetailListView ?: return@getPlayerImageWithSpid
//                    mDetailListView!!.showPlayerImage(accessId, playerDTO, size)
//                }, {
//                    LogUtil.vLog(LogUtil.TAG_UI, TAG,"Result : getMatchDetailList response : $it")
//                    mDetailListView ?: return@getPlayerImageWithSpid
//                    playerDTO.imageUrl = it.toString()
//                    if (it == 0) {
//                        mDetailListView!!.showPlayerImage(accessId, playerDTO, size)
//                    } else {
//                        mDetailListView!!.showError(it)
//                    }
//                    mDetailListView!!.hideLoading()
//                })
            }
        }

    }

    /*DummyData
    [{"id":101001183,"po":7}, {"id":214003647,"po":25},…]*/
    override fun getRankerPlayerList(matchType: Int, playerDTO: PlayerDTO) {
        mDetailListView!!.showLoading()
        runBlocking {
            launch {
                DataManager.getInstance().loadRankerPlayerAverData(matchType,
                    getPlayerObject(playerDTO),
                    {
                        mDetailListView ?: return@loadRankerPlayerAverData
                        mDetailListView!!.hideLoading()
                        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getRankerPlayerList success!")
                        mDetailListView!!.showPlayerDetailDialogFragment(playerDTO,it)
                    },{
                        mDetailListView ?: return@loadRankerPlayerAverData
                        mDetailListView!!.hideLoading()
                        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getRankerPlayerList failed!")
                        mDetailListView!!.showPlayerDetailDialogFragment(playerDTO, emptyList())
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

    private fun getPlayerImageWithSpid(playerDTO: PlayerDTO,
                                       onSuccess: ((String) -> Unit),
                                       onFailed: (Int) -> Unit) {
        val spId = playerDTO.spId
        LogUtil.dLog(LogUtil.TAG_SEARCH, TAG, "KHS > TEST, spId : ${playerDTO.spId} / imageUrl : ${playerDTO.imageUrl} / subImgUrl : ${playerDTO.subImageUrl}")
        DataManager.getInstance().loadPlayerImage(spId, {
            onSuccess(it.toString())
        }, {
            onFailed(0)
        })
    }

    private fun getPlayerImage(
        playerDTO: PlayerDTO,
        onSuccess: ((String) -> Unit),
        onFailed: (Int) -> Unit
    ) {
        var seasonId = playerDTO.spId.toString().substring(0, 3)
        var seasonName: String? = null
        if (seasonId == "224") {
            LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"TEST, 224 : $playerDTO")
            playerDTO.spId = playerDTO.spId.toString().replaceRange(0 .. 2, "234").toInt()
            seasonId = "234"
        }
        for (item in SeasonEnum.values()) {
            if (item.seasonId.toString() == seasonId)
                seasonName = item.className
        }

        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > seasonName : $seasonName")
        if (seasonName == null) {
            onFailed(0)
            return
        }

        try {
            DataManager.getInstance().loadPlayerInfo(playerDTO.spId, playerDTO.spGrade, {
                val doc = Jsoup.parseBodyFragment(it.string())
                val parentBody = doc.body().getElementById("wrapper")
                    .getElementById("middle")
                if (parentBody == null) {
                    onFailed(0)
                    return@loadPlayerInfo
                }

                try {
                    val imageUrl = parentBody
                        .getElementsByClass("datacenter").first()
                        .getElementsByClass("wrap").first()
                        .getElementsByClass("player_view").first()
                        .getElementsByClass("content data_detail").first()
                        .getElementsByClass("wrap").first()
                        .getElementsByClass("content_header").first()
                        .getElementsByClass("thumb $seasonName  _${seasonName.toUpperCase()}").first()
                        .getElementsByClass("img").first()
                        .childNodes().first()
                        .attributes().get("src")
                    onSuccess(imageUrl!!)
                } catch (e : Exception) {
                    LogUtil.eLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > exception $e")
                    onFailed(0)
                }
            }, {

            })
        }catch (e : Exception) {
            onFailed(0)
        }
    }
}