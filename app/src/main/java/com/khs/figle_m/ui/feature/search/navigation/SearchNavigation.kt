package com.khs.figle_m.ui.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.khs.figle_m.ui.feature.search.SearchScreen

const val playerId = "playerId"
const val searchNavigationRoute = "search_route/{$playerId}"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchNavigationRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(
    onShowDialog: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(
        route = searchNavigationRoute,
        arguments = listOf(
            navArgument(playerId) { type = NavType.StringType },
        ),
    ) {
        SearchScreen(
            onShowDialog = onShowDialog,
            onShowSnackbar = onShowSnackbar
        )
    }
}
