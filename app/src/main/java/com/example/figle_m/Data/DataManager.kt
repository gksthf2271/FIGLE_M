package com.example.figle_m.Data

import android.content.Context
import android.util.Log
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserResponse
import retrofit2.Response

class DataManager {

    val TAG:String = javaClass.name

    var mContext:Context? = null

    fun DataManager(context: Context) {
        mContext = context
    }

    companion object {
        @Volatile private var instance: DataManager? = null

        @JvmStatic fun getInstance(): DataManager =
            instance ?: synchronized(this) {
                instance
                    ?: DataManager().also {
                    instance = it
                }
            }
    }

    fun loadUserData(nickName: String): UserResponse? {
        var response: Response<UserResponse>? = null
        var userResponse: UserResponse? = null
        response = SearchUser.getService().requestUser(nickname = nickName).execute()
        Log.v(TAG, "response(...) $response")
        Log.v(TAG, "response(...) ${response!!.body().toString()}")
        if (response != null && response!!.isSuccessful) {
            userResponse = response!!.body()
        }
        return userResponse
    }

    fun loadMatchDetail(matchId: String): MatchDetailResponse? {
        var response: Response<MatchDetailResponse>? = null
        var matchResponse: MatchDetailResponse? = null
        response = SearchUser.getService().requestMatchDetail(/*matchid = matchId*/).execute()
        Log.v(TAG, "response(...) $response")
        Log.v(TAG, "response(...) ${response!!.body().toString()}")
        if (response != null && response!!.isSuccessful) {
            matchResponse = response!!.body()
        }
        return matchResponse
    }

}