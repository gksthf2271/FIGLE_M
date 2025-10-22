package com.khs.figle_m.ui.feature.common

// Re-export common components for backward compatibility
import com.khs.figle_m.common.ui.component.FigleSearch as CommonFigleSearch
import com.khs.figle_m.common.ui.component.FigleTitleText as CommonFigleTitleText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun FigleTitleText(
    modifier: Modifier,
    title: String,
    fontSize : TextUnit = 50.sp,
    textColor: Color = MaterialTheme.colorScheme.primary,
    fontWeight: FontWeight = FontWeight.W600
) {
    CommonFigleTitleText(
        modifier = modifier,
        title = title,
        fontSize = fontSize,
        textColor = textColor,
        fontWeight = fontWeight
    )
}

@Composable
fun FigleSearch(
    modifier: Modifier,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit
) {
    CommonFigleSearch(
        modifier = modifier,
        searchQuery = searchQuery,
        onSearchQueryChanged = onSearchQueryChanged,
        onSearchTriggered = onSearchTriggered
    )
}