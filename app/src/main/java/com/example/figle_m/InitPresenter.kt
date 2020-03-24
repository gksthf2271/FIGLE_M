package com.example.figle_m

import android.content.Context
import android.util.Log
import com.example.figle_m.DB.PlayerDataBase
import com.example.figle_m.DB.PlayerEntity
import com.example.figle_m.Data.DataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody

class InitPresenter : InitContract.Presenter {
    val TAG: String = javaClass.name
    val isDebug: Boolean = false
    var mPlayerNameList: InitContract.View? = null

    open val ERROR_EMPTY = "EMPTY"

    override fun takeView(view: InitContract.View) {
        mPlayerNameList = view
    }

    override fun dropView() {
        mPlayerNameList = null
    }

    override fun getPlayerNameList(context: Context) {
        mPlayerNameList?.showLoading()
        runBlocking {
            launch {
                getPlayerNameList(
                    {
                        Log.v(TAG, "getPlayerNameList Success! ${it}")
                        mPlayerNameList?.showMainActivity(it)
                        updatePlayerDB(context, it
                            , {
                                Log.v(TAG, "pref save successful")
                                CoroutineScope(Dispatchers.Main).launch {
                                mPlayerNameList?.hideLoading()
                                }
                            }
                        )
                    }, {
                        Log.v(TAG, "getPlayerNameList Failed! $it")
                        mPlayerNameList?.hideLoading()
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
                index = 0
                Log.v(TAG,"------------------ update Player DB ------------------")
                playerDB!!.playerDao().deleteAll()
                for (item in 0..stringList.size - 1 step 2) {
                    val loIndex = index
                    val key = stringList[index]
                    val value = stringList[++index]
                    Log.v(TAG, "updatePlayerDB - index : $loIndex , key : $key , value : $value")
                    playerDB!!.playerDao().insert(PlayerEntity(null,key,value))
                    index++
                }

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