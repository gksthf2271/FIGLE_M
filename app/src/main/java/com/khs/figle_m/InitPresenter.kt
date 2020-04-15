package com.khs.figle_m

import android.content.Context
import android.util.Log
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.DB.PlayerEntity
import com.khs.figle_m.DB.SeasonEntity
import com.khs.figle_m.Data.DataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody

class InitPresenter : InitContract.Presenter {
    val TAG: String = javaClass.name
    val isDebug: Boolean = false
    var mInitContract: InitContract.View? = null

    open val ERROR_EMPTY = "EMPTY"

    override fun takeView(view: InitContract.View) {
        mInitContract = view
    }

    override fun dropView() {
        mInitContract = null
    }

    override fun getSeasonIdList(context: Context) {
        mInitContract?.showLoading()
        runBlocking {
            launch {
                DataManager.getInstance().loadSeasonIdList({
                    if (isDebug) Log.v(TAG, "getSeasonIdList Success! $it")
                    updateSeasonDB(context,it,{
                        Log.v(TAG, "SeasonList save successful")
                    })
                }, {
                    Log.v(TAG, "getSeasonIdList Failed! $it")
                })
            }
        }
    }

    override fun getPlayerNameList(context: Context) {
        mInitContract?.showLoading()
        runBlocking {
            launch {
                getPlayerNameList(
                    {
                        Log.v(TAG, "getPlayerNameList Success! ${it}")
                        mInitContract?.showMainActivity(it)
                        updatePlayerDB(context, it
                            , {
                                Log.v(TAG, "PlayerName save successful")
                                CoroutineScope(Dispatchers.Main).launch {
                                mInitContract?.hideLoading()
                                }
                            }
                        )
                    }, {
                        Log.v(TAG, "getPlayerNameList Failed! ${it}")
                        mInitContract?.hideLoading()
                    })
            }
        }
    }

    fun getPlayerNameList(onSuccess: (ResponseBody) -> Unit, onFailed: (String) -> Unit) {
        DataManager.getInstance().loadPlayerName({
            if (isDebug) Log.v(TAG, "getPlayerNameList Success! $it")
            onSuccess(it)
        }, {
            Log.v(TAG, "getPlayerNameList Failed! $it")
            onFailed(it)
        })
    }

    fun updateSeasonDB(context: Context, responseBody: ResponseBody, onSuccess: () -> Unit) {
        Log.v(TAG, "updateSeasonDB(...)")
        CoroutineScope(Dispatchers.IO).launch {
            var result: String = responseBody.string()
            val stringList: List<String> =
                result.removeSurrounding("[", "]").replace("\"", "").replace("seasonId:", "")
                    .replace("className:", "").replace("seasonImg:","").replace("\n", "")
                    .replace(" ", "").replace("{", "")
                    .replace("}", "").split(",")
            var index = 0


            val seasonDB = PlayerDataBase.getInstance(context)
            Log.v(TAG, "seasonList size : ${stringList.size}")
            val seasonList : ArrayList<SeasonEntity> = arrayListOf()
            for (item in 0..stringList.size - 1 step 3) {
                val loIndex = index
                val seasonId = stringList[index]
                val className = stringList[++index]
                val seasonImg = stringList[++index]
                if (isDebug) Log.v(TAG, "index : $loIndex , seasonId : $seasonId , className : $className , seasonImg : $seasonImg")
                seasonList.add(SeasonEntity(null, seasonId.toLong(), className, seasonImg))
                index++
            }
            val startTime = System.currentTimeMillis()
            val localPlayerList = seasonDB!!.seasonDao().getAll()
            if(seasonList.size != localPlayerList.size) {
                index = 0
                Log.v(TAG,"------------------ update Player DB, Season Table  ------------------")
                seasonDB!!.seasonDao().deleteAll()
                for (item in 0..stringList.size - 1 step 3) {
                    val loIndex = index
                    val seasonId = stringList[index]
                    val className = stringList[++index]
                    val seasonImg = stringList[++index]
                    Log.v(TAG, "index : $loIndex , seasonId : $seasonId , className : $className , seasonImg : $seasonImg")
                    seasonDB!!.seasonDao().insert(SeasonEntity(null, seasonId.toLong(), className, seasonImg))
                    index++
                }
                Log.v(TAG,"------------------ EndTime : ${System.currentTimeMillis()-startTime} ------------------")
            } else {
                Log.v(TAG,"------------------ Same DB ------------------")
                Log.v(TAG,"------------------ Local PlayerDB, Season Table Size : ${localPlayerList.size} ------------------")
                Log.v(TAG,"------------------ Server PlayerDB, Season Table Size : ${seasonList.size} ------------------")
            }
            onSuccess()
        }
    }

    fun updatePlayerDB(context: Context, responseBody: ResponseBody, onSuccess: () -> Unit) {
        Log.v(TAG, "updatePlayerDB(...)")
        CoroutineScope(Dispatchers.IO).launch {
            var result: String = responseBody.string()
            val stringList: List<String> =
                result.removeSurrounding("[", "]").replace("\"", "").replace("id:", "")
                    .replace("name:", "").replace("\n", "").replace(" ", "").replace("{", "")
                    .replace("}", "").split(",")
            var index = 0
            val playerDB = PlayerDataBase.getInstance(context)
            Log.v(TAG, "stringList size : ${stringList.size}")
            val playerList : ArrayList<PlayerEntity> = arrayListOf()
            for (item in 0..stringList.size - 1 step 2) {
                val loIndex = index
                val key = stringList[index]
                val value = stringList[++index]
                if(isDebug) Log.v(TAG, "index : $loIndex , key : $key , value : $value")
                playerList.add(PlayerEntity(null,key,value))
                index++
            }
            val startTime = System.currentTimeMillis()
            val localPlayerList = playerDB!!.playerDao().getAll()
            if(playerList.size != localPlayerList.size) {
                mInitContract!!.setProgressMax(stringList.size)
                index = 0
                if(isDebug) Log.v(TAG,"------------------ update Player DB ------------------")
                playerDB!!.playerDao().deleteAll()
                for (item in 0..stringList.size - 1 step 2) {
                    val loIndex = index
                    val key = stringList[index]
                    val value = stringList[++index]
                    if(isDebug) Log.v(TAG, "updatePlayerDB - index : $loIndex , key : $key , value : $value")
                    playerDB!!.playerDao().insert(PlayerEntity(null,key,value))
                    mInitContract!!.updateProgress(index)
                    index++
                }
                mInitContract!!.updateProgress(stringList.size)
                Log.v(TAG,"------------------ EndTime : ${System.currentTimeMillis()-startTime} ------------------")

            } else {
                Log.v(TAG,"------------------ Same DB ------------------")
                Log.v(TAG,"------------------ Local PlayerDB Size : ${localPlayerList.size} ------------------")
                Log.v(TAG,"------------------ Server PlayerDB Size : ${playerList.size} ------------------")
            }
            onSuccess()
        }
    }
}