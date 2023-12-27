package com.khs.figle_m.SearchDetail

import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Utils.CrawlingUtils
import com.khs.figle_m.Utils.LogUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject

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
    override fun getPlayerImage(ouid: String, playerDTO: PlayerDTO, size:Int) {
        mDetailListView ?: return
        mDetailListView!!.showLoading()
        runBlocking {
            launch {
                getPlayerImage(playerDTO, {
                    LogUtil.dLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > getMatchDetailList: $it")
                    playerDTO.imageUrl = it
                    mDetailListView ?: return@getPlayerImage
                    mDetailListView!!.showPlayerImage(ouid, playerDTO, size)
                }, {
                    LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > Result : getMatchDetailList response : $it")
                    mDetailListView ?: return@getPlayerImage
                    playerDTO.imageUrl = it.toString()
                    if (it == 0) {
                        mDetailListView!!.showPlayerImage(ouid, playerDTO, size)
                    } else {
                        mDetailListView!!.showError(it)
                    }
                    mDetailListView!!.hideLoading()
                })
//                getPlayerImageWithSpid(playerDTO, {
//                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"SearchPresenter getMatchDetailList: $it")
//                    playerDTO.imageUrl = it
//                    mDetailListView ?: return@getPlayerImageWithSpid
//                    mDetailListView!!.showPlayerImage(ouid, playerDTO, size)
//                }, {
//                    LogUtil.vLog(LogUtil.TAG_UI, TAG,"Result : getMatchDetailList response : $it")
//                    mDetailListView ?: return@getPlayerImageWithSpid
//                    playerDTO.imageUrl = it.toString()
//                    if (it == 0) {
//                        mDetailListView!!.showPlayerImage(ouid, playerDTO, size)
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
        CrawlingUtils().getPlayerImg(playerDTO, onSuccess, onFailed)
    }
}