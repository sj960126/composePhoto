package com.book.feature_bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.book.domain.common.entities.BookEntities
import com.book.presentation_core.component.ItemCard

@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel = hiltViewModel(), onItemClick: (String) -> Unit) {
    val viewUiState by bookmarkViewModel.uiState.collectAsState()
    Column() {
        FilterLayout()
        PriceFilter()
        AuthorFilter()
        when(viewUiState.state){
            BookmarkContract.BookmarkState.Loading -> {}
            BookmarkContract.BookmarkState.Empty -> {}
            BookmarkContract.BookmarkState.Error -> {}
            is BookmarkContract.BookmarkState.Success ->{
                BookmarkListLayout(
                    itemList = (viewUiState.state as BookmarkContract.BookmarkState.Success).itemList,
                    onItemClick = onItemClick,
                    onBookmarkClick = {
                        bookmarkViewModel.handleEvent(if(it.second) BookmarkContract.BookmarkEvent.AddBookmark(it.first) else BookmarkContract.BookmarkEvent.RemoveBookmark(it.first))
                    }
                )
            }
        }
    }
}
enum class SortOrder {
    ASCENDING,
    DESCENDING
}
@Composable
fun FilterLayout(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        // Sorting buttons
        Button(onClick = {  }) {
            Text("오름차순 정렬")
        }
        Button(onClick = {  }) {
            Text("내림차순 정렬")
        }

    }
}
@Composable
fun PriceFilter(){
    var filterPrice by remember { mutableStateOf("") }

    OutlinedTextField(
        value = filterPrice,
        onValueChange = { filterPrice = it },
        label = { Text("금액 필터") },
        modifier = Modifier.width(120.dp)
    )
}
@Composable
fun AuthorFilter(){
    var filterAuthor by remember { mutableStateOf("") }

    // Filter by author (optional)
    OutlinedTextField(
        value = filterAuthor,
        onValueChange = { filterAuthor = it },
        label = { Text("저자 검색") },
        modifier = Modifier.width(120.dp)
    )
}
@Composable
fun BookmarkListLayout(itemList: List<BookEntities.Document>, onItemClick: (String) -> Unit,onBookmarkClick : (Pair<BookEntities.Document,Boolean>) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(itemList){item ->
            ItemCard(onItemClick = onItemClick, item = item,onBookmarkClick = {onBookmarkClick.invoke(Pair(item,it))})
        }
    }
}
