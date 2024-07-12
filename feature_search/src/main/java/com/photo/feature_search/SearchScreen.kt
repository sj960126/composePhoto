package com.photo.feature_search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.component.DualPaneLayout
import com.photo.presentation_core.component.EmptyLayout
import com.photo.presentation_core.component.SearchBarLayout
import com.photo.presentation_core.component.SinglePaneLayout
import com.photo.presentation_core.extension.showToast
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
                    context.showToast(context.getString(effect.id))
                }
            }
        }
    }

    LaunchedEffect(searchKeyWord) {
        snapshotFlow { searchKeyWord }
            .filterNotNull()
            .debounce(1000)
            .collect { keyword ->
                searchViewModel.handleEvent(SearchContract.SearchEvent.Search(keyword))
            }
    }

    Column {
        SearchBarLayout(modifier = Modifier.fillMaxWidth(), labelTitle = stringResource(id = com.photo.presentation_core.R.string.search_label), text = searchKeyWord?:"", onTextChange = {searchKeyWord = it})
        when(viewUiState.state){
            SearchContract.SearchState.Init -> EmptyLayout(title = stringResource(id =com.photo.presentation_core.R.string.search_empty_hint))
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
            searchViewModel.setEvent(SearchContract.SearchEvent.ShowErrorLayout(message = stringResource(id = com.photo.presentation_core.R.string.error_message)))
        }
        else -> {
            if (isDualPane) {
                DualPaneLayout(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = onItemClick,
                    onBookmarkClick = {
                        searchViewModel.handleEvent(
                            if (it.second) SearchContract.SearchEvent.SaveBookmark(it.first)
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
                            if (it.second) SearchContract.SearchEvent.SaveBookmark(it.first)
                            else SearchContract.SearchEvent.RemoveBookmark(it.first)
                        )
                    }
                )
            }
        }
    }
}