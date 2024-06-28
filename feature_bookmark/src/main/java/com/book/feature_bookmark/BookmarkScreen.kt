package com.book.feature_bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun BookmarkScreen(bookmarkViewmodel: BookmarkViewModel = hiltViewModel(), onItemClick: (String) -> Unit) {
    val viewUiState by bookmarkViewmodel.uiState.collectAsState()
    when(viewUiState.state){
        BookmarkContract.BookmarkState.Loading -> {}
        BookmarkContract.BookmarkState.Empty -> {}
        BookmarkContract.BookmarkState.Error -> {}
        is BookmarkContract.BookmarkState.Success ->{
            BookmarkListLayout(itemList = (viewUiState.state as BookmarkContract.BookmarkState.Success).itemList, onItemClick = onItemClick)
        }
    }
}

@Composable
fun BookmarkListLayout(itemList: List<BookEntities.Document>, onItemClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(itemList){item ->
            ItemCard(onItemClick = onItemClick, item = item)
        }
    }
}
