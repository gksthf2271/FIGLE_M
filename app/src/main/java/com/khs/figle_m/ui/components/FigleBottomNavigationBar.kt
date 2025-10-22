package com.khs.figle_m.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.khs.figle_m.ui.theme.FigleComposeTheme

/**
 * 하단 네비게이션 바 아이템
 */
sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "홈")
    object Search : BottomNavItem("search", Icons.Filled.Search, "검색")
    object Ranking : BottomNavItem("ranking", Icons.Filled.Star, "랭킹")
    object Profile : BottomNavItem("profile", Icons.Filled.Person, "프로필")
}

/**
 * FIGLE 앱의 하단 네비게이션 바
 *
 * @param selectedRoute 현재 선택된 라우트
 * @param onNavigate 네비게이션 아이템 선택 시 호출되는 콜백
 * @param modifier Modifier
 */
@Composable
fun FigleBottomNavigationBar(
    selectedRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Ranking,
        BottomNavItem.Profile
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = selectedRoute == item.route,
                onClick = { onNavigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FigleBottomNavigationBarPreview() {
    FigleComposeTheme {
        FigleBottomNavigationBar(
            selectedRoute = "home",
            onNavigate = {}
        )
    }
}
