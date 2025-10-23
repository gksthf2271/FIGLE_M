package com.khs.figle_m.ui.feature.ranking.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.khs.figle_m.ui.feature.ranking.RankingScreen

const val rankingNavigationRoute = "ranking_route"

fun NavController.navigateToRanking(navOptions: NavOptions? = null) {
    this.navigate(rankingNavigationRoute, navOptions)
}

fun NavGraphBuilder.rankingScreen(
    onClose: () -> Unit,
    onNavigateToSearch: ((String, String) -> Unit)? = null,
    onShowError: (Int) -> Unit
) {
    composable(
        route = rankingNavigationRoute
    ) {
        RankingScreen(
            onClose = onClose,
            onNavigateToSearch = onNavigateToSearch,
            onShowError = onShowError
        )
    }
}
