package com.khs.figle_m.Data

import android.content.Context
import android.util.Log
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.UserHighRankResponse
import com.khs.figle_m.Response.UserResponse
import okhttp3.HttpUrl
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

class DataManager{
    val TAG: String = javaClass.name
    val DEBUG: Boolean = false

    var mContext: Context? = null

    fun init(context:Context?) {
        mContext = context
    }

    enum class matchType(val matchType: Int) {
        normalMatch(50),
        coachMatch(52)
    }

    val offset: Int = 0

//    200	OK	성공
//    301	Moved Permanently	HTTP 프로토콜로 호출
//    400	Bad Request	요청 형식 오류 (잘못된 파라미터 입력)
//    401	Unauthorized	미승인 서비스 (미지원 service, service type)
//    403	Forbidden	허용되지 않은 AccessToken 사용
//    404	Not Found	해당 리소스가 존재하지 않음
//    405	Method not allowed	미지원 API
//    413	Request Entity Too Large	너무 긴 요청 파라미터 입력
//    429	Too many request	AccessToken의 요청 허용량(Rate Limit) 초과
//    500	Internal Server Error	서버 내부 에러
//    504	Gateway Timeout	서버 내부 처리 timeout
    open val SEARCH_LIMIT: Int = 100
    open val SUCCESS_CODE: Int = 200
    open val ERROR_BAD_REQUEST = 400
    open val ERROR_UNAUTHORIZED = 401
    open val ERROR_FORBIDDEN = 403
    open val ERROR_NOT_FOUND= 404
    open val ERROR_METHOD_NOT_ALLOWED = 405
    open val ERROR_REQUEST_ENTITY_TOO_LARGE = 413
    open val ERROR_TOO_MANY_REQUEST = 429
    open val ERROR_INTERNAL_SERVER_ERROR = 500
    open val ERROR_GATEWAY_TIMEOUT = 504
    open val ERROR_NETWORK_DISCONNECTED = 990
    open val ERROR_OTHERS = 999

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

    fun loadUserData(
        nickName: String,
        onSuccess: (UserResponse?) -> Unit,
        onFailed: (Int) -> Unit
    ) {
        SearchUser.getApiService()
            .requestUser(authorization = mAuthorizationKey, nickname = nickName)
            .enqueue(object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    if (t is UnknownHostException) {
                        onFailed(maekErrorException(t))
                        return
                    }
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Log.v(TAG, "loadUserData response(...) ${response.code()}")
                    if (response != null && response!!.isSuccessful) {
                        if (response.code() == SUCCESS_CODE) {
                            onSuccess(response!!.body())
                        } else {
                            onFailed(response.code())
                        }
                    } else {
                        onFailed(response.code())
                    }
                }
            })
    }

    fun loadMatchDetail(
        matchId: String,
        onSuccess: ((MatchDetailResponse) -> Unit),
        onFailed: (Int) -> Unit
    ) {
        SearchUser.getApiService()
            .requestMatchDetail(authorization = mAuthorizationKey, matchid = matchId)
            .enqueue(object : Callback<MatchDetailResponse> {
                override fun onFailure(call: Call<MatchDetailResponse>, t: Throwable) {
                    if (t is UnknownHostException) {
                        onFailed(maekErrorException(t))
                        return
                    }
                }

                override fun onResponse(
                    call: Call<MatchDetailResponse>,
                    response: Response<MatchDetailResponse>
                ) {
//                    Log.v(TAG, "loadMatchDetail response(...) ${response.code()}")
                    if (DEBUG) Log.v(TAG, "response(...) ${response!!.body().toString()}")
                    if (response.code() == SUCCESS_CODE) {
                        onSuccess(response!!.body()!!)
                    } else {
                        onFailed(response.code())
                    }
                }
            })
    }

    fun loadMatchId(
        accessid: String,
        matchtype: Int,
        offset: Int?,
        limit: Int?,
        onSuccess: (ResponseBody?) -> Unit,
        onFailed: (Int) -> Unit
    ) {
        SearchUser.getApiService().requestMatchId(
            authorization = mAuthorizationKey,
            accessid = accessid,
            matchtype = matchtype,
            offset = offset,
            limit = limit
        )
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    if (t is UnknownHostException) {
                        onFailed(maekErrorException(t))
                        return
                    }
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.v(TAG, "loadMatchId onResponse(...) ::: ${response.code()}")
                    if (response.code() == SUCCESS_CODE) {
                        onSuccess(response!!.body())
                    } else {
                        onFailed(response.code())
                    }
                }
            })
    }

    fun loadUserHighRank(
        accessId: String,
        onSuccess: (List<UserHighRankResponse>) -> Unit,
        onFailed: (Int) -> Unit
    ) {
        SearchUser.getApiService()
            .requestUserHighRank(authorization = mAuthorizationKey, accessid = accessId)
            .enqueue(object : Callback<List<UserHighRankResponse>> {
                override fun onFailure(call: Call<List<UserHighRankResponse>>, t: Throwable) {
                    if (t is UnknownHostException) {
                        onFailed(maekErrorException(t))
                        return
                    }
                }

                override fun onResponse(
                    call: Call<List<UserHighRankResponse>>,
                    response: Response<List<UserHighRankResponse>>
                ) {
                    if (DEBUG) Log.v(TAG, "loadUserHighRank response(...) ${response.code()}")
                    if (response != null && response!!.isSuccessful) {
                        if (response.code() == SUCCESS_CODE) {
                            onSuccess(response!!.body()!!)
                        } else {
                            onFailed(response.code())
                        }
                    }
                }
            })
    }

    fun loadPlayerImage(
        spid: Int,
        onSuccess: ((HttpUrl) -> Unit),
        onFailed: (Int) -> Unit
    ) {
        val call = SearchUser.getServiceImage()
            .requestPlayerImage(authorization = mAuthorizationKey, spid = spid)
//        Log.v(TAG, "loadPlayerImage call : ${call.request()}")
        onSuccess(call.request().url())
    }

    fun loadSeasonIdList(onSuccess: ((ResponseBody) -> Unit), onFailed: (Int) -> Unit) {
        val call = SearchUser.getPlayerNameApiService()
            .requestSeasonIdList(authorization = mAuthorizationKey)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (t is UnknownHostException) {
                    onFailed(maekErrorException(t))
                    return
                }
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (DEBUG) Log.v(TAG, "loadSeasonIdList response(...) ${response.code()}")
                if (response != null && response!!.isSuccessful) {
                    if (response.code() == SUCCESS_CODE) {
                        onSuccess(response!!.body()!!)
                    } else {
                        onFailed(response.code())
                    }
                }
            }
        })
    }

    fun loadPlayerName(onSuccess: ((ResponseBody) -> Unit), onFailed: (Int) -> Unit) {
        val call = SearchUser.getPlayerNameApiService()
            .requestPlayerName(authorization = mAuthorizationKey)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (t is UnknownHostException) {
                    onFailed(maekErrorException(t))
                    return
                }
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (DEBUG) Log.v(TAG, "loadPlayerName response(...) ${response.code()}")
                if (response != null && response!!.isSuccessful) {
                    if (response.code() == SUCCESS_CODE) {
                        onSuccess(response!!.body()!!)
                    } else {
                        onFailed(response.code())
                    }
                }
            }
        })
    }

    fun loadRankerPlayerAverData(
        matchType: Int,
        players: String,
        onSuccess: (List<RankerPlayerDTO>) -> Unit,
        onFailed: (Int) -> Unit
    ) {
        if (DEBUG) Log.v(TAG, "TEST, players : ${players}")
        val call = SearchUser.getApiService()
            .requestRankerPlayerAverList(
                authorization = mAuthorizationKey,
                matchType = matchType,
                players = players
            )
        if (DEBUG) Log.v(TAG, "TEST, call : ${call.request()}")

        call.enqueue(object : Callback<List<RankerPlayerDTO>> {
            override fun onFailure(call: Call<List<RankerPlayerDTO>>, t: Throwable) {
                if (t is UnknownHostException) {
                    onFailed(maekErrorException(t))
                    return
                }
            }

            override fun onResponse(
                call: Call<List<RankerPlayerDTO>>,
                response: Response<List<RankerPlayerDTO>>
            ) {
                if (DEBUG) Log.v(TAG, "loadRankerPlayerAverData response(...) ${response.code()}")
                if (response != null && response!!.isSuccessful) {
                    if (response.code() == SUCCESS_CODE) {
                        onSuccess(response!!.body()!!)
                    } else {
                        onFailed(response.code())
                    }
                }
            }
        })
    }

    fun loadPlayerInfo(
        spid: Int,
        strong: Int,
        onSuccess: (ResponseBody) -> Unit,
        onFailed: (Int) -> Unit
    ) {
        val call = SearchUser.getCrawlingService()
            .requestPlayerInfo(spId = spid, strong = strong)

        if (DEBUG) Log.v(TAG, "TEST, Call : ${call.request()}")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (t is UnknownHostException) {
                    onFailed(maekErrorException(t))
                    return
                }
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                onSuccess(response.body()!!)
            }
        })
    }

    fun maekErrorException(t :Throwable) :Int {
        if (t is UnknownHostException) {
            return ERROR_NETWORK_DISCONNECTED
        } else {
         return ERROR_OTHERS
        }
    }
}