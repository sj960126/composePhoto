package com.book.feature_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.book.domain.search.entities.BookEntities
import com.book.presentation_core.design_system.LocalTypography
import com.book.presentation_core.extension.noRippleClickable


@Composable
fun ListScreen(navController: NavController,listViewModel: ListViewModel = hiltViewModel()) {
    val viewUiState by listViewModel.uiState.collectAsState()
    when(viewUiState.state){
        ListContract.ListState.Init -> {}
        ListContract.ListState.Error -> {}
        is ListContract.ListState.Success ->{
            ListLayout(listViewModel = listViewModel, pagingData = (viewUiState.state as ListContract.ListState.Success).item, navController = navController)
        }
    }
    val lazyPagingItems = listViewModel.getSearchResults().collectAsLazyPagingItems()

    Column{

    }
}

@Composable
fun ListLayout(listViewModel: ListViewModel, pagingData: PagingData<BookEntities.Document>, navController: NavController) {
    val lazyPagingItems = pagingData.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(lazyPagingItems) { index, item ->
            ListItemCard(navController = navController, item = item)
        }
    }
}

@Composable
fun ListItemCard(navController: NavController, item: BookEntities.Document) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .noRippleClickable {
                // Navigate to detail screen
                // navController.navigate("detail/${item.id}")
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = LocalTypography.current.body2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.contents,
                style = LocalTypography.current.body2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}