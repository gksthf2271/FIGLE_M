package com.khs.figle_m.ui.feature.searchlist

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khs.domain.nexon.entity.Match
import com.khs.domain.nexon.entity.User
import com.khs.figle_m.common.util.DateUtils
import com.khs.figle_m.feature.searchlist.R
import com.khs.figle_m.feature.searchlist.SearchListUIState
import com.khs.figle_m.feature.searchlist.SearchListViewModel

@Composable
fun SearchListScreen(
    userInfo: User,
    matchIdList: List<String>,
    matchType: Int,
    searchListViewModel: SearchListViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onMatchClick: ((Match) -> Unit)? = null
) {
    val uiState by searchListViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(matchIdList) {
        searchListViewModel.loadMatchList(matchIdList)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (val state = uiState) {
            is SearchListUIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is SearchListUIState.Success -> {
                SearchListContent(
                    userInfo = userInfo,
                    matchList = state.matchList,
                    matchType = matchType,
                    onBack = onBack,
                    onMatchClick = onMatchClick
                )
            }

            is SearchListUIState.Failed -> {
                Text(
                    text = "Error loading match list",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun SearchListContent(
    userInfo: User,
    matchList: List<Match>,
    matchType: Int,
    onBack: () -> Unit,
    onMatchClick: ((Match) -> Unit)?
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
                    contentDescription = stringResource(R.string.searchlist_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            val titleRes = if (matchType == 50) {
                R.string.searchlist_title_official
            } else {
                R.string.searchlist_title_coach
            }

            Text(
                text = stringResource(titleRes),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Match List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(matchList) { match ->
                MatchListItem(
                    userInfo = userInfo,
                    match = match,
                    onClick = { onMatchClick?.invoke(match) }
                )
            }
        }
    }
}

@Composable
private fun MatchListItem(
    userInfo: User,
    match: Match,
    onClick: () -> Unit
) {
    if (match.matchInfo.size < 2) return

    val myIndex = if (userInfo.accessId.equals(match.matchInfo[0].accessId, ignoreCase = true)) 0 else 1
    val opponentIndex = if (myIndex == 0) 1 else 0

    val myMatchInfo = match.matchInfo[myIndex]
    val opponentMatchInfo = match.matchInfo[opponentIndex]

    val myScore = if (myMatchInfo.shoot.goalTotal == myMatchInfo.shoot.goalTotalDisplay) {
        myMatchInfo.shoot.goalTotal
    } else {
        myMatchInfo.shoot.goalTotalDisplay
    }

    val opponentScore = if (opponentMatchInfo.shoot.goalTotal == opponentMatchInfo.shoot.goalTotalDisplay) {
        opponentMatchInfo.shoot.goalTotal
    } else {
        opponentMatchInfo.shoot.goalTotalDisplay
    }

    val matchDate = DateUtils.formatTimeString(match.matchDate.toLong())

    // Determine result
    val myResult = myMatchInfo.matchDetailInfo.matchResult
    val (resultText, backgroundColor) = when (myResult) {
        "승" -> {
            val text = if (myMatchInfo.shoot.goalTotal == myMatchInfo.shoot.goalTotalDisplay) {
                stringResource(R.string.searchlist_win)
            } else {
                stringResource(R.string.searchlist_forfeit_win, myMatchInfo.shoot.goalTotal, opponentMatchInfo.shoot.goalTotal)
            }
            text to Color(0xFF4CAF50) // Green for win
        }
        "패" -> {
            val text = if (opponentMatchInfo.shoot.goalTotal == opponentMatchInfo.shoot.goalTotalDisplay) {
                stringResource(R.string.searchlist_lose)
            } else {
                stringResource(R.string.searchlist_forfeit_lose, myMatchInfo.shoot.goalTotal, opponentMatchInfo.shoot.goalTotal)
            }
            text to Color(0xFFF44336) // Red for lose
        }
        else -> {
            stringResource(R.string.searchlist_draw) to Color(0xFF9E9E9E) // Gray for draw
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Date
            Text(
                text = matchDate,
                fontSize = 11.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.TopStart)
            )

            // Score and nicknames
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Left result
                Text(
                    text = resultText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.width(80.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Left player
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = myScore.toString(),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = myMatchInfo.nickname,
                        fontSize = 12.sp,
                        color = Color.White,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = ":",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Right player
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = opponentScore.toString(),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = opponentMatchInfo.nickname,
                        fontSize = 12.sp,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
