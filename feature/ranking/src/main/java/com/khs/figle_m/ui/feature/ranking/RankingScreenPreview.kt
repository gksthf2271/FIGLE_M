package com.khs.figle_m.ui.feature.ranking

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.khs.figle_m.common.preview.PreviewData

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RankingContentPreview() {
    Surface {
        RankingContent(
            rankerList = PreviewData.sampleRankerList,
            selectedRanker = PreviewData.sampleRanker1,
            onRankerClick = { },
            onClose = { },
            onSearch = { }
        )
    }
}

@Preview(showBackground = true, name = "Ranking Item Preview")
@Composable
fun RankingListItemPreview() {
    Surface {
        RankingListItem(
            ranker = PreviewData.sampleRanker1,
            onClick = { }
        )
    }
}

@Preview(showBackground = true, name = "Ranking Top Section Preview")
@Composable
fun RankingTopSectionPreview() {
    Surface {
        RankingTopSection(
            ranker = PreviewData.sampleRanker1,
            onSearch = { }
        )
    }
}
