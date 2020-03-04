package com.example.figle_m.Data

import android.content.Context
import android.util.Log
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataManager {

    val TAG: String = javaClass.name

    var mContext: Context? = null

    fun DataManager(context: Context) {
        mContext = context
    }

    enum class matchType(val matchType: Int) {
        normalMatch(50),
        coachMatch(51)
    }

    val offset: Int = 0
    open val SEARCH_LIMIT: Int = 100
    open val SUCCESS_CODE: Int = 200

    private val mAuthorizationKey: String =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTI0MTUyOTI2NCIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTQ4MSIsIlgtQXBwLVJhdGUtTGltaXQiOiIyMDAwMDoxMCIsIm5iZiI6MTU3NjkxNjU0MSwiZXhwIjoxNjM5OTg4NTQxLCJpYXQiOjE1NzY5MTY1NDF9.emF4Bd9O7zbC1giC4s3IrZ4S8Oax6-5IhDe3nZ0gCi4"

    companion object {
        @Volatile
        private var instance: DataManager? = null

        @JvmStatic
        fun getInstance(): DataManager =
            instance ?: synchronized(this) {
                instance
                    ?: DataManager().also {
                        instance = it
                    }
            }
    }

    fun loadUserData(nickName: String, onSuccess: (UserResponse?) -> Unit, onFailed: (String) -> Unit) {
        SearchUser.getService().requestUser(authorization = mAuthorizationKey, nickname = nickName)
            .enqueue(object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    onFailed("Failed! " + t)
                }

                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    Log.v(TAG, "loadUserData response(...) ${response.code()}")
                    if (response != null && response!!.isSuccessful) {
                        if (response.code() == SUCCESS_CODE) {
                            onSuccess(response!!.body())
                        } else {
                            onFailed("Failed! errorcode : " + response.code())
                        }
                    }
                }
            })
    }

    fun loadMatchDetail(matchId: String, onSuccess: ((MatchDetailResponse) -> Unit), onFailed: (String) -> Unit) {
        SearchUser.getService()
            .requestMatchDetail(authorization = mAuthorizationKey, matchid = matchId)
            .enqueue(object : Callback<MatchDetailResponse> {
                override fun onFailure(call: Call<MatchDetailResponse>, t: Throwable) {
                    onFailed("Failed! : $matchId")
                }

                override fun onResponse(
                    call: Call<MatchDetailResponse>,
                    response: Response<MatchDetailResponse>
                ) {
//                    Log.v(TAG, "loadMatchDetail response(...) ${response.code()}")
                    Log.v(TAG, "response(...) ${response!!.body().toString()}")
                    if (response.code() == SUCCESS_CODE) {
                        onSuccess(response!!.body()!!)
                    } else {
                        onFailed(response.code().toString())
                    }
                }
            })
    }

    fun loadMatchId(accessid: String, matchtype: Int, offset: Int?, limit: Int?, onSuccess: (ResponseBody?) -> Unit, onFailed: (String) -> Unit) {
        SearchUser.getService().requestMatchId(authorization = mAuthorizationKey, accessid = accessid, matchtype = matchtype, offset = offset, limit = limit)
            .enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onFailed("Failed! " + t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.v(TAG, "loadMatchId onResponse(...) ::: ${response.code()}")
                if (response.code() == SUCCESS_CODE) {
                    onSuccess(response!!.body())
                } else {
                    onFailed("Failed! errorcode : " + response.code())
                }
            }
        })
    }

}