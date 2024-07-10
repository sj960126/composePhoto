package com.photo.feature_bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
                    context.showToast(effect.message)
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
        SearchBarLayout(modifier = Modifier.fillMaxWidth(), labelTitle = "컬렉션 검색", text = searchKeyWord?:"", onTextChange = {searchKeyWord = it})
        Text(modifier = Modifier.padding(start = 8.dp),text = "컬렉션을 검색해주세요", color = LocalColors.current.gray01, style = LocalTypography.current.body2)
        when(viewUiState.state){
            BookmarkContract.BookmarkState.Loading -> {}
            BookmarkContract.BookmarkState.Empty -> EmptyLayout(title = "상품이 없습니다.")
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
