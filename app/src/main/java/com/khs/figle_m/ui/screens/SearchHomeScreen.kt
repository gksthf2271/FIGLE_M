package com.khs.figle_m.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khs.figle_m.ui.components.*
import com.khs.figle_m.ui.theme.*

/**
 * 검색 홈 화면 UI 상태
 */
data class SearchHomeUiState(
    val nickname: String = "",
    val teamPrice: String? = null,
    val level: String = "",
    val normalHighRank: String? = null,
    val normalAchievementDate: String? = null,
    val coachHighRank: String? = null,
    val coachAchievementDate: String? = null,
    val normalWinRate: WinRateData? = null,
    val coachWinRate: WinRateData? = null,
    val hasNormalMatches: Boolean = true,
    val hasCoachMatches: Boolean = true,
    val isLoading: Boolean = false,
    val currentTab: Int = 0
)

/**
 * SearchHome 화면 Composable
 *
 * @param uiState UI 상태
 * @param onBackClick 뒤로가기 버튼 클릭
 * @param onNormalMatchClick 1 ON 1 전적 검색 클릭
 * @param onCoachMatchClick 감독 모드 전적 검색 클릭
 * @param onTradeClick 거래 내역 클릭
 * @param onAnalysisClick 최근 10경기 분석 클릭
 * @param onTabSelected 탭 선택 (0: 1 ON 1, 1: 감독 모드)
 * @param modifier Modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchHomeScreen(
    uiState: SearchHomeUiState,
    onBackClick: () -> Unit,
    onNormalMatchClick: () -> Unit,
    onCoachMatchClick: () -> Unit,
    onTradeClick: () -> Unit,
    onAnalysisClick: () -> Unit,
    onTabSelected: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableStateOf(uiState.currentTab) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryColorV2)
    ) {
        if (uiState.isLoading) {
            // 로딩 중
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    color = ColorPrimary
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // 상단 앱바
                TopAppBar(
                    title = {
                        Text(
                            text = "구단주 조회",
                            fontSize = 20.sp,
                            color = TextColor,
                            modifier = Modifier.fillMaxWidth()
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

                // 상단 영역 (승률 + 사용자 정보)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(horizontal = 5.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    // 왼쪽: 승률 카드 (Tab으로 전환 가능)
                    Column(
                        modifier = Modifier
                            .weight(0.45f)
                            .fillMaxHeight()
                    ) {
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                            containerColor = ButtonColor,
                            contentColor = TextColor
                        ) {
                            Tab(
                                selected = selectedTabIndex == 0,
                                onClick = {
                                    selectedTabIndex = 0
                                    onTabSelected(0)
                                },
                                text = { Text("1 ON 1", fontSize = 12.sp) }
                            )
                            Tab(
                                selected = selectedTabIndex == 1,
                                onClick = {
                                    selectedTabIndex = 1
                                    onTabSelected(1)
                                },
                                text = { Text("감독모드", fontSize = 12.sp) }
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            when (selectedTabIndex) {
                                0 -> WinRateCard(
                                    title = "1 ON 1",
                                    winRateData = uiState.normalWinRate
                                )
                                1 -> WinRateCard(
                                    title = "감독모드",
                                    winRateData = uiState.coachWinRate
                                )
                            }
                        }
                    }

                    // 오른쪽: 사용자 정보
                    Card(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(),
                        colors = CardDefaults.cardColors(
                            containerColor = ButtonColor
                        )
                    ) {
                        UserInfoCard(
                            nickname = uiState.nickname,
                            teamPrice = uiState.teamPrice,
                            level = uiState.level,
                            normalHighRank = uiState.normalHighRank,
                            normalAchievementDate = uiState.normalAchievementDate,
                            coachHighRank = uiState.coachHighRank,
                            coachAchievementDate = uiState.coachAchievementDate
                        )
                    }
                }

                // 하단 영역 (매치 검색 + 액션 버튼들)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(horizontal = 5.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    // 왼쪽 컬럼
                    Column(
                        modifier = Modifier
                            .weight(0.45f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        // 1 ON 1 전적 검색
                        MatchTypeCard(
                            title = "1 ON 1\n전적 검색",
                            icon = Icons.Default.DateRange,
                            isEmpty = !uiState.hasNormalMatches,
                            onClick = onNormalMatchClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )

                        // 최근 거래 내역
                        ActionCard(
                            title = "최근\n거래 내역",
                            icon = Icons.Default.ShoppingCart,
                            onClick = onTradeClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }

                    // 오른쪽 컬럼
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        // 감독 모드 전적 검색 (상단 절반 - 숨김, 스페이서로 대체)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.0001f)
                        )

                        // 최근 10경기 분석
                        ActionCard(
                            title = "1 ON 1\n최근 10경기 분석",
                            icon = Icons.Default.Star,
                            onClick = onAnalysisClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchHomeScreenPreview() {
    FigleComposeTheme {
        SearchHomeScreen(
            uiState = SearchHomeUiState(
                nickname = "정도남",
                teamPrice = "1,024,758,000 BP",
                level = "130",
                normalHighRank = "프로1",
                normalAchievementDate = "2020-03-10T18:32:12",
                coachHighRank = "프로2",
                coachAchievementDate = "2020-04-15T12:15:30",
                normalWinRate = WinRateData(win = 15, draw = 3, lose = 2),
                coachWinRate = WinRateData(win = 10, draw = 5, lose = 5),
                hasNormalMatches = true,
                hasCoachMatches = true
            ),
            onBackClick = {},
            onNormalMatchClick = {},
            onCoachMatchClick = {},
            onTradeClick = {},
            onAnalysisClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchHomeScreenLoadingPreview() {
    FigleComposeTheme {
        SearchHomeScreen(
            uiState = SearchHomeUiState(
                isLoading = true
            ),
            onBackClick = {},
            onNormalMatchClick = {},
            onCoachMatchClick = {},
            onTradeClick = {},
            onAnalysisClick = {}
        )
    }
}
