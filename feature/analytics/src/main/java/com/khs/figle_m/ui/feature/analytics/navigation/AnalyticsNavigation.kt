package com.khs.figle_m.ui.feature.analytics.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.khs.figle_m.common.model.AnalyticsPlayer
import com.khs.figle_m.ui.feature.analytics.AnalyticsScreen

const val analyticsNavigationRoute = "analytics_route"
const val accessIdArg = "accessId"
const val matchIdsArg = "matchIds"

fun NavController.navigateToAnalytics(
    accessId: String,
    matchIds: List<String>,
    navOptions: NavOptions? = null
) {
    val matchIdsString = matchIds.joinToString(",")
    this.navigate("$analyticsNavigationRoute/$accessId/$matchIdsString", navOptions)
}

fun NavGraphBuilder.analyticsScreen(
    onBack: () -> Unit,
    onPlayerClick: ((AnalyticsPlayer) -> Unit)? = null
) {
    composable(
        route = "$analyticsNavigationRoute/{$accessIdArg}/{$matchIdsArg}",
        arguments = listOf(
            navArgument(accessIdArg) { type = NavType.StringType },
            navArgument(matchIdsArg) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val accessId = backStackEntry.arguments?.getString(accessIdArg) ?: ""
        val matchIdsString = backStackEntry.arguments?.getString(matchIdsArg) ?: ""
        val matchIdList = matchIdsString.split(",")

        AnalyticsScreen(
            accessId = accessId,
            matchIdList = matchIdList,
            onBack = onBack,
            onPlayerClick = onPlayerClick
        )
    }
}
