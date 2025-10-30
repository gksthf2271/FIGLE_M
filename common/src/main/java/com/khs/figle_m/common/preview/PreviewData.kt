package com.khs.figle_m.common.preview

import com.khs.data.nexon_api.response.DTO.DefenceDTO
import com.khs.data.nexon_api.response.DTO.MatchDetailDTO
import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import com.khs.data.nexon_api.response.DTO.PassDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.DTO.ShootDTO
import com.khs.data.nexon_api.response.DTO.ShootDetailDTO
import com.khs.data.nexon_api.response.DTO.StatusDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.data.nexon_api.response.UserResponse
import com.khs.figle_m.common.model.AnalyticsPlayer
import com.khs.figle_m.common.model.ParentPositionEnum
import com.khs.figle_m.common.model.Ranker
import com.khs.figle_m.common.model.TotalStatus

/**
 * Preview 및 테스트용 샘플 데이터
 */
object PreviewData {

    // ========== Ranker Sample Data ==========

    val sampleRanker1 = Ranker(
        rank_no = "1",
        rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank0.png",
        level = "50",
        level_gage = "75",
        name = "FC바르셀로나",
        price = "1,234,567,890 BP",
        win = "150",
        draw = "30",
        lose = "20",
        rank_rate = "75.0",
        rank_point = "2500",
        rank_percent = "상위 0.1%",
        best_rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank0.png",
        pre_rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank1.png",
        cur_rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank0.png"
    )

    val sampleRanker2 = Ranker(
        rank_no = "2",
        rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank0.png",
        level = "48",
        level_gage = "60",
        name = "레알마드리드",
        price = "1,100,000,000 BP",
        win = "140",
        draw = "25",
        lose = "35",
        rank_rate = "70.0",
        rank_point = "2400",
        rank_percent = "상위 0.2%",
        best_rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank0.png",
        pre_rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank0.png",
        cur_rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank0.png"
    )

    val sampleRanker3 = Ranker(
        rank_no = "3",
        rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank1.png",
        level = "45",
        level_gage = "45",
        name = "맨체스터시티",
        price = "980,000,000 BP",
        win = "130",
        draw = "30",
        lose = "40",
        rank_rate = "65.0",
        rank_point = "2300",
        rank_percent = "상위 0.5%",
        best_rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank0.png",
        pre_rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank1.png",
        cur_rank_icon_url = "https://ssl.nexon.com/s2/game/fo4/obt/rank/large/update_2009/ico_rank1.png"
    )

    val sampleRankerList = listOf(
        sampleRanker1,
        sampleRanker2,
        sampleRanker3,
        sampleRanker1.copy(rank_no = "4", name = "리버풀", rank_percent = "상위 1%", rank_point = "2200"),
        sampleRanker2.copy(rank_no = "5", name = "바이에른뮌헨", rank_percent = "상위 2%", rank_point = "2100"),
        sampleRanker3.copy(rank_no = "6", name = "파리생제르맹", rank_percent = "상위 3%", rank_point = "2000"),
        sampleRanker1.copy(rank_no = "7", name = "첼시", rank_percent = "상위 5%", rank_point = "1900"),
        sampleRanker2.copy(rank_no = "8", name = "유벤투스", rank_percent = "상위 10%", rank_point = "1800"),
    )

    // ========== AnalyticsPlayer Sample Data ==========

    val samplePlayer1 = AnalyticsPlayer(
        spId = 254080,
        position = ParentPositionEnum.F,
        totalData = TotalStatus(
            totalShoot = 150,
            totalEffectiveShoot = 120,
            totalAssist = 15,
            totalGoal = 25,
            totalDribble = 80,
            totalPassTry = 500,
            totalPassSuccess = 420,
            totalBlock = 10,
            totalTackle = 45,
            totalSpRating = 8.5f
        ),
        imageResUrl = "https://fco.dn.nexoncdn.co.kr/live/externalAssets/common/players/p254080.png",
        playerDataList = emptyList()
    )

    val samplePlayer2 = AnalyticsPlayer(
        spId = 238414,
        position = ParentPositionEnum.M,
        totalData = TotalStatus(
            totalShoot = 100,
            totalEffectiveShoot = 80,
            totalAssist = 20,
            totalGoal = 12,
            totalDribble = 90,
            totalPassTry = 450,
            totalPassSuccess = 380,
            totalBlock = 8,
            totalTackle = 40,
            totalSpRating = 8.2f
        ),
        imageResUrl = "https://fco.dn.nexoncdn.co.kr/live/externalAssets/common/players/p238414.png",
        playerDataList = emptyList()
    )

    val sampleRatingTopPlayers = listOf(
        samplePlayer1,
        samplePlayer2,
        samplePlayer1.copy(
            spId = 100001,
            totalData = samplePlayer1.totalData.copy(totalSpRating = 8.0f)
        ),
        samplePlayer2.copy(
            spId = 100002,
            totalData = samplePlayer2.totalData.copy(totalSpRating = 7.9f)
        ),
        samplePlayer1.copy(
            spId = 100003,
            totalData = samplePlayer1.totalData.copy(totalSpRating = 7.8f)
        ),
        samplePlayer2.copy(
            spId = 100004,
            totalData = samplePlayer2.totalData.copy(totalSpRating = 7.7f)
        ),
        samplePlayer1.copy(
            spId = 100005,
            totalData = samplePlayer1.totalData.copy(totalSpRating = 7.6f)
        ),
        samplePlayer2.copy(
            spId = 100006,
            totalData = samplePlayer2.totalData.copy(totalSpRating = 7.5f)
        ),
        samplePlayer1.copy(
            spId = 100007,
            totalData = samplePlayer1.totalData.copy(totalSpRating = 7.4f)
        ),
        samplePlayer2.copy(
            spId = 100008,
            totalData = samplePlayer2.totalData.copy(totalSpRating = 7.3f)
        ),
    )

    val sampleGoalTopPlayers = listOf(
        samplePlayer1.copy(
            totalData = samplePlayer1.totalData.copy(totalGoal = 25)
        ),
        samplePlayer2.copy(
            totalData = samplePlayer2.totalData.copy(totalGoal = 18)
        ),
        samplePlayer1.copy(
            spId = 100101,
            totalData = samplePlayer1.totalData.copy(totalGoal = 15)
        ),
        samplePlayer2.copy(
            spId = 100102,
            totalData = samplePlayer2.totalData.copy(totalGoal = 12)
        ),
        samplePlayer1.copy(
            spId = 100103,
            totalData = samplePlayer1.totalData.copy(totalGoal = 10)
        ),
    )

    val sampleAssistTopPlayers = listOf(
        samplePlayer2.copy(
            totalData = samplePlayer2.totalData.copy(totalAssist = 20)
        ),
        samplePlayer1.copy(
            totalData = samplePlayer1.totalData.copy(totalAssist = 15)
        ),
        samplePlayer2.copy(
            spId = 100201,
            totalData = samplePlayer2.totalData.copy(totalAssist = 12)
        ),
        samplePlayer1.copy(
            spId = 100202,
            totalData = samplePlayer1.totalData.copy(totalAssist = 10)
        ),
        samplePlayer2.copy(
            spId = 100203,
            totalData = samplePlayer2.totalData.copy(totalAssist = 8)
        ),
    )

    // ========== UserResponse Sample Data ==========

    val sampleUserResponse = UserResponse(
        ouid = "test-ouid-12345",
        nickname = "테스트유저",
        level = "50",
        teamPrice = "1,234,567,890"
    )

    // ========== MatchDetailResponse Sample Data ==========

    private fun createSamplePlayer(spId: Int, spPosition: Int, spGrade: Int, spRating: Float) = PlayerDTO(
        spId = spId,
        spPosition = spPosition,
        spGrade = spGrade,
        status = StatusDTO(
            shoot = 5,
            effectiveShoot = 3,
            assist = 2,
            goal = 1,
            dribble = 10,
            passTry = 30,
            passSuccess = 25,
            block = 2,
            tackle = 3,
            spRating = spRating
        ),
        imageUrl = "https://fco.dn.nexoncdn.co.kr/live/externalAssets/common/players/p$spId.png",
        subImageUrl = null
    )

    val sampleMatchDetail = MatchDetailResponse(
        matchId = "match-12345",
        matchDate = "1730000000000",
        matchType = 50,
        matchInfo = listOf(
            // Winner team
            MatchInfoDTO(
                ouid = "winner-ouid-123",
                nickname = "WinnerPlayer",
                matchDetail = MatchDetailDTO(
                    seasonId = 280,
                    matchResult = "승",
                    matchEndType = 1,
                    systemPause = 0,
                    foul = 5,
                    injury = 1,
                    redCards = 0,
                    yellowCards = 2,
                    dribble = 45,
                    cornerKick = 6,
                    possession = 55
                ),
                shoot = ShootDTO(
                    shootTotal = 15,
                    effectiveShootTotal = "10",
                    shootOutScore = 0,
                    goalTotal = 3,
                    goalTotalDisplay = 3,
                    ownGoal = 0,
                    shootHeading = 2,
                    goalHeading = 1,
                    shootFreekick = 1,
                    goalFreekick = 0,
                    shootInPenalty = 10,
                    goalInPenalty = 2,
                    shootOutPenalty = 5,
                    goalOutPenalty = 1,
                    shootPenaltyKick = 0,
                    goalPenaltyKick = 0
                ),
                shootDetail = listOf(
                    ShootDetailDTO(
                        goalTime = 15,
                        x = 0.5,
                        y = 0.3,
                        type = 3,
                        result = 3,
                        assist = true,
                        hitPost = false,
                        inPenalty = true
                    ),
                    ShootDetailDTO(
                        goalTime = 42,
                        x = 0.6,
                        y = 0.4,
                        type = 3,
                        result = 3,
                        assist = false,
                        hitPost = false,
                        inPenalty = true
                    ),
                    ShootDetailDTO(
                        goalTime = 78,
                        x = 0.4,
                        y = 0.5,
                        type = 12,
                        result = 3,
                        assist = true,
                        hitPost = false,
                        inPenalty = false
                    )
                ),
                pass = PassDTO(
                    passTry = 450,
                    passSuccess = 380,
                    shortPassTry = 300,
                    shortPassSuccess = 270,
                    longPassTry = 100,
                    longPassSuccess = 70,
                    bouncingLobPassTry = 20,
                    bouncingLobPassSuccess = 15,
                    drivenGroundPassTry = 10,
                    drivenGroundPassSuccess = 8,
                    throughPassTry = 15,
                    throughPassSuccess = 12,
                    lobbedThroughPassTry = 5,
                    lobbedThroughPassSuccess = 5
                ),
                defence = DefenceDTO(
                    blockTry = 10,
                    blockSuccess = 7,
                    tackleTry = 15,
                    tackleSuccess = 10
                ),
                player = listOf(
                    createSamplePlayer(254080, 22, 8, 8.5f),
                    createSamplePlayer(238414, 20, 7, 8.2f),
                    createSamplePlayer(231747, 10, 7, 7.8f),
                    createSamplePlayer(233488, 6, 6, 7.5f),
                    createSamplePlayer(222509, 5, 6, 7.3f),
                    createSamplePlayer(231678, 4, 5, 7.1f),
                    createSamplePlayer(222509, 3, 5, 7.0f),
                    createSamplePlayer(247263, 2, 6, 7.2f),
                    createSamplePlayer(223340, 1, 7, 7.4f),
                    createSamplePlayer(189511, 0, 8, 7.8f),
                    createSamplePlayer(216393, 28, 5, 6.9f)
                )
            ),
            // Loser team
            MatchInfoDTO(
                ouid = "loser-ouid-456",
                nickname = "LoserPlayer",
                matchDetail = MatchDetailDTO(
                    seasonId = 280,
                    matchResult = "패",
                    matchEndType = 1,
                    systemPause = 0,
                    foul = 8,
                    injury = 2,
                    redCards = 1,
                    yellowCards = 3,
                    dribble = 38,
                    cornerKick = 4,
                    possession = 45
                ),
                shoot = ShootDTO(
                    shootTotal = 10,
                    effectiveShootTotal = "6",
                    shootOutScore = 0,
                    goalTotal = 1,
                    goalTotalDisplay = 1,
                    ownGoal = 0,
                    shootHeading = 1,
                    goalHeading = 0,
                    shootFreekick = 2,
                    goalFreekick = 1,
                    shootInPenalty = 6,
                    goalInPenalty = 0,
                    shootOutPenalty = 4,
                    goalOutPenalty = 1,
                    shootPenaltyKick = 0,
                    goalPenaltyKick = 0
                ),
                shootDetail = listOf(
                    ShootDetailDTO(
                        goalTime = 68,
                        x = 0.7,
                        y = 0.5,
                        type = 20,
                        result = 3,
                        assist = false,
                        hitPost = false,
                        inPenalty = false
                    )
                ),
                pass = PassDTO(
                    passTry = 380,
                    passSuccess = 310,
                    shortPassTry = 260,
                    shortPassSuccess = 230,
                    longPassTry = 80,
                    longPassSuccess = 50,
                    bouncingLobPassTry = 15,
                    bouncingLobPassSuccess = 10,
                    drivenGroundPassTry = 10,
                    drivenGroundPassSuccess = 7,
                    throughPassTry = 10,
                    throughPassSuccess = 8,
                    lobbedThroughPassTry = 5,
                    lobbedThroughPassSuccess = 5
                ),
                defence = DefenceDTO(
                    blockTry = 12,
                    blockSuccess = 8,
                    tackleTry = 18,
                    tackleSuccess = 11
                ),
                player = listOf(
                    createSamplePlayer(234642, 22, 7, 7.2f),
                    createSamplePlayer(184941, 20, 6, 6.9f),
                    createSamplePlayer(229891, 10, 6, 6.8f),
                    createSamplePlayer(188350, 6, 5, 6.5f),
                    createSamplePlayer(201535, 5, 5, 6.4f),
                    createSamplePlayer(225100, 4, 5, 6.3f),
                    createSamplePlayer(209331, 3, 5, 6.2f),
                    createSamplePlayer(226328, 2, 5, 6.5f),
                    createSamplePlayer(183130, 1, 6, 6.7f),
                    createSamplePlayer(177003, 0, 7, 7.0f),
                    createSamplePlayer(206534, 28, 4, 6.0f)
                )
            )
        )
    )
}
