package com.photo.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.photo.component.DualPaneLayout
import com.photo.component.EmptyLayout
import com.photo.component.SearchBarLayout
import com.photo.component.SinglePaneLayout
import com.photo.design_system.LocalColors
import com.photo.design_system.LocalTypography
import com.photo.domain.common.entities.PhotoEntities
import com.photo.extension.showToast
import com.photo.ui.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun BookmarkRoute(
    isDualPane: Boolean,
    onNavigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
){
    val viewUiState by bookmarkViewModel.uiState.collectAsStateWithLifecycle()
    val rememberCoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(bookmarkViewModel.effect){
        bookmarkViewModel.effect.collect { effect ->
            when (effect) {
                is BookmarkContract.BookmarkSideEffect.ShowToast -> context.showToast(context.getString(effect.id))
                is BookmarkContract.BookmarkSideEffect.MoveDetailPage -> onNavigateToDetail(effect.item)
            }
        }
    }
    BookmarkScreen(
        isDualPane = isDualPane,
        searchKeyWord = viewUiState.keyword?:"",
        uiState = viewUiState.state,
        rememberCoroutineScope = rememberCoroutineScope,
        onNavigateToDetail = {

        },
        onSearchChange = {
            bookmarkViewModel.handleEvent(event = BookmarkContract.BookmarkEvent.Search(it))
        },
        onSaveBookmark = {
            bookmarkViewModel.handleEvent(event = BookmarkContract.BookmarkEvent.SaveBookmark(it))
        },
        onRemoveBookmark = {
            bookmarkViewModel.handleEvent(event = BookmarkContract.BookmarkEvent.RemoveBookmark(it))
        },
        onClearBookmark = {
            bookmarkViewModel.handleEvent(event = BookmarkContract.BookmarkEvent.ClearBookmark)
        },
        modifier = modifier
    )

}
@Composable
internal fun BookmarkScreen(
    isDualPane : Boolean,
    searchKeyWord: String,
    uiState : BookmarkContract.BookmarkUiState,
    rememberCoroutineScope : CoroutineScope,
    onSearchChange: (String) -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onClearBookmark : () -> Unit,
    onSaveBookmark : (PhotoEntities.Document) -> Unit,
    onRemoveBookmark : (PhotoEntities.Document) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier){
        BookmarkSearchBar(
            searchKeyWord = searchKeyWord,
            onSearchChange = {
                rememberCoroutineScope.launch {
                    flowOf(it)
                        .debounce(1000)
                        .collectLatest { keyword ->
                            onSearchChange(keyword)
                        }
                }
            }
        )
        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 8.dp, vertical = 5.dp),
            onClick = {
                onClearBookmark()
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
            viewUiState = uiState,
            isDualPane = isDualPane,
            onItemClick = {
                onNavigateToDetail(it)
            },
            onBookmarkClick = {
                val (item,isSelect) = it
                if(isSelect) onSaveBookmark(item) else onRemoveBookmark(item)
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
    when(viewUiState) {
        BookmarkContract.BookmarkUiState.Loading -> Unit
        BookmarkContract.BookmarkUiState.Empty -> EmptyLayout(
            title = stringResource(id = R.string.bookmark_empty)
        )
        is BookmarkContract.BookmarkUiState.Success -> {
            val itemList = viewUiState.itemList
            if (isDualPane) {
                DualPaneLayout(itemList = itemList, onItemClick = onItemClick, onBookmarkClick = onBookmarkClick)
            } else {
                SinglePaneLayout(itemList = itemList, onItemClick = onItemClick, onBookmarkClick = onBookmarkClick)
            }
        }
    }
}
