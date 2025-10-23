package com.khs.figle_m.ui.feature.ranking

import android.app.Activity
import android.content.Intent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.khs.figle_m.common.model.Ranker
import com.khs.figle_m.feature.ranking.R
import com.khs.figle_m.feature.ranking.RankingUIState
import com.khs.figle_m.feature.ranking.RankingViewModel

@Composable
fun RankingScreen(
    rankingViewModel: RankingViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onNavigateToSearch: ((String, String) -> Unit)? = null,
    onShowError: (Int) -> Unit
) {
    val uiState by rankingViewModel.uiState.collectAsStateWithLifecycle()
    val selectedRanker by rankingViewModel.selectedRanker.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (val state = uiState) {
            is RankingUIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is RankingUIState.Success -> {
                RankingContent(
                    rankerList = state.rankerList,
                    selectedRanker = selectedRanker,
                    onRankerClick = { ranker ->
                        rankingViewModel.selectRanker(ranker)
                    },
                    onClose = onClose,
                    onSearch = {
                        // Handle search action
                        selectedRanker?.let { ranker ->
                            if (onNavigateToSearch != null) {
                                onNavigateToSearch(ranker.name ?: "", ranker.price ?: "")
                            } else {
                                // For Activity context, return result
                                val activity = context as? Activity
                                activity?.let {
                                    val intent = Intent().apply {
                                        putExtra("KEY_SEARCH", ranker.name)
                                        putExtra("KEY_SEARCH_TEAM_PRICE", ranker.price)
                                    }
                                    it.setResult(Activity.RESULT_OK, intent)
                                    it.finish()
                                }
                            }
                        }
                    }
                )
            }

            is RankingUIState.Failed -> {
                onShowError(state.errorCode)
            }
        }
    }
}

@Composable
private fun RankingContent(
    rankerList: List<Ranker>,
    selectedRanker: Ranker?,
    onRankerClick: (Ranker) -> Unit,
    onClose: () -> Unit,
    onSearch: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (closeButton, topSection, listSection) = createRefs()

        // Close Button
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .constrainAs(closeButton) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                }
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = "X",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Top Section - Selected Ranker Detail
        selectedRanker?.let { ranker ->
            RankingTopSection(
                ranker = ranker,
                onSearch = onSearch,
                modifier = Modifier.constrainAs(topSection) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    bottom.linkTo(listSection.top)
                }
            )
        }

        // List Section
        LazyColumn(
            modifier = Modifier
                .constrainAs(listSection) {
                    top.linkTo(topSection.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.percent(0.7f)
                }
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(rankerList) { ranker ->
                RankingListItem(
                    ranker = ranker,
                    onClick = { onRankerClick(ranker) }
                )
            }
        }
    }
}

@Composable
private fun RankingTopSection(
    ranker: Ranker,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ranking Number
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)) {
                        append(ranker.rank_no ?: "")
                    }
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append(" ")
                        append(stringResource(R.string.ranking_position))
                    }
                },
                color = MaterialTheme.colorScheme.onBackground
            )

            // Rank Percent
            ranker.rank_percent?.let { percent ->
                Text(
                    text = stringResource(R.string.ranking_percent, percent),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Image
            Box(contentAlignment = Alignment.BottomCenter) {
                AsyncImage(
                    model = ranker.rank_icon_url,
                    contentDescription = "Ranker Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = com.khs.figle_m.common.R.drawable.person_icon),
                    error = painterResource(id = com.khs.figle_m.common.R.drawable.person_icon)
                )

                // Search Icon
                IconButton(
                    onClick = onSearch,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = com.khs.figle_m.common.R.drawable.search),
                        contentDescription = stringResource(R.string.ranking_search),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Name
            Text(
                text = ranker.name ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Price
            Text(
                text = ranker.price ?: "",
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun RankingListItem(
    ranker: Ranker,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ranking Number and Point
            Column(
                modifier = Modifier.width(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                            append(ranker.rank_no ?: "")
                        }
                        withStyle(style = SpanStyle(fontSize = 14.sp)) {
                            append(" ")
                            append(stringResource(R.string.ranking_position))
                        }
                    },
                    color = MaterialTheme.colorScheme.onSurface
                )
                ranker.rank_point?.let { point ->
                    Text(
                        text = "$point ${stringResource(R.string.ranking_point)}",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Name and Price
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = ranker.name ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = ranker.price ?: "",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
