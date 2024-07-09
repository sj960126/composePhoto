package com.photo.feature_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.layout.FoldingFeature
import com.photo.presentation_core.component.DualPaneLayout
import com.photo.presentation_core.component.EmptyLayout
import com.photo.presentation_core.component.SearchBarLayout
import com.photo.presentation_core.component.SinglePaneLayout
import com.photo.presentation_core.extension.showToast
import com.photo.presentation_core.state.rememberFoldableState
import kotlinx.coroutines.flow.debounce


@Composable
fun SearchScreen(searchViewModel: SearchViewModel = hiltViewModel(), onItemClick: (String) -> Unit) {
    val viewUiState by searchViewModel.uiState.collectAsState()
    var searchKeyWord by remember { mutableStateOf("") }
    val context = LocalContext.current
    val foldableState by rememberFoldableState(context)
    val isDualPane = foldableState?.state == FoldingFeature.State.HALF_OPENED && foldableState?.isSeparating == true

    LaunchedEffect(searchViewModel.effect){
        searchViewModel.effect.collect { effect ->
            when (effect) {
                is SearchContract.SearchSideEffect.ShowToast -> {
                    context.showToast(effect.message)
                }
            }
        }
    }
    LaunchedEffect(searchKeyWord) {
        snapshotFlow { searchKeyWord }
            .debounce(1000)
            .collect { keyword ->
                searchViewModel.handleEvent(SearchContract.SearchEvent.Search(keyword))
            }
    }
    Column {
        SearchBarLayout(modifier = Modifier.fillMaxWidth(), labelTitle = "검색", text = searchKeyWord, onTextChange = {searchKeyWord = it})
        when(viewUiState.state){
            SearchContract.SearchState.Loading -> {}
            SearchContract.SearchState.Empty -> EmptyLayout(title = "상품이 없습니다.")
            is SearchContract.SearchState.Success ->{
                val lazyPagingItems = (viewUiState.state as SearchContract.SearchState.Success).item.collectAsLazyPagingItems()
                if (isDualPane) {
                    DualPaneLayout(
                        lazyPagingItems = lazyPagingItems,
                        onItemClick = onItemClick,
                        onBookmarkClick = {
                            searchViewModel.handleEvent(if(it.second) SearchContract.SearchEvent.AddBookmark(it.first) else SearchContract.SearchEvent.RemoveBookmark(it.first))
                        }
                    )
                } else {
                    SinglePaneLayout(
                        lazyPagingItems = lazyPagingItems,
                        onItemClick = onItemClick,
                        onBookmarkClick = {
                            searchViewModel.handleEvent(if(it.second) SearchContract.SearchEvent.AddBookmark(it.first) else SearchContract.SearchEvent.RemoveBookmark(it.first))
                        }
                    )
                }
            }
        }
    }
}
