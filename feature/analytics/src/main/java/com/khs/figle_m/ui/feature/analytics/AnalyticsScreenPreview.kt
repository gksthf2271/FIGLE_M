package com.khs.figle_m.ui.feature.analytics

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.khs.figle_m.common.preview.PreviewData

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AnalyticsContentPreview() {
    Surface {
        AnalyticsContent(
            ratingTopPlayers = PreviewData.sampleRatingTopPlayers,
            goalTopPlayers = PreviewData.sampleGoalTopPlayers,
            assistTopPlayers = PreviewData.sampleAssistTopPlayers,
            onBack = { },
            onPlayerClick = null
        )
    }
}

@Preview(showBackground = true, name = "Analytics Section - Rating")
@Composable
fun AnalyticsSectionRatingPreview() {
    Surface {
        AnalyticsSection(
            title = "Rating TOP 10",
            players = PreviewData.sampleRatingTopPlayers,
            rowType = RowType.RATING,
            onPlayerClick = null
        )
    }
}

@Preview(showBackground = true, name = "Analytics Section - Goal")
@Composable
fun AnalyticsSectionGoalPreview() {
    Surface {
        AnalyticsSection(
            title = "Goal TOP 5",
            players = PreviewData.sampleGoalTopPlayers,
            rowType = RowType.GOAL,
            onPlayerClick = null
        )
    }
}

@Preview(showBackground = true, name = "Analytics Section - Assist")
@Composable
fun AnalyticsSectionAssistPreview() {
    Surface {
        AnalyticsSection(
            title = "Assist TOP 5",
            players = PreviewData.sampleAssistTopPlayers,
            rowType = RowType.ASSIST,
            onPlayerClick = null
        )
    }
}

@Preview(showBackground = true, name = "Player Card Preview")
@Composable
fun PlayerCardPreview() {
    Surface {
        PlayerCard(
            player = PreviewData.samplePlayer1,
            rank = 1,
            rowType = RowType.RATING,
            onClick = { }
        )
    }
}
