package com.khs.figle_m.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khs.figle_m.ui.theme.FigleComposeTheme

/**
 * 랭킹 리스트의 개별 아이템 Composable
 *
 * @param ranking 순위 (예: "1위")
 * @param rankPoint 랭킹 포인트 (예: "2869.21 점")
 * @param userId 사용자 ID
 * @param totalPrice 총 BP (예: "5,790,863,810 BP")
 * @param modifier Modifier
 * @param onItemClick 아이템 클릭 시 호출되는 콜백
 */
@Composable
fun RankingListItem(
    ranking: String,
    rankPoint: String,
    userId: String,
    totalPrice: String,
    modifier: Modifier = Modifier,
    onItemClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 왼쪽: 순위 및 포인트
        Column(
            modifier = Modifier
                .width(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = ranking,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = rankPoint,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // 오른쪽: 사용자 ID 및 총 BP
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = userId,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = totalPrice,
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RankingListItemPreview() {
    FigleComposeTheme {
        RankingListItem(
            ranking = "100위",
            rankPoint = "2869.21 점",
            userId = "WARIGARIWARI",
            totalPrice = "5,790,863,810 BP"
        )
    }
}
