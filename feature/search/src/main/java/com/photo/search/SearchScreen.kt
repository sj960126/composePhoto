package com.photo.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@Composable
fun SearchRoute(
    isDualPane : Boolean,
    onNavigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
){
    val viewUiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val rememberCoroutineScope  = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(searchViewModel.effect){
        searchViewModel.effect.collect { effect ->
            when (effect) {
                is SearchContract.SearchSideEffect.ShowToast -> context.showToast(context.getString(effect.id))
                is SearchContract.SearchSideEffect.MoveDetailPage -> onNavigateToDetail(effect.item)
            }
        }
    }
    SearchScreen(
        isDualPane = isDualPane,
        searchKeyword = viewUiState.searchKeyWord?:"",
        modifier = modifier,
        uiState = viewUiState.state,
        rememberCoroutineScope = rememberCoroutineScope,
        onSaveBookmark = {
            searchViewModel.handleEvent(
                SearchContract.SearchEvent.SaveBookmark(it)
            )
        },
        onRemoveBookmark = {
            searchViewModel.handleEvent(
                SearchContract.SearchEvent.RemoveBookmark(it)
            )
        },
        onNavigateToDetail = {
            searchViewModel.handleEvent(SearchContract.SearchEvent.ClickItem(it))
        },
        onShowErrorLayout = {
            searchViewModel.setEvent(
                SearchContract.SearchEvent.ShowErrorLayout(it)
            )
        },
        onSearchKeyword = { keyword ->
            searchViewModel.handleEvent(SearchContract.SearchEvent.Search(keyword))
        },
    )
}
@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    isDualPane : Boolean,
    searchKeyword : String,
    uiState : SearchContract.SearchUiState,
    rememberCoroutineScope : CoroutineScope,
    onSaveBookmark :(PhotoEntities.Document) -> Unit,
    onRemoveBookmark : (PhotoEntities.Document) -> Unit,
    onShowErrorLayout : (String) -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onSearchKeyword : (String) -> Unit,
) {

    Column(
        modifier = modifier
    ) {
        SearchBarLayout(
            modifier = Modifier.fillMaxWidth(),
            labelTitle = stringResource(id = R.string.search_label),
            text = searchKeyword,
            onTextChange = {
                rememberCoroutineScope.launch {
                    flowOf(it)
                        .debounce(1000)
                        .collectLatest { keyword ->
                            onSearchKeyword(keyword)
                        }
                }
            }
        )
        when(uiState){
            SearchContract.SearchUiState.Init -> EmptyLayout(
                title = stringResource(
                    id = R.string.search_empty_hint
                )
            )
            is SearchContract.SearchUiState.Empty -> EmptyLayout(title = uiState.message)
            is SearchContract.SearchUiState.Error -> EmptyLayout(title = uiState.message)
            is SearchContract.SearchUiState.Load -> {
                val lazyPagingItems = uiState.item.collectAsLazyPagingItems()
                LoadItemsHandler(
                    lazyPagingItems = lazyPagingItems,
                    isDualPane = isDualPane,
                    onItemClick = onNavigateToDetail,
                    onSaveBookmark = onSaveBookmark,
                    onRemoveBookmark = onRemoveBookmark,
                    onShowErrorLayout = onShowErrorLayout
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
    onSaveBookmark :(PhotoEntities.Document) -> Unit,
    onRemoveBookmark : (PhotoEntities.Document) -> Unit,
    onShowErrorLayout : (String) -> Unit,
) {
    val loadState = lazyPagingItems.loadState

    when {
        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error || loadState.prepend is LoadState.Error -> {
            onShowErrorLayout(stringResource(id = R.string.error_message))
        }
        else -> {
            if (isDualPane) {
                DualPaneLayout(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = onItemClick,
                    onBookmarkClick = {
                        val (item,isSelect) = it
                        if(isSelect) onSaveBookmark(item) else onRemoveBookmark(item)
                    }
                )
            } else {
                SinglePaneLayout(
                    lazyPagingItems = lazyPagingItems,
                    onItemClick = onItemClick,
                    onBookmarkClick = {
                        val (item,isSelect) = it
                        if(isSelect) onSaveBookmark(item) else onRemoveBookmark(item)
                    }
                )
            }
        }
    }
}