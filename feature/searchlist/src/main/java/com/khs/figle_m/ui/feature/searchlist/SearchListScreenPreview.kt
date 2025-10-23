package com.khs.figle_m.ui.feature.searchlist

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.khs.data.nexon_api.response.DTO.MatchDetailDTO
import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import com.khs.data.nexon_api.response.DTO.PassDTO
import com.khs.data.nexon_api.response.DTO.ShootDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.common.preview.PreviewData

// Sample match data for preview
private val sampleMatchInfo1 = MatchInfoDTO().apply {
    ouid = "test-ouid-1"
    nickname = "테스트유저1"
    shoot = ShootDTO().apply {
        goalTotal = 3
        goalTotalDisplay = 3
        shootTotal = 15
    }
    matchDetail = MatchDetailDTO().apply {
        matchResult = "승"
    }
    pass = PassDTO().apply {
        passSuccess = 85
        passTotal = 100
    }
}

private val sampleMatchInfo2 = MatchInfoDTO().apply {
    ouid = "test-ouid-2"
    nickname = "테스트유저2"
    shoot = ShootDTO().apply {
        goalTotal = 1
        goalTotalDisplay = 1
        shootTotal = 10
    }
    matchDetail = MatchDetailDTO().apply {
        matchResult = "패"
    }
    pass = PassDTO().apply {
        passSuccess = 75
        passTotal = 95
    }
}

private val sampleMatchDetail = MatchDetailResponse(
    matchId = "match-12345",
    matchDate = "1730000000000",
    matchType = 50,
    matchInfo = listOf(sampleMatchInfo1, sampleMatchInfo2)
)

private val sampleMatchList = listOf(
    sampleMatchDetail,
    sampleMatchDetail.copy(
        matchId = "match-12346",
        matchInfo = listOf(
            sampleMatchInfo1.copy().apply {
                shoot.goalTotal = 2
                shoot.goalTotalDisplay = 2
                matchDetail.matchResult = "패"
            },
            sampleMatchInfo2.copy().apply {
                shoot.goalTotal = 3
                shoot.goalTotalDisplay = 3
                matchDetail.matchResult = "승"
            }
        )
    ),
    sampleMatchDetail.copy(
        matchId = "match-12347",
        matchInfo = listOf(
            sampleMatchInfo1.copy().apply {
                shoot.goalTotal = 2
                shoot.goalTotalDisplay = 2
                matchDetail.matchResult = "무"
            },
            sampleMatchInfo2.copy().apply {
                shoot.goalTotal = 2
                shoot.goalTotalDisplay = 2
                matchDetail.matchResult = "무"
            }
        )
    ),
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchListContentPreview() {
    Surface {
        SearchListContent(
            userInfo = PreviewData.sampleUserResponse.copy(ouid = "test-ouid-1"),
            matchList = sampleMatchList,
            matchType = 50,
            onBack = { },
            onMatchClick = null
        )
    }
}

@Preview(showBackground = true, name = "Match List Item - Win")
@Composable
fun MatchListItemWinPreview() {
    Surface {
        MatchListItem(
            userInfo = PreviewData.sampleUserResponse.copy(ouid = "test-ouid-1"),
            match = sampleMatchDetail,
            onClick = { }
        )
    }
}

@Preview(showBackground = true, name = "Match List Item - Lose")
@Composable
fun MatchListItemLosePreview() {
    Surface {
        MatchListItem(
            userInfo = PreviewData.sampleUserResponse.copy(ouid = "test-ouid-1"),
            match = sampleMatchList[1],
            onClick = { }
        )
    }
}

@Preview(showBackground = true, name = "Match List Item - Draw")
@Composable
fun MatchListItemDrawPreview() {
    Surface {
        MatchListItem(
            userInfo = PreviewData.sampleUserResponse.copy(ouid = "test-ouid-1"),
            match = sampleMatchList[2],
            onClick = { }
        )
    }
}
