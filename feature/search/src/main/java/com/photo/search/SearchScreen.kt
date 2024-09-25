package com.photo.search

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
import com.photo.ui.R
import com.photo.component.DualPaneLayout
import com.photo.component.EmptyLayout
import com.photo.component.SearchBarLayout
import com.photo.component.SinglePaneLayout
import com.photo.extension.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit,
    isDualPane : Boolean,
    modifier: Modifier = Modifier
) {
    val viewUiState by searchViewModel.uiState.collectAsState()
    val rememberCoroutineScope  = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(searchViewModel.effect){
        searchViewModel.effect.collect { effect ->
            when (effect) {
                is SearchContract.SearchSideEffect.ShowToast -> context.showToast(context.getString(effect.id))
                is SearchContract.SearchSideEffect.MoveDetailPage -> onNavigateToDetail.invoke(effect.item)
            }
        }
    }

    Column(
        modifier = modifier
    ) {
        SearchBarLayout(
            modifier = Modifier.fillMaxWidth(),
            labelTitle = stringResource(id = R.string.search_label),
            text = viewUiState.searchKeyWord ?: "",
            onTextChange = {
                rememberCoroutineScope.launch {
                    flowOf(it)
                        .debounce(1000)
                        .collectLatest { keyword ->
                            searchViewModel.handleEvent(SearchContract.SearchEvent.Search(keyword))
                        }
                }
            }
        )
        when(viewUiState.state){
            SearchContract.SearchState.Init -> EmptyLayout(
                title = stringResource(
                    id = R.string.search_empty_hint
                )
            )
            is SearchContract.SearchState.Empty -> EmptyLayout(title = (viewUiState.state as SearchContract.SearchState.Empty).message)
            is SearchContract.SearchState.Error -> EmptyLayout(title = (viewUiState.state as SearchContract.SearchState.Error).message)
            is SearchContract.SearchState.Load -> {
                val lazyPagingItems = (viewUiState.state as SearchContract.SearchState.Load).item.collectAsLazyPagingItems()
                LoadItemsHandler(
                    lazyPagingItems = lazyPagingItems,
                    isDualPane = isDualPane,
                    onItemClick = {
                        searchViewModel.handleEvent(SearchContract.SearchEvent.ClickItem(it))
                    },
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
            searchViewModel.setEvent(
                SearchContract.SearchEvent.ShowErrorLayout(
                    message = stringResource(
                        id = R.string.error_message
                    )
                )
            )
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