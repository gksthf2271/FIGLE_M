package com.khs.figle_m.common.preview

import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
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

    val sampleMatchDetail = MatchDetailResponse(
        matchId = "match-12345",
        matchDate = "1730000000000",
        matchType = 50,
        matchInfo = listOf(
            MatchInfoDTO().apply {
                // 필요한 필드 초기화
            },
            MatchInfoDTO().apply {
                // 필요한 필드 초기화
            }
        )
    )
}
