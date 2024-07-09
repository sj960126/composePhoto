package com.photo.feature_bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.photo.domain.common.entities.BookEntities
import com.photo.presentation_core.component.EmptyLayout
import com.photo.presentation_core.component.ItemRow
import com.photo.presentation_core.design_system.LocalColors
import com.photo.presentation_core.design_system.LocalTypography
import com.photo.presentation_core.extension.showToast

@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel = hiltViewModel(), onItemClick: (String) -> Unit) {
    val viewUiState by bookmarkViewModel.uiState.collectAsState()
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
    Column{
        PriceFilter(onSearchClick = {bookmarkViewModel.handleEvent(BookmarkContract.BookmarkEvent.PriceFilter(it.first,it.second))})
        AuthorFilter(onAuthorSearch = {bookmarkViewModel.handleEvent(BookmarkContract.BookmarkEvent.AuthorFilter(it))})
        SortFilterLayout(onSortClick = { bookmarkViewModel.handleEvent(BookmarkContract.BookmarkEvent.SortTitle(it))})
        when(viewUiState.state){
            BookmarkContract.BookmarkState.Loading -> {}
            BookmarkContract.BookmarkState.Empty -> EmptyLayout(title = "상품이 없습니다.")
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
@Composable
fun SortFilterLayout(onSortClick :(SortDefine) -> Unit ){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        SortDefine.values().forEach {
            Button(modifier = Modifier.weight(1f),onClick = { onSortClick.invoke(it) }, colors = ButtonDefaults.buttonColors( backgroundColor = LocalColors.current.primary,contentColor = LocalColors.current.tintWhite,)) {
                Text("${it.title}정렬")
            }
        }
    }
}

@Composable
fun PriceFilter(onSearchClick: (Pair<Int, PriceFilterDefine>) -> Unit) {
    var filterPrice by remember { mutableStateOf("") }

    Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterEditLayout(
            modifier = Modifier.weight(1f),
            labelTitle = "금액 필터",
            text = filterPrice,
            onTextChange = { filterPrice = it },
            keyboardType = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        PriceFilterDefine.values().forEach { filterDefine ->
            Button(
                onClick = { onSearchClick(Pair(filterPrice.toIntOrNull()?:0, filterDefine)) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalColors.current.primary,
                    contentColor = LocalColors.current.tintWhite
                ),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(filterDefine.title)
            }
        }
    }
}
@Composable
fun AuthorFilter(onAuthorSearch: (String) -> Unit) {
    var filterAuthor by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterEditLayout(
            modifier = Modifier.weight(1f),
            labelTitle = "저자검색",
            text = filterAuthor,
            onTextChange = { filterAuthor = it }
        )
        Button(
            onClick = { onAuthorSearch(filterAuthor) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = LocalColors.current.primary,
                contentColor = LocalColors.current.tintWhite
            ),
        ) {
            Text("검색")
        }
    }
}

@Composable
fun FilterEditLayout(
    modifier: Modifier,
    labelTitle: String,
    text: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(labelTitle, style = LocalTypography.current.caption2) },
        modifier = modifier
            .padding(end = 8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = LocalColors.current.transparent,
            focusedBorderColor = LocalColors.current.primary,
            unfocusedBorderColor = LocalColors.current.primary,
            cursorColor = LocalColors.current.primary
        ),
        textStyle = LocalTypography.current.caption2,
        keyboardOptions= keyboardType
    )
}

/**
 * LazyVerticalGrid 이미지 로드 성능 이슈로 직접 구현하였습니다.
 * https://github.com/coil-kt/coil/issues/1610
 */
@Composable
fun BookmarkListLayout(itemList: List<BookEntities.Document>, onItemClick: (String) -> Unit,onBookmarkClick : (Pair<BookEntities.Document,Boolean>) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(8.dp)) {
        items(itemList.size) { index ->
            if (index % 2 == 0) {
                ItemRow(
                    firstItem = itemList[index],
                    secondItem = itemList.getOrNull(index +1),
                    onItemClick = onItemClick,
                    onBookmarkClick = onBookmarkClick
                )
            }
        }
    }
}

enum class SortDefine(val title : String) {
    ASCENDING("오름차순"),
    DESCENDING("내림차순")
}
enum class PriceFilterDefine(val title : String) {
    UP("이상"),
    DOWN("이하")
}