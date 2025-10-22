package com.khs.figle_m.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khs.figle_m.ui.components.MatchItemData
import com.khs.figle_m.ui.components.MatchListItem
import com.khs.figle_m.ui.theme.*
import kotlinx.coroutines.delay

/**
 * SearchList 화면 UI 상태
 */
data class SearchListUiState(
    val title: String = "",
    val matchList: List<MatchItemData> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val hasMoreData: Boolean = true
)

/**
 * SearchList 화면 Composable (LazyColumn 사용)
 *
 * @param uiState UI 상태
 * @param onBackClick 뒤로가기 버튼 클릭
 * @param onMatchClick 매치 아이템 클릭
 * @param onLoadMore 더 많은 데이터 로드 요청
 * @param modifier Modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchListScreen(
    uiState: SearchListUiState,
    onBackClick: () -> Unit,
    onMatchClick: (MatchItemData) -> Unit,
    onLoadMore: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // 스크롤 끝 감지를 위한 LaunchedEffect
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                val totalItemsCount = layoutInfo.totalItemsCount

                // 마지막 아이템이 보이고, 더 로드할 데이터가 있으며, 로딩 중이 아닐 때
                if (lastVisibleItemIndex >= totalItemsCount - 3
                    && uiState.hasMoreData
                    && !uiState.isLoading
                    && totalItemsCount >= 7) {
                    onLoadMore()
                }
            }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryColorV2)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 상단 앱바
            TopAppBar(
                title = {
                    Text(
                        text = uiState.title,
                        fontSize = 20.sp,
                        color = TextColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = TextColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryColorV2
                )
            )

            // 리스트 또는 Empty/Loading 상태
            when {
                uiState.isEmpty -> {
                    // Empty 상태
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "경기 기록이 없습니다",
                            fontSize = 16.sp,
                            color = Grey2
                        )
                    }
                }
                else -> {
                    // LazyColumn으로 리스트 표시
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(
                            items = uiState.matchList,
                            key = { it.matchId }
                        ) { matchData ->
                            MatchListItem(
                                matchData = matchData,
                                onClick = { onMatchClick(matchData) }
                            )
                        }

                        // 로딩 인디케이터 (하단)
                        if (uiState.isLoading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(40.dp),
                                        color = ColorPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 전체 화면 로딩 (초기 로딩 시)
        if (uiState.isLoading && uiState.matchList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    color = ColorPrimary
                )
            }
        }
    }
}

/**
 * 스크롤 끝 감지 Extension
 */
@Composable
fun LazyListState.isScrolledToEnd(): Boolean {
    val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    val totalItemsCount = layoutInfo.totalItemsCount
    return lastVisibleItemIndex >= totalItemsCount - 1
}

@Preview(showBackground = true)
@Composable
fun SearchListScreenPreview() {
    FigleComposeTheme {
        SearchListScreen(
            uiState = SearchListUiState(
                title = "1 ON 1 경기 조회",
                matchList = listOf(
                    MatchItemData(
                        matchId = "1",
                        matchDate = "1일 전",
                        myNickname = "JUMP",
                        opposingNickname = "KIMHS",
                        myScore = "3",
                        opposingScore = "1",
                        myResult = "승",
                        matchResult = "승"
                    ),
                    MatchItemData(
                        matchId = "2",
                        matchDate = "2일 전",
                        myNickname = "PlayerA",
                        opposingNickname = "PlayerB",
                        myScore = "2",
                        opposingScore = "2",
                        myResult = "무",
                        matchResult = "무"
                    ),
                    MatchItemData(
                        matchId = "3",
                        matchDate = "3일 전",
                        myNickname = "Test1",
                        opposingNickname = "Test2",
                        myScore = "1",
                        opposingScore = "3",
                        myResult = "패",
                        matchResult = "패"
                    )
                ),
                isLoading = false,
                isEmpty = false
            ),
            onBackClick = {},
            onMatchClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchListScreenEmptyPreview() {
    FigleComposeTheme {
        SearchListScreen(
            uiState = SearchListUiState(
                title = "1 ON 1 경기 조회",
                matchList = emptyList(),
                isLoading = false,
                isEmpty = true
            ),
            onBackClick = {},
            onMatchClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchListScreenLoadingPreview() {
    FigleComposeTheme {
        SearchListScreen(
            uiState = SearchListUiState(
                title = "1 ON 1 경기 조회",
                matchList = emptyList(),
                isLoading = true,
                isEmpty = false
            ),
            onBackClick = {},
            onMatchClick = {}
        )
    }
}
