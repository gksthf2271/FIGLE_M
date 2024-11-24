package com.khs.figle_m.ui.feature.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.khs.figle_m.R
import com.khs.figle_m.ui.feature.common.FigleSearch
import com.khs.figle_m.ui.feature.common.FigleTitleText

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    SearchScreenDetail()
}

@Composable
fun SearchScreenDetail() {
    ConstraintLayout {
        val (title, search) = createRefs()

        FigleTitleText(
            modifier = Modifier
                .constrainAs(title) {
                    linkTo(top = parent.top, bottom = search.top)
                    linkTo(start = parent.start, end = parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight(),
            title = androidx.compose.ui.res.stringResource(id = R.string.main_title),
        )

        FigleSearch(
            modifier = Modifier
                .constrainAs(search) {
                    linkTo(top = title.bottom, bottom = parent.bottom)
                    linkTo(start = parent.start, end = parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}