package com.khs.figle_m.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.khs.figle_m.ui.feature.search.navigation.searchNavigationRoute
import com.khs.figle_m.ui.feature.search.navigation.searchScreen

@Composable
fun FigleNavHost(
    modifier: Modifier = Modifier,
    appState: FigleAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    startDestination: String = searchNavigationRoute,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        searchScreen(
            onShowDialog = {},
            onShowSnackbar = onShowSnackbar
        )
    }
}