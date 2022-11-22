package com.khs.figle_m

import android.content.Context
import android.util.Log
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.DB.PlayerEntity
import com.khs.figle_m.DB.SeasonEntity
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody

class InitPresenter : InitContract.Presenter {
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = false
    var mInitContract: InitContract.View? = null

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
                    LogUtil.dLog(LogUtil.TAG_SETUP, TAG,"getSeasonIdList Success! $it")
                    updateSeasonDB(context,it) {
                        LogUtil.vLog(LogUtil.TAG_SETUP, TAG, "SeasonList save successful")
                    }
                }, {
                    LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"getSeasonIdList Failed! $it")
                    mInitContract?.showError(it)
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
                        LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"getPlayerNameList Success! ${it}")
                        mInitContract?.showMainActivity(it)
                        updatePlayerDB(context, it
                        ) {
                            LogUtil.vLog(LogUtil.TAG_SETUP, TAG, "PlayerName save successful")
                            CoroutineScope(Dispatchers.Main).launch {
                                mInitContract?.hideLoading()
                            }
                        }
                    }, {
                        LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"getPlayerNameList Failed! ${it}")
                        mInitContract?.hideLoading()
                        mInitContract?.showError(it)
                    })
            }
        }
    }

    fun getPlayerNameList(onSuccess: (ResponseBody) -> Unit, onFailed: (Int) -> Unit) {
        DataManager.getInstance().loadPlayerName({
            LogUtil.dLog(LogUtil.TAG_SETUP, TAG,"getPlayerNameList Success! $it")
            onSuccess(it)
        }, {
            LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"getPlayerNameList Failed! $it")
            onFailed(it)
        })
    }

    fun updateSeasonDB(context: Context, responseBody: ResponseBody, onSuccess: () -> Unit) {
        LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"updateSeasonDB(...)")
        CoroutineScope(Dispatchers.IO).launch {
            var result: String = responseBody.string()
            val stringList: List<String> =
                result.removeSurrounding("[", "]").replace("\"", "").replace("seasonId:", "")
                    .replace("className:", "").replace("seasonImg:","").replace("\n", "")
                    .replace(" ", "").replace("{", "")
                    .replace("}", "").split(",")
            var index = 0


            val seasonDB = PlayerDataBase.getInstance(context)
            LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"seasonList size : ${stringList.size}")
            val seasonList : ArrayList<SeasonEntity> = arrayListOf()
            for (item in 0..stringList.size - 1 step 3) {
                val loIndex = index
                val seasonId = stringList[index]
                val className = stringList[++index]
                val seasonImg = stringList[++index]
                LogUtil.dLog(LogUtil.TAG_SETUP, TAG,"index : $loIndex , seasonId : $seasonId , className : $className , seasonImg : $seasonImg")
                seasonList.add(SeasonEntity(null, seasonId.toLong(), className, seasonImg))
                index++
            }
            val startTime = System.currentTimeMillis()
            val localPlayerList = seasonDB!!.seasonDao().getAll()
            if(seasonList.size != localPlayerList.size) {
                index = 0
                LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"------------------ update Player DB, Season Table  ------------------")
                seasonDB!!.seasonDao().deleteAll()
                for (item in 0..stringList.size - 1 step 3) {
                    val loIndex = index
                    val seasonId = stringList[index]
                    val className = stringList[++index]
                    val seasonImg = stringList[++index]
                    LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"index : $loIndex , seasonId : $seasonId , className : $className , seasonImg : $seasonImg")
                    seasonDB!!.seasonDao().insert(SeasonEntity(null, seasonId.toLong(), className, seasonImg))
                    index++
                }
                LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"------------------ EndTime : ${System.currentTimeMillis()-startTime} ------------------")
            } else {
                LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"------------------ Same DB ------------------")
                LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"------------------ Local PlayerDB, Season Table Size : ${localPlayerList.size} ------------------")
                LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"------------------ Server PlayerDB, Season Table Size : ${seasonList.size} ------------------")
            }
            onSuccess()
        }
    }

    fun updatePlayerDB(context: Context, responseBody: ResponseBody, onSuccess: () -> Unit) {
        LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"updatePlayerDB(...)")
        CoroutineScope(Dispatchers.IO).launch {
            var result: String = responseBody.string()
            val stringList: List<String> =
                result.removeSurrounding("[", "]").replace("\"", "").replace("id:", "")
                    .replace("name:", "").replace("\n", "").replace(" ", "").replace("{", "")
                    .replace("}", "").split(",")
            var index = 0
            val playerDB = PlayerDataBase.getInstance(context)
            LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"stringList size : ${stringList.size}")
            val playerList : ArrayList<PlayerEntity> = arrayListOf()
            for (item in 0..stringList.size - 1 step 2) {
                val loIndex = index
                val key = stringList[index]
                val value = stringList[++index]
                LogUtil.dLog(LogUtil.TAG_SETUP, TAG,"index : $loIndex , key : $key , value : $value")
                playerList.add(PlayerEntity(null,key,value))
                index++
            }
            val startTime = System.currentTimeMillis()
            val localPlayerList = playerDB!!.playerDao().getAll()
            if(playerList.size != localPlayerList.size) {
                mInitContract?.setProgressMax(stringList.size)
                index = 0
                LogUtil.dLog(LogUtil.TAG_SETUP, TAG,"------------------ update Player DB ------------------")
                playerDB?.playerDao().deleteAll()
                for (item in 0..stringList.size - 1 step 2) {
                    val loIndex = index
                    val key = stringList[index]
                    val value = stringList[++index]
                    LogUtil.dLog(LogUtil.TAG_SETUP, TAG,"updatePlayerDB - index : $loIndex , key : $key , value : $value")
                    playerDB!!.playerDao().insert(PlayerEntity(null,key,value))
                    mInitContract?.updateProgress(index)
                    index++
                }
                mInitContract?.updateProgress(stringList.size)
                LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"------------------ EndTime : ${System.currentTimeMillis()-startTime} ------------------")

            } else {
                LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"------------------ Same DB ------------------")
                LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"------------------ Local PlayerDB Size : ${localPlayerList.size} ------------------")
                LogUtil.vLog(LogUtil.TAG_SETUP, TAG,"------------------ Server PlayerDB Size : ${playerList.size} ------------------")
            }
            onSuccess()
        }
    }
}