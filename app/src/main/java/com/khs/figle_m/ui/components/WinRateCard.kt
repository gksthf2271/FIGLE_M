package com.khs.figle_m.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.khs.figle_m.ui.theme.*

/**
 * 승률 데이터 클래스
 */
data class WinRateData(
    val win: Int,
    val draw: Int,
    val lose: Int
) {
    val total: Int get() = win + draw + lose
    val winRate: Float get() = if (total > 0) (win.toFloat() / total * 100) else 0f
}

/**
 * 승률 카드 Composable
 * 승/무/패 정보를 표시
 *
 * @param title 카드 제목 (예: "1 ON 1")
 * @param winRateData 승률 데이터
 * @param isLoading 로딩 중 여부
 * @param modifier Modifier
 */
@Composable
fun WinRateCard(
    title: String,
    winRateData: WinRateData?,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = ButtonColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 제목
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                isLoading -> {
                    // 로딩 중
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = ColorPrimary
                    )
                }
                winRateData == null || winRateData.total == 0 -> {
                    // 데이터 없음
                    Text(
                        text = "데이터 없음",
                        color = Grey2,
                        fontSize = 14.sp
                    )
                }
                else -> {
                    // 승률 표시
                    Text(
                        text = "${String.format("%.1f", winRateData.winRate)}%",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 승/무/패 정보
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WinRateItem(
                            label = "승",
                            value = winRateData.win,
                            color = BlueColor
                        )
                        WinRateItem(
                            label = "무",
                            value = winRateData.draw,
                            color = Grey1
                        )
                        WinRateItem(
                            label = "패",
                            value = winRateData.lose,
                            color = RedColor
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 총 경기 수
                    Text(
                        text = "총 ${winRateData.total}경기",
                        fontSize = 12.sp,
                        color = Grey2
                    )
                }
            }
        }
    }
}

/**
 * 승/무/패 개별 아이템 Composable
 */
@Composable
private fun WinRateItem(
    label: String,
    value: Int,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Grey1
        )
        Text(
            text = value.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WinRateCardPreview() {
    FigleComposeTheme {
        Column(
            modifier = Modifier
                .background(PrimaryColorV2)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WinRateCard(
                title = "1 ON 1",
                winRateData = WinRateData(win = 15, draw = 3, lose = 2),
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            WinRateCard(
                title = "감독모드",
                winRateData = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            WinRateCard(
                title = "로딩 중",
                winRateData = null,
                isLoading = true,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
        }
    }
}
