package com.khs.data.nexon_api.response

import com.khs.data.nexon_api.response.DTO.DefenceDTO
import com.khs.data.nexon_api.response.DTO.MatchDetailDTO
import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import com.khs.data.nexon_api.response.DTO.PassDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.data.nexon_api.response.DTO.RankerPlayerStatDTO
import com.khs.data.nexon_api.response.DTO.ShootDTO
import com.khs.data.nexon_api.response.DTO.ShootDetailDTO
import com.khs.data.nexon_api.response.DTO.StatusDTO
import com.khs.domain.entity.DefenceInfo
import com.khs.domain.entity.HighRankUser
import com.khs.domain.entity.Match
import com.khs.domain.entity.MatchDetailInfo
import com.khs.domain.entity.MatchInfo
import com.khs.domain.entity.PassInfo
import com.khs.domain.entity.Player
import com.khs.domain.entity.RankerPlayer
import com.khs.domain.entity.RankerPlayerStat
import com.khs.domain.entity.ShootDetailInfo
import com.khs.domain.entity.ShootInfo
import com.khs.domain.entity.StatusInfo
import com.khs.domain.entity.TradeInfo
import com.khs.domain.entity.User
import okhttp3.HttpUrl
import okhttp3.ResponseBody

fun UserResponse.asUser(): User = User(
    accessId = accessId,
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

fun List<UserHighRankResponse>.asHighRanker() : List<HighRankUser> = map {
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
    accessId = accessId,
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
