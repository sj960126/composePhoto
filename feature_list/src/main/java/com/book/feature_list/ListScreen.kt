package com.book.feature_list

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.book.domain.common.entities.BookEntities
import com.book.presentation_core.component.ItemCard


@Composable
fun ListScreen(listViewModel: ListViewModel = hiltViewModel(),onItemClick: (String) -> Unit) {
    val viewUiState by listViewModel.uiState.collectAsState()
    when(viewUiState.state){
        ListContract.ListState.Loading -> {}
        ListContract.ListState.Empty -> {}
        ListContract.ListState.Error -> {}
        is ListContract.ListState.Success ->{
            val lazyPagingItems = (viewUiState.state as ListContract.ListState.Success).item.collectAsLazyPagingItems()
            ListLayout(
                lazyPagingItems = lazyPagingItems,
                onItemClick = onItemClick,
                onBookmarkClick = {
                    listViewModel.handleEvent(if(it.second) ListContract.ListEvent.AddBookmark(it.first) else ListContract.ListEvent.RemoveBookmark(it.first))
                }
            )
        }
    }
}

@Composable
fun ListLayout(lazyPagingItems: LazyPagingItems<BookEntities.Document>, onItemClick: (String) -> Unit, onBookmarkClick : (Pair<BookEntities.Document,Boolean>) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { item ->
                ItemCard(onItemClick = onItemClick, item = item, onBookmarkClick = {onBookmarkClick.invoke(Pair(item,it))})
            }
        }
    }
}
