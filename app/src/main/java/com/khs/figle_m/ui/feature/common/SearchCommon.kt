package com.khs.figle_m.ui.feature.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    Text(
        text = title,
        fontSize = fontSize,
        color = textColor,
        fontWeight = fontWeight,
        maxLines = 1
    )
}

@Composable
fun FigleSearch(
        modifier: Modifier,
        ) {

}