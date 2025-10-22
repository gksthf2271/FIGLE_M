package com.khs.figle_m.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
 * 매치 결과를 표시하는 리스트 아이템 Composable
 *
 * @param date 날짜 (예: "1일전")
 * @param leftScore 왼쪽 플레이어 점수
 * @param rightScore 오른쪽 플레이어 점수
 * @param leftNickname 왼쪽 플레이어 닉네임
 * @param rightNickname 오른쪽 플레이어 닉네임
 * @param leftResult 왼쪽 플레이어 결과 텍스트 (예: "몰수승\n(3:0)")
 * @param modifier Modifier
 */
@Composable
fun MatchResultItem(
    date: String,
    leftScore: String,
    rightScore: String,
    leftNickname: String,
    rightNickname: String,
    leftResult: String? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(1.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 날짜 표시 (왼쪽 상단)
            Text(
                text = date,
                fontSize = 11.sp,
                color = SearchTextColor,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 5.dp, top = 2.dp)
            )

            // 중앙 점수 표시
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 왼쪽 결과 텍스트
                if (leftResult != null) {
                    Text(
                        text = leftResult,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SearchTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.width(40.dp))
                }

                // 왼쪽 점수 및 닉네임
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Text(
                        text = leftScore,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = SearchTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(60.dp)
                            .background(FragmentBackground)
                    )
                    Text(
                        text = leftNickname,
                        fontSize = 12.sp,
                        color = SearchTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(120.dp)
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
                        text = rightScore,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = SearchTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(60.dp)
                            .background(FragmentBackground)
                    )
                    Text(
                        text = rightNickname,
                        fontSize = 12.sp,
                        color = SearchTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(120.dp)
                    )
                }

                Spacer(modifier = Modifier.width(40.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2B2B2B)
@Composable
fun MatchResultItemPreview() {
    FigleComposeTheme {
        Column(
            modifier = Modifier
                .background(MainBackground)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MatchResultItem(
                date = "1일전",
                leftScore = "3",
                rightScore = "1",
                leftNickname = "JUMP",
                rightNickname = "KIMHS",
                leftResult = "몰수승\n(3:0)"
            )
            MatchResultItem(
                date = "2일전",
                leftScore = "2",
                rightScore = "2",
                leftNickname = "PlayerA",
                rightNickname = "PlayerB"
            )
        }
    }
}
