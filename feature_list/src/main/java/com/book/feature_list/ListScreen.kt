package com.book.feature_list

import android.net.Uri
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
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.book.domain.search.entities.BookEntities
import com.book.presentation_core.component.ItemCard


@Composable
fun ListScreen(listViewModel: ListViewModel = hiltViewModel(),onItemClick: (String) -> Unit) {
    val viewUiState by listViewModel.uiState.collectAsState()
    when(viewUiState.state){
        ListContract.ListState.Init -> {}
        ListContract.ListState.Error -> {}
        is ListContract.ListState.Success ->{
            val lazyPagingItems = (viewUiState.state as ListContract.ListState.Success).item.collectAsLazyPagingItems()
            ListLayout(lazyPagingItems = lazyPagingItems, onItemClick = onItemClick)
        }
    }
}

@Composable
fun ListLayout(lazyPagingItems: LazyPagingItems<BookEntities.Document>,onItemClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(lazyPagingItems.itemCount) {
            lazyPagingItems[it]?.let { item -> ItemCard(onItemClick = onItemClick, item = item) }
        }
    }
}


