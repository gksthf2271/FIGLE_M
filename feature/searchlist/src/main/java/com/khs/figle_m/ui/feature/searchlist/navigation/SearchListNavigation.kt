package com.khs.figle_m.ui.feature.searchlist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.data.nexon_api.response.UserResponse
import com.khs.figle_m.ui.feature.searchlist.SearchListScreen

const val searchListNavigationRoute = "searchlist_route"
const val userInfoArg = "userInfo"
const val matchIdsArg = "matchIds"
const val matchTypeArg = "matchType"

fun NavController.navigateToSearchList(
    userInfo: UserResponse,
    matchIds: List<String>,
    matchType: Int,
    navOptions: NavOptions? = null
) {
    val matchIdsString = matchIds.joinToString(",")
    // For simplicity, we'll pass the ouid and nickname separately
    this.navigate("$searchListNavigationRoute/${userInfo.ouid}/${userInfo.nickname}/$matchType/$matchIdsString", navOptions)
}

fun NavGraphBuilder.searchListScreen(
    onBack: () -> Unit,
    onMatchClick: ((MatchDetailResponse) -> Unit)? = null
) {
    composable(
        route = "$searchListNavigationRoute/{ouid}/{nickname}/{$matchTypeArg}/{$matchIdsArg}",
        arguments = listOf(
            navArgument("ouid") { type = NavType.StringType },
            navArgument("nickname") { type = NavType.StringType },
            navArgument(matchTypeArg) { type = NavType.IntType },
            navArgument(matchIdsArg) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val ouid = backStackEntry.arguments?.getString("ouid") ?: ""
        val nickname = backStackEntry.arguments?.getString("nickname") ?: ""
        val matchType = backStackEntry.arguments?.getInt(matchTypeArg) ?: 50
        val matchIdsString = backStackEntry.arguments?.getString(matchIdsArg) ?: ""
        val matchIdList = matchIdsString.split(",").filter { it.isNotBlank() }

        // Create minimal UserResponse
        val userInfo = UserResponse(
            ouid = ouid,
            nickname = nickname,
            level = "",
            accessId = "",
            teamPrice = ""
        )

        SearchListScreen(
            userInfo = userInfo,
            matchIdList = matchIdList,
            matchType = matchType,
            onBack = onBack,
            onMatchClick = onMatchClick
        )
    }
}
