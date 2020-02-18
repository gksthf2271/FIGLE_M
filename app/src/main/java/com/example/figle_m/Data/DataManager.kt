package com.example.figle_m.Data

import android.content.Context
import android.util.Log
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserMatchIdResponse
import com.example.figle_m.Response.UserResponse
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Response
import java.util.*

class DataManager {

    val TAG: String = javaClass.name

    var mContext: Context? = null

    fun DataManager(context: Context) {
        mContext = context
    }

    enum class matchType(val matchType: Int) {
        normalMatch( 50),
        coachMatch(51)
    }

    val offset: Int = 0
    val limit: Int = 20

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

    fun loadUserData(nickName: String): UserResponse? {
        var response: Response<UserResponse>? = null
        var userResponse: UserResponse? = null
        response = SearchUser.getService().requestUser(
            authorization = mAuthorizationKey,
            nickname = nickName
        ).execute()
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
        response = SearchUser.getService().requestMatchDetail(
            authorization = mAuthorizationKey,
            matchid = matchId
        ).execute()
        Log.v(TAG, "response(...) $response")
        Log.v(TAG, "response(...) ${response!!.body().toString()}")
        if (response != null && response!!.isSuccessful) {
            matchResponse = response!!.body()
        }
        return matchResponse
    }

    fun loadMatchId(
        accessid: String,
        matchtype: Int,
        offset: Int?,
        limit: Int?
    ): ResponseBody? {
        var response: Response<ResponseBody>? = null
        var usermatchIdResponse: ResponseBody? = null
        response = SearchUser.getService().requestMatchId(
            authorization = mAuthorizationKey,
            accessid = accessid,
            matchtype = matchtype,
            offset = offset,
            limit = limit
        ).execute()
        Log.v(TAG, "response(...) $response")
        Log.v(TAG, "response(...) ${response!!.body().toString()}")
        if (response != null && response!!.isSuccessful) {
            usermatchIdResponse = response!!.body()
        }
        return usermatchIdResponse
    }

}