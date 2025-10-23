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
        name = "손흥민",
        spPosition = 22,
        imageUrl = "https://fco.dn.nexoncdn.co.kr/live/externalAssets/common/players/p254080.png",
        totalStatus = TotalStatus(
            spRating = 8.5,
            matchCount = 50,
            assist = 15,
            goal = 25,
            validTackle = 45,
            shootTotal = 150,
            passSuccess = 420,
            defending = 0,
            dribble = 80,
            intercept = 30,
            attackPoint = 320.5,
            defencePoint = 125.0
        ),
        parentPosition = ParentPositionEnum.F
    )

    val samplePlayer2 = AnalyticsPlayer(
        spId = 238414,
        name = "이강인",
        spPosition = 20,
        imageUrl = "https://fco.dn.nexoncdn.co.kr/live/externalAssets/common/players/p238414.png",
        totalStatus = TotalStatus(
            spRating = 8.2,
            matchCount = 45,
            assist = 20,
            goal = 12,
            validTackle = 40,
            shootTotal = 100,
            passSuccess = 380,
            defending = 0,
            dribble = 90,
            intercept = 35,
            attackPoint = 280.0,
            defencePoint = 110.0
        ),
        parentPosition = ParentPositionEnum.M
    )

    val sampleRatingTopPlayers = listOf(
        samplePlayer1,
        samplePlayer2,
        samplePlayer1.copy(spId = 100001, name = "김민재", spRating = 8.0),
        samplePlayer2.copy(spId = 100002, name = "황희찬", spRating = 7.9),
        samplePlayer1.copy(spId = 100003, name = "황인범", spRating = 7.8),
        samplePlayer2.copy(spId = 100004, name = "이재성", spRating = 7.7),
        samplePlayer1.copy(spId = 100005, name = "조규성", spRating = 7.6),
        samplePlayer2.copy(spId = 100006, name = "정우영", spRating = 7.5),
        samplePlayer1.copy(spId = 100007, name = "백승호", spRating = 7.4),
        samplePlayer2.copy(spId = 100008, name = "이승우", spRating = 7.3),
    )

    val sampleGoalTopPlayers = listOf(
        samplePlayer1.copy(goal = 25),
        samplePlayer2.copy(goal = 18),
        samplePlayer1.copy(spId = 100101, name = "황희찬", goal = 15),
        samplePlayer2.copy(spId = 100102, name = "조규성", goal = 12),
        samplePlayer1.copy(spId = 100103, name = "황의조", goal = 10),
    )

    val sampleAssistTopPlayers = listOf(
        samplePlayer2.copy(assist = 20),
        samplePlayer1.copy(assist = 15),
        samplePlayer2.copy(spId = 100201, name = "이승우", assist = 12),
        samplePlayer1.copy(spId = 100202, name = "정우영", assist = 10),
        samplePlayer2.copy(spId = 100203, name = "이재성", assist = 8),
    )

    // ========== UserResponse Sample Data ==========

    val sampleUserResponse = UserResponse(
        ouid = "test-ouid-12345",
        nickname = "테스트유저"
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
