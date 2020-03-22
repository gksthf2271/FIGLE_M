package com.example.figle_m

import android.content.Context
import android.util.Log
import com.example.figle_m.Data.DataManager
import com.example.figle_m.utils.SharedPreferenceUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody

class InitPresenter : InitContract.Presenter {
    val TAG: String = javaClass.name
    val DEBUG: Boolean = false
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
                        mPlayerNameList?.hideLoading()
                        updatePlayerListPref(context, it
                            , {
                                Log.v(TAG, "pref save successful")
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
            if (DEBUG) Log.v(TAG, "getPlayerNameList Success! $it")
            onSuccess(it)
        }, {
            Log.v(TAG, "getPlayerNameList Failed! $it")
            onFailed(it)
        })
    }

    fun updatePlayerListPref(context: Context, responseBody: ResponseBody, onSuccess: () -> Unit) {
        Log.v(TAG, "updatePlayerListPref(...)")
        runBlocking {
            launch {
                var result: String = responseBody.string()
                val stringList: List<String> =
                    result.removeSurrounding("[", "]").replace("\"", "").replace("id:", "")
                        .replace("name:", "").replace("\n", "").replace(" ", "").replace("{", "")
                        .replace("}", "").split(",")
                var index = 0
                Log.v(TAG, "stringList size : ${stringList.size}")
                for (item in 0..stringList.size-1 step 2) {
                    val loIndex = index
                    val key = stringList[index]
                    val value = stringList[++index]
                    Log.v(
                        TAG,
                        "updatePlayerListPref - index : $loIndex , key : $key , value : $value"
                    )
                    SharedPreferenceUtil().savePref(context, MainActivity().PREF_NAME, key, value
                    )
                    index++
                }
            }
        }
        onSuccess
    }
}