package com.khs.figle_m.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khs.figle_m.ui.theme.*

/**
 * 매치 타입 카드 Composable
 * "1 ON 1 전적 검색" 또는 "감독 모드 전적 검색"을 표시
 *
 * @param title 카드 제목
 * @param icon 아이콘
 * @param isEmpty 데이터가 비어있는지 여부
 * @param onClick 클릭 이벤트
 * @param modifier Modifier
 */
@Composable
fun MatchTypeCard(
    title: String,
    icon: ImageVector,
    isEmpty: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .clickable(enabled = !isEmpty, onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = ButtonColor
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isEmpty) {
                Text(
                    text = "데이터 없음",
                    color = Grey2,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.size(50.dp),
                        tint = Grey1
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(
                        color = Grey1,
                        thickness = 3.dp,
                        modifier = Modifier.width(70.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = title,
                        color = TextColor,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Divider Composable (수평선)
 */
@Composable
fun Divider(
    color: Color,
    thickness: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(thickness)
            .background(color)
    )
}

/**
 * 액션 카드 Composable
 * "최근 거래 내역" 또는 "최근 10경기 분석"을 표시
 *
 * @param title 카드 제목
 * @param icon 아이콘
 * @param onClick 클릭 이벤트
 * @param modifier Modifier
 */
@Composable
fun ActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = ButtonColor
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(50.dp),
                    tint = Grey1
                )
                Spacer(modifier = Modifier.height(10.dp))
                Divider(
                    color = Grey1,
                    thickness = 3.dp,
                    modifier = Modifier.width(70.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = title,
                    color = TextColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatchTypeCardPreview() {
    FigleComposeTheme {
        Column(
            modifier = Modifier
                .background(PrimaryColorV2)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MatchTypeCard(
                title = "1 ON 1\n전적 검색",
                icon = Icons.Default.DateRange,
                isEmpty = false,
                onClick = {},
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            MatchTypeCard(
                title = "감독 모드\n전적 검색",
                icon = Icons.Default.Info,
                isEmpty = true,
                onClick = {},
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
        }
    }
}
