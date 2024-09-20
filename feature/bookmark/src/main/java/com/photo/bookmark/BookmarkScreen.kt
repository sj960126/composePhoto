package com.photo.bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.photo.component.DualPaneLayout
import com.photo.component.EmptyLayout
import com.photo.component.SearchBarLayout
import com.photo.component.SinglePaneLayout
import com.photo.ui.R
import com.photo.design_system.LocalColors
import com.photo.design_system.LocalTypography
import com.photo.domain.common.entities.PhotoEntities
import com.photo.extension.showToast
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun BookmarkScreen(
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    isDualPane : Boolean,
    onNavigateToDetail: (String) -> Unit
) {

    val viewUiState by bookmarkViewModel.uiState.collectAsState()
    var searchKeyWord by rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(bookmarkViewModel.effect){
        bookmarkViewModel.effect.collect { effect ->
            when (effect) {
                is BookmarkContract.BookmarkSideEffect.ShowToast -> context.showToast(context.getString(effect.id))
                is BookmarkContract.BookmarkSideEffect.MoveDetailPage -> onNavigateToDetail.invoke(effect.item)
            }
        }
    }

    LaunchedEffect(searchKeyWord) {
        snapshotFlow { searchKeyWord }
            .filterNotNull()
            .debounce(1000)
            .collect { keyword ->
                bookmarkViewModel.handleEvent(if(keyword.isBlank()) BookmarkContract.BookmarkEvent.GetBookmarkList else BookmarkContract.BookmarkEvent.Search(keyword))
            }
    }

    Column{
        BookmarkSearchBar(
            searchKeyWord = searchKeyWord,
            onSearchChange = {
                searchKeyWord = it
            }
        )
        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 8.dp, vertical = 5.dp),
            onClick = {
                bookmarkViewModel.setEvent(BookmarkContract.BookmarkEvent.ClearBookmark)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = LocalColors.current.secondary,
                contentColor = LocalColors.current.tintWhite
            ),
            content = {
                Text(
                    text = stringResource(id = R.string.bookmark_all_delete_button),
                    style = LocalTypography.current.body2
                )
            }
        )
        BookmarkContent(
            viewUiState = viewUiState,
            isDualPane = isDualPane,
            onItemClick = {
                bookmarkViewModel.handleEvent(BookmarkContract.BookmarkEvent.ClickItem(it))
            },
            onBookmarkClick = {
                val (item,isBookMark) = it
                bookmarkViewModel.handleEvent(
                    if (isBookMark) BookmarkContract.BookmarkEvent.SaveBookmark(item) else BookmarkContract.BookmarkEvent.RemoveBookmark(item)
                )
            }
        )
    }
}

@Composable
fun BookmarkSearchBar(
    searchKeyWord: String?,
    onSearchChange: (String) -> Unit
) {
    SearchBarLayout(
        modifier = Modifier.fillMaxWidth(),
        hint = stringResource(id = R.string.bookmark_search_hint),
        labelTitle = stringResource(id = R.string.bookmark_search_label),
        text = searchKeyWord ?: "",
        onTextChange = onSearchChange
    )
}

@Composable
fun BookmarkContent(
    viewUiState: BookmarkContract.BookmarkUiState,
    isDualPane: Boolean,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (Pair<PhotoEntities.Document, Boolean>) -> Unit
) {
    when(viewUiState.state) {
        BookmarkContract.BookmarkState.Loading -> {
            // 로딩 UI 처리
        }
        BookmarkContract.BookmarkState.Empty -> EmptyLayout(
            title = stringResource(id = R.string.bookmark_empty)
        )
        is BookmarkContract.BookmarkState.Success -> {
            val itemList = viewUiState.state.itemList
            if (isDualPane) {
                DualPaneLayout(itemList = itemList, onItemClick = onItemClick, onBookmarkClick = onBookmarkClick)
            } else {
                SinglePaneLayout(itemList = itemList, onItemClick = onItemClick, onBookmarkClick = onBookmarkClick)
            }
        }
    }
}
