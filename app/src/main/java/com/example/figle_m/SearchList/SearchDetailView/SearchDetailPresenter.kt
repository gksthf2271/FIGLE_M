package com.example.figle_m.SearchList.SearchDetailView

import android.util.Log
import com.example.figle_m.Data.DataManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.ResponseBody

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
                    mDetailListView?.showPlayerImage(it)
                }, {
                    Log.v(TAG, "Result : getMatchDetailList response : $it")
                    mDetailListView?.showError(ERROR_EMPTY)
                    mDetailListView?.hideLoading()
                })

            }
        }
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