package com.khs.figle_m.SearchList.SearchDetailView

import android.util.Log
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.DTO.PlayerDTO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import org.json.JSONArray
import org.json.JSONObject

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
    override fun getPlayerImage(spid: Int) {
        mDetailListView?.showLoading()
        runBlocking {
            launch {
                getPlayerImage(spid, {
                    if (DEBUG) Log.v(TAG, "SearchPresenter getMatchDetailList: $it")
                    mDetailListView?.showPlayerImage(spid, it)
                }, {
                    Log.v(TAG, "Result : getMatchDetailList response : $it")
                    mDetailListView?.showError(ERROR_EMPTY)
                    mDetailListView?.hideLoading()
                })

            }
        }
    }

    /*DummyData
    [{"id":101001183,"po":7}, {"id":214003647,"po":25},…]*/
    override fun getRankerPlayerList(matchType: Int, playerDTO: PlayerDTO) {
        mDetailListView?.showLoading()
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
        spid: Int,
        onSuccess: ((HttpUrl) -> Unit),
        onFailed: (String) -> Unit
    ) {
        DataManager.getInstance().loadPlayerImage(spid,
            {
                if (DEBUG) Log.v(
                    TAG,
                    "getPlayerImage Success! $it"
                )
                onSuccess(it)
            }, {
                Log.v(TAG, "getPlayerImage Failed! $it")
                onFailed(it)
            })

    }
}