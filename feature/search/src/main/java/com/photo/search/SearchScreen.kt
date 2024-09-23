package com.photo.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit,
    isDualPane : Boolean,
    modifier: Modifier = Modifier
) {
    val viewUiState by searchViewModel.uiState.collectAsState()
    var searchKeyWord by rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(searchViewModel.effect){
        searchViewModel.effect.collect { effect ->
            when (effect) {
                is SearchContract.SearchSideEffect.ShowToast -> context.showToast(context.getString(effect.id))
                is SearchContract.SearchSideEffect.MoveDetailPage -> onNavigateToDetail.invoke(effect.item)
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

    Column(
        modifier = modifier
    ) {
        SearchBarLayout(
            modifier = Modifier.fillMaxWidth(),
            labelTitle = stringResource(id = R.string.search_label),
            text = searchKeyWord ?: "",
            onTextChange = { searchKeyWord = it }
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
        searchViewModel =searchViewModel) {
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
    content: @Composable () -> Unit
) {
    when {
        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error || loadState.prepend is LoadState.Error -> {
            searchViewModel.setEvent(
                SearchContract.SearchEvent.ShowErrorLayout(
                    message = stringResource(id = R.string.error_message)
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
