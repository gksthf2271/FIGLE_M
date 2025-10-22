package com.khs.figle_m.ui.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.khs.data.nexon_api.response.UserResponse
import com.khs.figle_m.ui.feature.home.HomeScreen

const val homeNavigationRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onShowError: (Int) -> Unit,
    onNavigateToSearchHome: (UserResponse) -> Unit
) {
    composable(
        route = homeNavigationRoute
    ) {
        HomeScreen(
            onShowError = onShowError,
            onNavigateToSearchHome = onNavigateToSearchHome
        )
    }
}
