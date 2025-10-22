package com.khs.figle_m.ui.feature.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khs.data.nexon_api.response.UserResponse
import com.khs.figle_m.feature.home.HomeUIState
import com.khs.figle_m.feature.home.HomeViewModel
import com.khs.figle_m.feature.home.R
import com.khs.figle_m.common.ui.component.FigleSearch
import com.khs.figle_m.common.ui.component.FigleTitleText

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onShowError: (Int) -> Unit,
    onNavigateToSearchHome: (UserResponse) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Handle UI state changes
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is HomeUIState.Success -> {
                onNavigateToSearchHome(state.userResponse)
                homeViewModel.resetState()
            }
            is HomeUIState.Failed -> {
                onShowError(state.errorCode)
                homeViewModel.resetState()
            }
            else -> {
                // Do nothing for Idle and Loading states
            }
        }
    }

    HomeScreenDetail(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        onSearchTriggered = { searchQuery ->
            if (searchQuery.isBlank()) {
                Toast.makeText(context, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                homeViewModel.searchUser(searchQuery)
            }
        }
    )
}

@Composable
fun HomeScreenDetail(
    modifier: Modifier,
    onSearchTriggered: (String) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

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
                title = stringResource(id = R.string.home_title),
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
                onSearchTriggered = onSearchTriggered
            )
        }
    }
}
