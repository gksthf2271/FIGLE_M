package com.khs.data.nexon_api.response

import com.khs.data.nexon_api.response.DTO.DefenceDTO
import com.khs.data.nexon_api.response.DTO.MatchDetailDTO
import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import com.khs.data.nexon_api.response.DTO.PassDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.data.nexon_api.response.DTO.RankerPlayerStatDTO
import com.khs.data.nexon_api.response.DTO.SeasonDTO
import com.khs.data.nexon_api.response.DTO.ShootDTO
import com.khs.data.nexon_api.response.DTO.ShootDetailDTO
import com.khs.data.nexon_api.response.DTO.StatusDTO
import com.khs.domain.database.entity.PlayerData
import com.khs.domain.database.entity.Season
import com.khs.domain.nexon.entity.DefenceInfo
import com.khs.domain.nexon.entity.HighRankUser
import com.khs.domain.nexon.entity.Match
import com.khs.domain.nexon.entity.MatchDetailInfo
import com.khs.domain.nexon.entity.MatchInfo
import com.khs.domain.nexon.entity.PassInfo
import com.khs.domain.nexon.entity.Player
import com.khs.domain.nexon.entity.RankerPlayer
import com.khs.domain.nexon.entity.RankerPlayerStat
import com.khs.domain.nexon.entity.ShootDetailInfo
import com.khs.domain.nexon.entity.ShootInfo
import com.khs.domain.nexon.entity.StatusInfo
import com.khs.domain.nexon.entity.TradeInfo
import com.khs.domain.nexon.entity.User
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call

fun UserResponse.asUser(): User = User(
    accessId = ouid,
    nickname = nickname,
    level = level,
    teamPrice = teamPrice,
)

fun MatchDetailResponse.asMatch(): Match = Match(
    matchId = matchId,
    matchDate = matchDate,
    matchType = matchType,
    matchInfo = matchInfo.map {
        it.asMatchInfo()
    }
)

fun ResponseBody.asMatchIds(): List<String> =
    string().removeSurrounding("[", "]").replace("\"", "").split(",")

fun List<UserCareerHighResponse>.asHighRanker() : List<HighRankUser> = map {
    HighRankUser(
        matchType = it.matchType,
        division = it.division,
        achievementDate = it.achievementDate
    )
}

fun List<TradeResponse>.asTradeInfo() : List<TradeInfo> = map {
    TradeInfo(
        tradeDate = it.tradeDate,
        tradeDateMs = it.tradeDateMs,
        saleSn = it.saleSn,
        spid = it.spid,
        grade = it.grade,
        value = it.value,
        tradeType = it.tradeType,
        imageResUrl = it.imageResUrl
    )
}

fun ResponseBody.asImageByteArray() : ByteArray = this.bytes()

fun List<RankerPlayerDTO>.asRankerPlayers(): List<RankerPlayer> =
    map {
        RankerPlayer(
            spId = it.spId,
            spPosition = it.spPosition,
            createDate = it.createDate,
            status = it.status.asRankerPlayerStat()
        )
    }

fun RankerPlayerStatDTO.asRankerPlayerStat() : RankerPlayerStat = RankerPlayerStat(
    shoot = shoot,
    effectiveShoot = effectiveShoot,
    assist = assist,
    goal = goal,
    dribble = dribble,
    passTry = passTry,
    passSuccess = passSuccess,
    block = block,
    tackle = tackle,
    matchCount = matchCount
)

fun MatchInfoDTO.asMatchInfo(): MatchInfo = MatchInfo(
    accessId = ouid,
    nickname = nickname,
    matchDetailInfo = matchDetail.asMatchDetail(),
    shoot = shoot.asShootInfo(),
    shootDetail = shootDetail.map {
        it.asShootDetailInfo()
    },
    pass = pass.asPassInfo(),
    defence = defence.asDefenceInfo(),
    player = player.map {
        it.asPlayer()
    }
)

fun MatchDetailDTO.asMatchDetail() : MatchDetailInfo = MatchDetailInfo(
    seasonId = seasonId,
    matchResult = matchResult,
    matchEndType = matchEndType,
    systemPause = systemPause,
    foul = foul,
    injury = injury,
    redCards = redCards,
    yellowCards = yellowCards,
    dribble = dribble,
    cornerKick = cornerKick,
    possession = possession
)

fun ShootDTO.asShootInfo() : ShootInfo = ShootInfo(
    shootTotal = shootTotal,
    effectiveShootTotal = effectiveShootTotal,
    shootOutScore = shootOutScore,
    goalTotal = goalTotal,
    goalTotalDisplay = goalTotalDisplay,
    ownGoal = ownGoal,
    shootHeading = shootHeading,
    goalHeading = goalHeading,
    shootFreekick = shootFreekick,
    goalFreekick = goalFreekick,
    shootInPenalty = shootInPenalty,
    goalInPenalty = goalInPenalty,
    shootOutPenalty = shootOutPenalty,
    goalOutPenalty = goalOutPenalty,
    shootPenaltyKick = shootPenaltyKick,
    goalPenaltyKick = goalPenaltyKick
)

fun ShootDetailDTO.asShootDetailInfo() : ShootDetailInfo = ShootDetailInfo(
    goalTime = goalTime,
    x = x,
    y = y,
    type = type,
    result = result,
    assist = assist,
    hitPost = hitPost,
    inPenalty = inPenalty
)

fun PassDTO.asPassInfo() : PassInfo = PassInfo(
    passTry = passTry,
    passSuccess = passSuccess,
    shortPassTry = shortPassTry,
    shortPassSuccess = shortPassSuccess,
    longPassTry = longPassTry,
    longPassSuccess = longPassSuccess,
    bouncingLobPassTry = bouncingLobPassTry,
    bouncingLobPassSuccess = bouncingLobPassSuccess,
    drivenGroundPassTry = drivenGroundPassTry,
    drivenGroundPassSuccess = drivenGroundPassSuccess,
    throughPassTry = throughPassTry,
    throughPassSuccess = throughPassSuccess,
    lobbedThroughPassTry = lobbedThroughPassTry,
    lobbedThroughPassSuccess = lobbedThroughPassSuccess
)

fun DefenceDTO.asDefenceInfo() : DefenceInfo = DefenceInfo(
    blockTry = blockTry,
    blockSuccess = blockSuccess,
    tackleTry = tackleTry,
    tackleSuccess = tackleSuccess
)

fun PlayerDTO.asPlayer() : Player = Player(
    spId = spId,
    spPosition = spPosition,
    spGrade = spGrade,
    status = status.asStatus(),
    imageUrl = imageUrl,
    subImageUrl = subImageUrl
)

fun StatusDTO.asStatus() : StatusInfo = StatusInfo(
    shoot = shoot,
    effectiveShoot = effectiveShoot,
    assist = assist,
    goal = goal,
    dribble = dribble,
    passTry = passTry,
    passSuccess = passSuccess,
    block = block,
    tackle = tackle,
    spRating = spRating
)

fun List<SeasonDTO>.asSeasonList() : List<Season> {
    return this.map {
        Season(id = null, seasonId = it.seasonId.toLong(), className = it.className, seasonImg = it.seasonImg)
    }
}

/* TODO 23.08.17
    - MVP -> MVVM 전환
    - 앱 초기 DB 셋팅 로직 개선(선수 이름 json 파일 내재화)
      > response 헤더 값을 가지고 업데이트 시기 조정
    - 넥슨 API 각 Usecase 별 구현
    */
fun Flow<Call<ResponseBody>>.checkModified() : PlayerData {
//    val resultCB = object : Callback<ResponseBody> {
//        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//            val headers = response.headers()
//            //            age : 970
////            content-length : 4577454
////            last-modified : Mon, 04 Sep 2023 03:39:16 GMT
//            val jsonObject = JSONObject(response.body() as Map<*, *>)
//            val jsonString = jsonObject.toString()
//            val playerNameDTO = PlayerNameDTO(
//                dataVersion = headers["age"] ?: "",
//                contentsLength = headers["content-length"] ?: "",
//                lastModified = headers["last-modified"] ?: "",
//                playernames = Gson().fromJson(jsonString, TypeToken.getParameterized(List::class.java, PlayerName::class.java).type)
//            )
//        }
//
//        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//            TODO("Not yet implemented")
//        }
//
//    }
//    enqueue(resultCB)
    return PlayerData()
}

fun PlayerData.asPlayerList() : List<com.khs.domain.database.entity.Player> {
    return this.playerList.map {
        com.khs.domain.database.entity.Player(id = null, playerId = it.playerId.toString(), playerName = it.playerName)
    }
}