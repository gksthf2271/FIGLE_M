package com.khs.figle_m.ui.feature.searchlist

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.khs.figle_m.common.preview.PreviewData

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchListContentPreview() {
    Surface {
        SearchListContent(
            userInfo = PreviewData.sampleUser.copy(accessId = "winner-ouid-123"),
            matchList = listOf(
                PreviewData.sampleMatchDetail,
                PreviewData.sampleMatchLose,
                PreviewData.sampleMatchDraw
            ),
            matchType = 50,
            onBack = { },
            onMatchClick = null
        )
    }
}

@Preview(showBackground = true, name = "Match List Item - Win")
@Composable
fun MatchListItemWinPreview() {
    Surface {
        MatchListItem(
            userInfo = PreviewData.sampleUser.copy(accessId = "winner-ouid-123"),
            match = PreviewData.sampleMatchDetail,
            onClick = { }
        )
    }
}

@Preview(showBackground = true, name = "Match List Item - Lose")
@Composable
fun MatchListItemLosePreview() {
    Surface {
        MatchListItem(
            userInfo = PreviewData.sampleUser.copy(accessId = "winner-ouid-123"),
            match = PreviewData.sampleMatchLose,
            onClick = { }
        )
    }
}

@Preview(showBackground = true, name = "Match List Item - Draw")
@Composable
fun MatchListItemDrawPreview() {
    Surface {
        MatchListItem(
            userInfo = PreviewData.sampleUser.copy(accessId = "winner-ouid-123"),
            match = PreviewData.sampleMatchDraw,
            onClick = { }
        )
    }
}
