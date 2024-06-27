package com.book.feature_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.book.domain.search.entities.BookEntities
import com.book.presentation_core.component.ItemCard
import com.book.presentation_core.design_system.LocalColors
import com.book.presentation_core.design_system.LocalTypography
import com.book.presentation_core.extension.noRippleClickable


@Composable
fun ListScreen(navController: NavController,listViewModel: ListViewModel = hiltViewModel()) {
    val viewUiState by listViewModel.uiState.collectAsState()
    when(viewUiState.state){
        ListContract.ListState.Init -> {}
        ListContract.ListState.Error -> {}
        is ListContract.ListState.Success ->{
            val lazyPagingItems = (viewUiState.state as ListContract.ListState.Success).item.collectAsLazyPagingItems()
            ListLayout(lazyPagingItems = lazyPagingItems, navController = navController)
        }
    }
}

@Composable
fun ListLayout(lazyPagingItems: LazyPagingItems<BookEntities.Document>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(lazyPagingItems.itemCount) {
            lazyPagingItems[it]?.let { item -> ItemCard(onItemClick = {}, item = item) }
        }
    }
}


