package com.photo.feature_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.domain.common.entities.BookEntities
import com.photo.presentation_core.component.EmptyLayout
import com.photo.presentation_core.component.ItemRow
import com.photo.presentation_core.extension.showToast


@Composable
fun ListScreen(listViewModel: ListViewModel = hiltViewModel(),onItemClick: (String) -> Unit) {
    val viewUiState by listViewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(listViewModel.effect){
        listViewModel.effect.collect { effect ->
            when (effect) {
                is ListContract.ListSideEffect.ShowToast -> {
                    context.showToast(effect.message)
                }
            }
        }
    }
    when(viewUiState.state){
        ListContract.ListState.Loading -> {}
        ListContract.ListState.Empty -> EmptyLayout(title = "상품이 없습니다.")
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

/**
 * LazyVerticalGrid 이미지 로드 성능 이슈로 직접 구현하였습니다.
 * https://github.com/coil-kt/coil/issues/1610
 */
@Composable
fun ListLayout(
    lazyPagingItems: LazyPagingItems<BookEntities.Document>,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (Pair<BookEntities.Document, Boolean>) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(8.dp)) {
        items(lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index] ?: return@items
            if (index % 2 == 0) {
                ItemRow(
                    firstItem = item,
                    secondItem = if (index + 1 < lazyPagingItems.itemCount) lazyPagingItems[index + 1] else null,
                    onItemClick = onItemClick,
                    onBookmarkClick = onBookmarkClick
                )
            }
        }
    }
}