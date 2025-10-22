package com.khs.figle_m.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khs.figle_m.ui.theme.*

/**
 * 사용자 정보 카드 Composable
 * 닉네임, 레벨, 최고 랭크 정보를 표시
 *
 * @param nickname 사용자 닉네임
 * @param teamPrice 팀 가격
 * @param level 레벨
 * @param normalHighRank 1 ON 1 최고 랭크
 * @param normalAchievementDate 1 ON 1 달성 날짜
 * @param coachHighRank 감독 모드 최고 랭크
 * @param coachAchievementDate 감독 모드 달성 날짜
 * @param modifier Modifier
 */
@Composable
fun UserInfoCard(
    nickname: String,
    teamPrice: String?,
    level: String,
    normalHighRank: String?,
    normalAchievementDate: String?,
    coachHighRank: String?,
    coachAchievementDate: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 닉네임
        Text(
            text = nickname,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = SearchTextColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
        )

        // 팀 가격
        if (!teamPrice.isNullOrEmpty()) {
            Text(
                text = teamPrice,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = AccentColorV2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
        }

        // 구분선
        Divider(
            color = Grey1,
            thickness = 3.dp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // 레벨
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LV : ",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Grey1,
                modifier = Modifier.width(90.dp)
            )
            Text(
                text = level,
                fontSize = 15.sp,
                color = Grey1
            )
        }

        // 1 ON 1 최고 랭크
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "1 ON 1 : ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Grey1,
                    modifier = Modifier.width(90.dp)
                )
                Text(
                    text = normalHighRank ?: "-",
                    fontSize = 12.sp,
                    color = Grey1
                )
            }
            if (!normalAchievementDate.isNullOrEmpty()) {
                Text(
                    text = normalAchievementDate.replace("T", " / "),
                    fontSize = 12.sp,
                    color = Grey2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 3.dp, start = 90.dp)
                )
            }
        }

        // 감독 모드 최고 랭크
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "감독 모드 : ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Grey1,
                    modifier = Modifier.width(90.dp)
                )
                Text(
                    text = coachHighRank ?: "-",
                    fontSize = 12.sp,
                    color = Grey1
                )
            }
            if (!coachAchievementDate.isNullOrEmpty()) {
                Text(
                    text = coachAchievementDate.replace("T", " / "),
                    fontSize = 12.sp,
                    color = Grey2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 3.dp, start = 90.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF23242A)
@Composable
fun UserInfoCardPreview() {
    FigleComposeTheme {
        UserInfoCard(
            nickname = "정도남",
            teamPrice = "1,024,758,000 BP",
            level = "130",
            normalHighRank = "프로1",
            normalAchievementDate = "2020-03-10T18:32:12",
            coachHighRank = "프로2",
            coachAchievementDate = "2020-04-15T12:15:30"
        )
    }
}
