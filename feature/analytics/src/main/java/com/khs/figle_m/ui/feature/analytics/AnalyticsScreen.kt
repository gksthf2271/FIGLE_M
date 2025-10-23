package com.khs.figle_m.ui.feature.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.khs.figle_m.common.model.AnalyticsPlayer
import com.khs.figle_m.common.model.ParentPositionEnum
import com.khs.figle_m.feature.analytics.AnalyticsUIState
import com.khs.figle_m.feature.analytics.AnalyticsViewModel
import com.khs.figle_m.feature.analytics.R

@Composable
fun AnalyticsScreen(
    accessId: String,
    matchIdList: List<String>,
    analyticsViewModel: AnalyticsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onPlayerClick: ((AnalyticsPlayer) -> Unit)? = null
) {
    val uiState by analyticsViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(accessId, matchIdList) {
        val limitedList = matchIdList.take(10)
        analyticsViewModel.loadAnalytics(accessId, limitedList)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (val state = uiState) {
            is AnalyticsUIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is AnalyticsUIState.Success -> {
                AnalyticsContent(
                    ratingTopPlayers = state.ratingTopPlayers,
                    goalTopPlayers = state.goalTopPlayers,
                    assistTopPlayers = state.assistTopPlayers,
                    onBack = onBack,
                    onPlayerClick = onPlayerClick
                )
            }

            is AnalyticsUIState.Failed -> {
                Text(
                    text = "Error loading analytics",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun AnalyticsContent(
    ratingTopPlayers: List<AnalyticsPlayer>,
    goalTopPlayers: List<AnalyticsPlayer>,
    assistTopPlayers: List<AnalyticsPlayer>,
    onBack: () -> Unit,
    onPlayerClick: ((AnalyticsPlayer) -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = com.khs.figle_m.common.R.drawable.abc_vector_test),
                    contentDescription = stringResource(R.string.analytics_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = stringResource(R.string.analytics_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Rating TOP 10
            AnalyticsSection(
                title = stringResource(R.string.analytics_rating_top),
                players = ratingTopPlayers,
                rowType = RowType.RATING,
                onPlayerClick = onPlayerClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Goal TOP 5
            AnalyticsSection(
                title = stringResource(R.string.analytics_goal_top),
                players = goalTopPlayers,
                rowType = RowType.GOAL,
                onPlayerClick = onPlayerClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Assist TOP 5
            AnalyticsSection(
                title = stringResource(R.string.analytics_assist_top),
                players = assistTopPlayers,
                rowType = RowType.ASSIST,
                onPlayerClick = onPlayerClick
            )
        }
    }
}

@Composable
private fun AnalyticsSection(
    title: String,
    players: List<AnalyticsPlayer>,
    rowType: RowType,
    onPlayerClick: ((AnalyticsPlayer) -> Unit)?
) {
    Column {
        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(players) { index, player ->
                PlayerCard(
                    player = player,
                    rank = index + 1,
                    rowType = rowType,
                    onClick = { onPlayerClick?.invoke(player) }
                )
            }
        }
    }
}

@Composable
private fun PlayerCard(
    player: AnalyticsPlayer,
    rank: Int,
    rowType: RowType,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Rank and Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = rank.toString(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (rank == 1) Color.Red else Color.White,
                    modifier = Modifier
                        .background(
                            color = if (rank == 1) Color.Yellow else Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

                if (rowType == RowType.RATING) {
                    val avgRating = player.totalData.totalSpRating / player.playerDataList.size
                    Text(
                        text = String.format("%.2f", avgRating),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Player Image
            AsyncImage(
                model = player.imageResUrl,
                contentDescription = "Player Image",
                modifier = Modifier
                    .size(55.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Position
            Text(
                text = player.position.description,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Stats based on row type
            PlayerStats(player = player, rowType = rowType)
        }
    }
}

@Composable
private fun PlayerStats(player: AnalyticsPlayer, rowType: RowType) {
    val totalData = player.totalData
    val avgRating = totalData.totalSpRating / player.playerDataList.size

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (rowType) {
            RowType.RATING -> {
                when (player.position) {
                    ParentPositionEnum.F -> {
                        StatRow("슛", totalData.totalShoot.toString())
                        StatRow("유효슛", totalData.totalEffectiveShoot.toString())
                        StatRow("도움", totalData.totalAssist.toString())
                        StatRow("득점", totalData.totalGoal.toString())
                    }
                    ParentPositionEnum.M -> {
                        StatRow("패스시도", totalData.totalPassTry.toString())
                        StatRow("패스성공", totalData.totalPassSuccess.toString())
                        StatRow("도움", totalData.totalAssist.toString())
                        StatRow("득점", totalData.totalGoal.toString())
                    }
                    ParentPositionEnum.D, ParentPositionEnum.GK -> {
                        StatRow("블락", totalData.totalBlock.toString())
                        StatRow("태클", totalData.totalTackle.toString())
                        StatRow("패스시도", totalData.totalPassTry.toString())
                        StatRow("패스성공", totalData.totalPassSuccess.toString())
                    }
                    else -> {}
                }
            }
            RowType.GOAL -> {
                StatRow("득점", totalData.totalGoal.toString())
                StatRow("슛", totalData.totalShoot.toString())
                StatRow("유효슛", totalData.totalEffectiveShoot.toString())
                StatRow("평점", String.format("%.2f", avgRating))
            }
            RowType.ASSIST -> {
                StatRow("도움", totalData.totalAssist.toString())
                StatRow("패스시도", totalData.totalPassTry.toString())
                StatRow("패스성공", totalData.totalPassSuccess.toString())
                StatRow("평점", String.format("%.2f", avgRating))
            }
        }
    }
}

@Composable
private fun StatRow(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 9.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

enum class RowType {
    RATING, GOAL, ASSIST
}
