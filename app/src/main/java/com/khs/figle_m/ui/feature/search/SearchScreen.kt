package com.khs.figle_m.ui.feature.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    SearchScreenDetail()
}

@Composable
fun SearchScreenDetail() {

}