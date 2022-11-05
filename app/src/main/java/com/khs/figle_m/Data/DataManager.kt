package com.khs.figle_m.Data

import android.content.Context
import android.util.Log
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.TradeResponse
import com.khs.figle_m.Response.UserHighRankResponse
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.Trade.TradeHomeFragment
import com.khs.figle_m.Utils.DateUtils
import okhttp3.HttpUrl
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

class DataManager{
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = false

    var mContext: Context? = null

    fun init(context:Context?) {
        mContext = context
    }

//    {
//        "matchtype": 30,
//        "desc": "리그 친선"
//    },
//    {
//        "matchtype": 40,
//        "desc": "클래식 1on1"
//    },
//    {
//        "matchtype": 50,
//        "desc": "공식경기"
//    },
//    {
//        "matchtype": 52,
//        "desc": "감독모드"
//    },
//    {
//        "matchtype": 60,
//        "desc": "공식 친선"
//    },
//    {
//        "matchtype": 204,
//        "desc": "볼타 친선"
//    },
//    {
//        "matchtype": 214,
//        "desc": "볼타 공식"
//    },
//    {
//        "matchtype": 224,
//        "desc": "볼타 AI대전"
//    },
//    {
//        "matchtype": 234,
//        "desc": "볼타 커스텀"
//    }
//    ]

    enum class matchType(val matchType: Int) {
        normalMatch(50),
        coachMatch(52)
    }

    val offset: Int = 0

    open val SEARCH_LIMIT: Int = 100
    open val SEARCH_PAGE_SIZE: Int = 20

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
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJYLUFwcC1SYXRlLUxpbWl0IjoiNTAwOjEwIiwiYWNjb3VudF9pZCI6IjEyNDE1MjkyNjQiLCJhdXRoX2lkIjoiMiIsImV4cCI6MTY4MzE4MzQ0MSwiaWF0IjoxNjY3NjMxNDQxLCJuYmYiOjE2Njc2MzE0NDEsInNlcnZpY2VfaWQiOiI0MzAwMTE0ODEiLCJ0b2tlbl90eXBlIjoiQWNjZXNzVG9rZW4ifQ.fSaIlCKts-yIgg_65oEX6k1z6D-EWnZGToShWrDIulU"

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
                        onFailed(makeErrorException(t))
                        return
                    }
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Log.v(TAG, "loadUserData response(...) ${response.code()}")
                    if (response != null) {
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

    fun loadMatchDetailWrapper(matchId: String,
                               onSuccess: ((MatchDetailResponse) -> Unit),
                               onFailed: (String) -> Unit) {
        loadMatchDetail(matchId, onSuccess, {
            onFailed(matchId)
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
                        onFailed(makeErrorException(t))
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
        ).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    if (t is UnknownHostException) {
                        onFailed(makeErrorException(t))
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
                        onFailed(makeErrorException(t))
                        return
                    }
                }

                override fun onResponse(
                    call: Call<List<UserHighRankResponse>>,
                    response: Response<List<UserHighRankResponse>>
                ) {
                    if (DEBUG) Log.v(TAG, "loadUserHighRank response(...) ${response.code()}")
                    if (response != null) {
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
                    onFailed(makeErrorException(t))
                    return
                }
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (DEBUG) Log.v(TAG, "loadSeasonIdList response(...) ${response.code()}")
                if (response != null) {
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
                    onFailed(makeErrorException(t))
                    return
                }
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (DEBUG) Log.v(TAG, "loadPlayerName response(...) ${response.code()}")
                if (response != null) {
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
                    onFailed(makeErrorException(t))
                    return
                }
            }

            override fun onResponse(
                call: Call<List<RankerPlayerDTO>>,
                response: Response<List<RankerPlayerDTO>>
            ) {
                if (DEBUG) Log.v(TAG, "loadRankerPlayerAverData response(...) ${response.code()}")
                if (response != null) {
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
                    onFailed(makeErrorException(t))
                    return
                }
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                onSuccess(response.body()!!)
            }
        })
    }

    //    http://fifaonline4.nexon.com/datacenter/rank?n4pageno=3
    fun loadRaking(
        page: Int,
        onSuccess: (ResponseBody) -> Unit,
        onFailed: (Int) -> Unit
    ) {
        val call = SearchUser.getCrawlingService()
            .requestRaking(page = page)

        /*if (DEBUG) */Log.v(TAG, "TEST, Call : ${call.request()}")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (t is UnknownHostException) {
                    onFailed(makeErrorException(t))
                    return
                }
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                onSuccess(response.body()!!)
            }
        })
    }

    fun loadTradeInfo(
        accessId: String,
        tradeType: TradeHomeFragment.TradeType,
        offset: Int?,
        limit: Int?,
        onSuccess: (List<TradeResponse>) -> Unit,
        onFailed: (Int) -> Unit
    ) {
        val call = SearchUser.getApiService().requestTradeInfo(
            authorization = mAuthorizationKey,
            accessid = accessId,
            tradeType = tradeType.name,
            offset = offset,
            limit = limit
        )
        if (DEBUG) Log.v(TAG,"loadTradeInfo : ${call.request()}")

        call.enqueue(object : Callback<List<TradeResponse>> {
            override fun onFailure(call: Call<List<TradeResponse>>, t: Throwable) {
                Log.d(TAG,"onFailure(...) : $t")
                if (t is UnknownHostException) {
                    onFailed(makeErrorException(t))
                    return
                }
            }

            override fun onResponse(call: Call<List<TradeResponse>>, response: Response<List<TradeResponse>>) {
                Log.v(TAG,"loadTradeInfo(...)")
                if (response.body() == null) {
                    onFailed(tradeType.ordinal)
                    return
                }
                onSuccess(response.body()!!.apply {
                    for (item in this){
                        item.tradeType = tradeType.ordinal
                        item.tradeDateMs = DateUtils().getDate(item.tradeDate)
                    }
                })
            }
        })
    }

    fun makeErrorException(t :Throwable) :Int {
        if (t is UnknownHostException) {
            return ERROR_NETWORK_DISCONNECTED
        } else {
         return ERROR_OTHERS
        }
    }
}