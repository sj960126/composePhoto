package com.photo.feature_bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
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
import com.photo.presentation_core.component.DualPaneLayout
import com.photo.presentation_core.component.EmptyLayout
import com.photo.presentation_core.component.SearchBarLayout
import com.photo.presentation_core.component.SinglePaneLayout
import com.photo.presentation_core.design_system.LocalColors
import com.photo.presentation_core.design_system.LocalTypography
import com.photo.presentation_core.extension.showToast
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel = hiltViewModel(), isDualPane : Boolean,onItemClick: (String) -> Unit) {
    val viewUiState by bookmarkViewModel.uiState.collectAsState()
    var searchKeyWord by rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(bookmarkViewModel.effect){
        bookmarkViewModel.effect.collect { effect ->
            when (effect) {
                is BookmarkContract.BookmarkSideEffect.ShowToast -> {
                    context.showToast(context.getString(effect.id))
                }
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
        SearchBarLayout(modifier = Modifier.fillMaxWidth(), hint = stringResource(id = com.photo.presentation_core.R.string.bookmark_search_hint),labelTitle = stringResource(
            id = com.photo.presentation_core.R.string.bookmark_search_label
        ), text = searchKeyWord?:"", onTextChange = {searchKeyWord = it})
        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 8.dp, vertical = 5.dp),
            onClick = {bookmarkViewModel.setEvent(BookmarkContract.BookmarkEvent.ClearBookmark)},
            colors = ButtonDefaults.buttonColors(backgroundColor = LocalColors.current.secondary, contentColor = LocalColors.current.tintWhite),
            content = {
                Text(text = stringResource(id = com.photo.presentation_core.R.string.bookmark_all_delete_button), style = LocalTypography.current.body2)
            }
        )
        when(viewUiState.state){
            BookmarkContract.BookmarkState.Loading -> {}
            BookmarkContract.BookmarkState.Empty -> EmptyLayout(title = stringResource(id = com.photo.presentation_core.R.string.bookmark_empty))
            is BookmarkContract.BookmarkState.Success ->{
                if (isDualPane) {
                    DualPaneLayout(
                        itemList = (viewUiState.state as BookmarkContract.BookmarkState.Success).itemList,
                        onItemClick = onItemClick,
                        onBookmarkClick = {
                            bookmarkViewModel.handleEvent(if(it.second) BookmarkContract.BookmarkEvent.AddBookmark(it.first) else BookmarkContract.BookmarkEvent.RemoveBookmark(it.first))
                        }
                    )
                } else {
                    SinglePaneLayout(
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
}
