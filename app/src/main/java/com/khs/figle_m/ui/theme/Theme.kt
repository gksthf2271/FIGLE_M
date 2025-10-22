package com.khs.figle_m.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = ColorPrimary,
    secondary = ColorAccent,
    tertiary = AccentColorV2,
    background = MainBackground,
    surface = PrimaryColorV2,
    onPrimary = TextColor,
    onSecondary = TextColor,
    onTertiary = TextColor,
    onBackground = TextColor,
    onSurface = TextColor,
    error = RedColor,
    onError = TextColor
)

private val LightColorScheme = lightColorScheme(
    primary = ColorPrimary,
    secondary = ColorAccent,
    tertiary = AccentColorV2,
    background = MainBackground,
    surface = PrimaryColorV2,
    onPrimary = TextColor,
    onSecondary = TextColor,
    onTertiary = TextColor,
    onBackground = TextColor,
    onSurface = TextColor,
    error = RedColor,
    onError = TextColor
)

/**
 * FIGLE_M 앱의 메인 테마
 *
 * @param darkTheme 다크 테마 사용 여부 (기본값: 시스템 설정 따름)
 * @param content Composable 콘텐츠
 */
@Composable
fun FigleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = FigleTypography,
        content = content
    )
}

/**
 * 기존 View와 Compose를 혼용할 때 사용하는 테마
 * Fragment나 일부 화면만 Compose로 전환할 때 사용
 */
@Composable
fun FigleComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = FigleTypography,
        content = content
    )
}
