package com.khs.figle_m.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.ui.theme.*

/**
 * 매치 정보를 담은 데이터 클래스
 */
data class MatchItemData(
    val matchId: String,
    val matchDate: String,
    val myNickname: String,
    val opposingNickname: String,
    val myScore: String,
    val opposingScore: String,
    val myResult: String,  // "승", "무", "패", "몰수승\n(3:0)" 등
    val matchResult: String  // "승", "무", "패" (배경색 결정용)
)

/**
 * 매치 리스트 아이템 Composable
 * 경기 결과를 표시하며, 좌측은 내 정보, 우측은 상대방 정보
 *
 * @param matchData 매치 데이터
 * @param onClick 클릭 이벤트
 * @param modifier Modifier
 */
@Composable
fun MatchListItem(
    matchData: MatchItemData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (matchData.matchResult) {
        "승" -> SearchListWin
        "패" -> SearchListLose
        "무" -> SearchListDraw
        else -> SearchListDraw
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 날짜 표시 (왼쪽 상단)
            Text(
                text = matchData.matchDate,
                fontSize = 11.sp,
                color = SearchTextColor,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 5.dp, top = 2.dp)
            )

            // 중앙 점수 및 결과 표시
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 왼쪽 결과 텍스트 (몰수승/패 등)
                Text(
                    text = matchData.myResult,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = SearchTextColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(60.dp)
                        .padding(horizontal = 4.dp)
                )

                // 왼쪽 점수 및 닉네임
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Text(
                        text = matchData.myScore,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = SearchTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(60.dp)
                            .background(FragmentBackground)
                    )
                    Text(
                        text = matchData.myNickname,
                        fontSize = 12.sp,
                        color = SearchTextColor,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.widthIn(max = 120.dp)
                    )
                }

                // 구분자 ":"
                Text(
                    text = " : ",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = SearchTextColor,
                    textAlign = TextAlign.Center
                )

                // 오른쪽 점수 및 닉네임
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 20.dp)
                ) {
                    Text(
                        text = matchData.opposingScore,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = SearchTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(60.dp)
                            .background(FragmentBackground)
                    )
                    Text(
                        text = matchData.opposingNickname,
                        fontSize = 12.sp,
                        color = SearchTextColor,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.widthIn(max = 120.dp)
                    )
                }

                Spacer(modifier = Modifier.width(60.dp))
            }
        }
    }
}

/**
 * MatchDetailResponse를 MatchItemData로 변환하는 유틸리티 함수
 */
fun MatchDetailResponse.toMatchItemData(searchAccessId: String): MatchItemData? {
    if (matchInfo.size <= 1) return null

    val opposingUserIndex = if (searchAccessId == matchInfo[0].ouid.lowercase()) 1 else 0
    val myIndex = if (opposingUserIndex == 1) 0 else 1

    val myMatchInfo = matchInfo[myIndex]
    val opposingMatchInfo = matchInfo[opposingUserIndex]

    // 날짜 포맷팅 (간단하게)
    val formattedDate = formatMatchDate(matchDate)

    // 점수 표시
    val myScore = if (myMatchInfo.shoot.goalTotal == myMatchInfo.shoot.goalTotalDisplay) {
        myMatchInfo.shoot.goalTotal.toString()
    } else {
        myMatchInfo.shoot.goalTotalDisplay.toString()
    }

    val opposingScore = if (opposingMatchInfo.shoot.goalTotal == opposingMatchInfo.shoot.goalTotalDisplay) {
        opposingMatchInfo.shoot.goalTotal.toString()
    } else {
        opposingMatchInfo.shoot.goalTotalDisplay.toString()
    }

    // 결과 텍스트
    val myResult = when (myMatchInfo.matchDetail.matchResult ?: "무") {
        "승" -> {
            if (myMatchInfo.shoot.goalTotal == myMatchInfo.shoot.goalTotalDisplay) {
                "승"
            } else {
                "몰수승\n(${myMatchInfo.shoot.goalTotal}:${opposingMatchInfo.shoot.goalTotal})"
            }
        }
        "무" -> "무"
        "패" -> {
            if (opposingMatchInfo.shoot.goalTotal == opposingMatchInfo.shoot.goalTotalDisplay) {
                "패"
            } else {
                "몰수패\n(${myMatchInfo.shoot.goalTotal}:${opposingMatchInfo.shoot.goalTotal})"
            }
        }
        else -> "무"
    }

    return MatchItemData(
        matchId = matchId,
        matchDate = formattedDate,
        myNickname = myMatchInfo.nickname,
        opposingNickname = opposingMatchInfo.nickname,
        myScore = myScore,
        opposingScore = opposingScore,
        myResult = myResult,
        matchResult = myMatchInfo.matchDetail.matchResult ?: "무"
    )
}

/**
 * 매치 날짜 포맷팅 (간단한 구현)
 */
private fun formatMatchDate(matchDate: String): String {
    // 실제 DateUtils 로직을 여기에 구현하거나, 간단하게 처리
    return try {
        val timestamp = matchDate.toLongOrNull() ?: return matchDate
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        when {
            diff < 60 * 1000 -> "방금 전"
            diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)}분 전"
            diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)}시간 전"
            diff < 7 * 24 * 60 * 60 * 1000 -> "${diff / (24 * 60 * 60 * 1000)}일 전"
            else -> "${diff / (7 * 24 * 60 * 60 * 1000)}주 전"
        }
    } catch (e: Exception) {
        matchDate
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2B2B2B)
@Composable
fun MatchListItemPreview() {
    FigleComposeTheme {
        Column(
            modifier = Modifier
                .background(MainBackground)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MatchListItem(
                matchData = MatchItemData(
                    matchId = "123",
                    matchDate = "1일 전",
                    myNickname = "JUMP",
                    opposingNickname = "KIMHS",
                    myScore = "3",
                    opposingScore = "1",
                    myResult = "승",
                    matchResult = "승"
                ),
                onClick = {}
            )
            MatchListItem(
                matchData = MatchItemData(
                    matchId = "124",
                    matchDate = "2일 전",
                    myNickname = "PlayerA",
                    opposingNickname = "PlayerB",
                    myScore = "2",
                    opposingScore = "2",
                    myResult = "무",
                    matchResult = "무"
                ),
                onClick = {}
            )
            MatchListItem(
                matchData = MatchItemData(
                    matchId = "125",
                    matchDate = "3일 전",
                    myNickname = "Test1",
                    opposingNickname = "Test2",
                    myScore = "1",
                    opposingScore = "3",
                    myResult = "패",
                    matchResult = "패"
                ),
                onClick = {}
            )
        }
    }
}
