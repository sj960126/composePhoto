package com.photo.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.CombinedLoadStates
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
    val viewUiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val rememberCoroutineScope  = rememberCoroutineScope()

    LaunchedEffect(searchViewModel.effect,lifecycleOwner){
        searchViewModel.effect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle).collect { effect ->
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
        SearchContent(
            state = viewUiState.state,
            isDualPane = isDualPane,
            searchViewModel = searchViewModel
        )
    }
}

@Composable
private fun SearchContent(
    state : SearchContract.SearchState,
    isDualPane: Boolean,
    searchViewModel: SearchViewModel
){
    when(state){
        SearchContract.SearchState.Init -> EmptyLayout(
            title = stringResource(
                id = R.string.search_empty_hint
            )
        )
        is SearchContract.SearchState.Empty -> EmptyLayout(title = state.message)
        is SearchContract.SearchState.Error -> EmptyLayout(title = state.message)
        is SearchContract.SearchState.Load -> {
            SearchResult(
                lazyPagingItems = state.item.collectAsLazyPagingItems(),
                isDualPane = isDualPane,
                onItemClick = {
                    searchViewModel.handleEvent(SearchContract.SearchEvent.ClickItem(it))
                },
                searchViewModel = searchViewModel
            )
        }
    }
}

@Composable
private fun SearchResult(
    lazyPagingItems: LazyPagingItems<PhotoEntities.Document>,
    isDualPane: Boolean,
    onItemClick: (String) -> Unit,
    searchViewModel: SearchViewModel) {
    HandleLoadState(
        loadState = lazyPagingItems.loadState,
        searchViewModel =searchViewModel,
        itemCount = lazyPagingItems.itemCount ?:0) {
        LayoutSelector(
            isDualPane = isDualPane,
            lazyPagingItems = lazyPagingItems,
            onItemClick = onItemClick,
            onBookmarkClick = { (item, isBookmarked)  ->
                searchViewModel.handleEvent(
                    if (isBookmarked) SearchContract.SearchEvent.SaveBookmark(item)
                    else SearchContract.SearchEvent.RemoveBookmark(item)
                )
            }
        )
    }
}

@Composable
private fun HandleLoadState(
    loadState: CombinedLoadStates,
    searchViewModel: SearchViewModel,
    itemCount : Int,
    content: @Composable () -> Unit,
) {
    when {
        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error || loadState.prepend is LoadState.Error -> {
            searchViewModel.setEvent(
                SearchContract.SearchEvent.ShowErrorLayout(
                    message = stringResource(id = R.string.error_message)
                )
            )
        }
        loadState.refresh is LoadState.NotLoading && itemCount == 0 -> {
            searchViewModel.setEvent(
                SearchContract.SearchEvent.ShowEmptyLayout(
                    message = stringResource(id = R.string.search_empty_hint)
                )
            )
        }
        else -> content()
    }
}

@Composable
private fun LayoutSelector(
    isDualPane: Boolean,
    lazyPagingItems: LazyPagingItems<PhotoEntities.Document>,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (Pair<PhotoEntities.Document, Boolean>) -> Unit
) {
    if (isDualPane) {
        DualPaneLayout(
            lazyPagingItems = lazyPagingItems,
            onItemClick = onItemClick,
            onBookmarkClick = onBookmarkClick
        )
    } else {
        SinglePaneLayout(
            lazyPagingItems = lazyPagingItems,
            onItemClick = onItemClick,
            onBookmarkClick = onBookmarkClick
        )
    }
}
