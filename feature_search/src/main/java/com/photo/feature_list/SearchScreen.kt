package com.photo.feature_list

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.layout.FoldingFeature
import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.component.DualPaneLayout
import com.photo.presentation_core.component.EmptyLayout
import com.photo.presentation_core.component.SearchBarLayout
import com.photo.presentation_core.component.SinglePaneLayout
import com.photo.presentation_core.extension.showToast
import com.photo.presentation_core.state.rememberFoldableState
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull


@Composable
fun SearchScreen(searchViewModel: SearchViewModel = hiltViewModel(), onItemClick: (String) -> Unit, isDualPane : Boolean) {
    val viewUiState by searchViewModel.uiState.collectAsState()
    var searchKeyWord by rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current

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
            .filterNotNull()
            .debounce(1000)
            .collect { keyword ->
                Log.d("디버그","디바운스${keyword}")
                searchViewModel.handleEvent(SearchContract.SearchEvent.Search(keyword))
            }
    }

    Column {
        SearchBarLayout(modifier = Modifier.fillMaxWidth(), labelTitle = "검색", text = searchKeyWord?:"", onTextChange = {searchKeyWord = it})
        when(viewUiState.state){
            SearchContract.SearchState.Init -> EmptyLayout(title = "검색어를 입력해주세요.")
            is SearchContract.SearchState.Empty -> EmptyLayout(title = (viewUiState.state as SearchContract.SearchState.Empty).message)
            is SearchContract.SearchState.Error -> EmptyLayout(title = (viewUiState.state as SearchContract.SearchState.Error).message)
            is SearchContract.SearchState.Load -> {
                val lazyPagingItems = (viewUiState.state as SearchContract.SearchState.Load).item.collectAsLazyPagingItems()
                LoadItemsHandler(
                    lazyPagingItems = lazyPagingItems,
                    isDualPane = isDualPane,
                    onItemClick = onItemClick,
                    searchViewModel = searchViewModel
                )
            }
        }
    }
}
@Composable
private fun LoadItemsHandler(
    lazyPagingItems: LazyPagingItems<PhotoEntities.Document>,
    isDualPane: Boolean,
    onItemClick: (String) -> Unit,
    searchViewModel: SearchViewModel
) {
    val loadState = lazyPagingItems.loadState

    when {
        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error || loadState.prepend is LoadState.Error -> {
            searchViewModel.setEvent(SearchContract.SearchEvent.ShowErrorLayout(message = "에러발생"))
        }
        else -> {
            if (isDualPane) {
                DualPaneLayout(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = onItemClick,
                    onBookmarkClick = {
                        searchViewModel.handleEvent(
                            if (it.second) SearchContract.SearchEvent.AddBookmark(it.first)
                            else SearchContract.SearchEvent.RemoveBookmark(it.first)
                        )
                    }
                )
            } else {
                SinglePaneLayout(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = onItemClick,
                    onBookmarkClick = {
                        searchViewModel.handleEvent(
                            if (it.second) SearchContract.SearchEvent.AddBookmark(it.first)
                            else SearchContract.SearchEvent.RemoveBookmark(it.first)
                        )
                    }
                )
            }
        }
    }
}