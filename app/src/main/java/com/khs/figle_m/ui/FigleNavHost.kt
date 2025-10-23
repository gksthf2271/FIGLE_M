package com.khs.figle_m.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.khs.figle_m.ui.feature.search.navigation.searchNavigationRoute
import com.khs.figle_m.ui.feature.search.navigation.searchScreen
import com.khs.figle_m.ui.feature.home.navigation.homeNavigationRoute
import com.khs.figle_m.ui.feature.home.navigation.homeScreen
import com.khs.figle_m.ui.feature.ranking.navigation.rankingScreen
import com.khs.figle_m.ui.feature.analytics.navigation.analyticsScreen

@Composable
fun FigleNavHost(
    modifier: Modifier = Modifier,
    appState: FigleAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onShowError: (Int) -> Unit = {},
    startDestination: String = homeNavigationRoute,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onShowError = onShowError,
            onNavigateToSearchHome = { userResponse ->
                // TODO: Navigate to SearchHome screen
                // For now, we'll need to implement SearchHome in Compose first
                // or use the existing Fragment navigation
            }
        )

        rankingScreen(
            onClose = {
                navController.popBackStack()
            },
            onNavigateToSearch = { name, price ->
                // TODO: Navigate to SearchHome with name and price
                // or call the search API directly
            },
            onShowError = onShowError
        )

        analyticsScreen(
            onBack = {
                navController.popBackStack()
            },
            onPlayerClick = { analyticsPlayer ->
                // TODO: Navigate to PlayerDetail screen
            }
        )

        searchScreen(
            onShowDialog = {},
            onShowSnackbar = onShowSnackbar
        )
    }
}