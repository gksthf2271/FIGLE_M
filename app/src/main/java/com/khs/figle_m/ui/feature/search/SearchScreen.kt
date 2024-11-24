package com.khs.figle_m.ui.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.khs.figle_m.R
import com.khs.figle_m.ui.feature.common.FigleSearch
import com.khs.figle_m.ui.feature.common.FigleTitleText
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onShowDialog: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    SearchScreenDetail(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        onShowDialog = onShowDialog,
        onShowSnackbar = onShowSnackbar
    )
}

@Composable
fun SearchScreenDetail(
    modifier: Modifier,
    onShowDialog: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (title, search) = createRefs()

            FigleTitleText(
                modifier = Modifier
                    .constrainAs(title) {
                        linkTo(top = parent.top, bottom = search.top)
                        linkTo(start = parent.start, end = parent.end)
                    }
                    .wrapContentSize(),
                title = androidx.compose.ui.res.stringResource(id = R.string.main_title),
            )

            FigleSearch(
                modifier = Modifier
                    .constrainAs(search) {
                        linkTo(top = title.bottom, bottom = parent.bottom, topMargin = 100.dp)
                        linkTo(start = parent.start, end = parent.end)
                    }
                    .fillMaxWidth()
                    .wrapContentHeight(),
                searchQuery = searchQuery,
                onSearchQueryChanged = {
                    searchQuery = it
                },
                onSearchTriggered = {
                    scope.launch {
                        onShowSnackbar(it, null)
                    }
                }
            )
        }
    }
}