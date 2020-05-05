package com.khs.figle_m.SearchDetail

import android.util.Log
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.utils.SeasonEnum
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import java.lang.NullPointerException

class SearchDetailPresenter: SearchDetailContract.Presenter {
    val TAG:String = javaClass.name
    val DEBUG:Boolean = false
    var  mDetailListView: SearchDetailContract.View? = null

    open val ERROR_EMPTY = "EMPTY"

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
                    if (DEBUG) Log.v(TAG, "SearchPresenter getMatchDetailList: $it")
                    playerDTO.imageUrl = it
                    mDetailListView!!.showPlayerImage(accessId, playerDTO, size)
                }, {
                    Log.v(TAG, "Result : getMatchDetailList response : $it")
                    mDetailListView!!.showError(it)
                    mDetailListView!!.hideLoading()
                })
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
                        mDetailListView!!.hideLoading()
                        Log.v(TAG,"getRankerPlayerList success!")
                        mDetailListView!!.showPlayerDetailDialogFragment(playerDTO,it)
                    },{
                        mDetailListView!!.hideLoading()
                        Log.v(TAG,"getRankerPlayerList failed!")
                        mDetailListView!!.showPlayerDetailDialogFragment(playerDTO, emptyList())
                    })
            }
        }
    }

    fun getPlayerObject(playerDTO: PlayerDTO) : String {
        var jsonObject = JSONObject()
        jsonObject.put("id", playerDTO.spId)
        jsonObject.put("po", playerDTO.spPosition)

        var jsonArray = JSONArray()
        jsonArray.put(jsonObject)
        return jsonArray.toString()
    }

    fun getPlayerImage(
        playerDTO: PlayerDTO,
        onSuccess: ((String) -> Unit),
        onFailed: (Int) -> Unit
    ) {
        val seasonId = playerDTO.spId.toString().substring(0, 3)
        var seasonName: String? = null
        for (item in SeasonEnum.values()) {
            if (item.seasonId.toString().equals(seasonId))
                seasonName = item.className
        }

        Log.v(TAG,"test, seasonName : $seasonName")
        if (seasonName == null) {
            return
        }

        try {
            DataManager.getInstance().loadPlayerInfo(playerDTO.spId, playerDTO.spGrade, {
                val doc = Jsoup.parseBodyFragment(it.string())
                if(DEBUG) Log.v(TAG, "TEST ----------- \n $doc")
                val parentBody = doc.body().getElementById("wrapper")
                    .getElementById("middle")
                if (parentBody == null) {
                    Log.e(TAG, "ERROR ----------- \n $doc")
                    onFailed(0)
                    return@loadPlayerInfo
                }

                try {
                    val imageUrl = parentBody
                        .getElementsByClass("datacenter").get(0)
                        .getElementsByClass("wrap").get(0)
                        .getElementsByClass("player_view").get(0)
                        .getElementsByClass("content data_detail").get(0)
                        .getElementsByClass("wrap").get(0)
                        .getElementsByClass("content_header").get(0)
                        .getElementsByClass("thumb ${seasonName}").get(0)
                        .getElementsByClass("img").get(0)
                        .childNodes().get(0)
                        .attributes().get("src")
                    onSuccess(imageUrl!!)
                } catch (e : IndexOutOfBoundsException) {
                    Log.e(TAG,"IndexOutOfBoundsException $e")
                    onFailed(0)
                }
            }, {

            })
        }catch (e : Exception) {
            onFailed(0)
        }
    }
}