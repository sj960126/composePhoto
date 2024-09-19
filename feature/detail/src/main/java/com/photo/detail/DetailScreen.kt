package com.photo.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.photo.domain.common.entities.PhotoEntities
import com.photo.component.BookmarkIcon
import com.photo.extension.showToast


@Composable
fun DetailScreen(bookDetail: PhotoEntities.Document, detailViewModel: DetailViewModel = hiltViewModel()) {
    val viewUiState by detailViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(bookDetail) {
        detailViewModel.setEvent(DetailContract.DetailEvent.Rending(bookDetail))
    }

    LaunchedEffect(detailViewModel.effect){
        detailViewModel.effect.collect { effect ->
            when (effect) {
                is DetailContract.DetailSideEffect.ShowToast -> {
                    context.showToast(context.getString(effect.id))
                }
            }
        }
    }
    when(viewUiState.state){
        DetailContract.DetailState.Loading ->{}
        is DetailContract.DetailState.Success ->{
            val item = (viewUiState.state as DetailContract.DetailState.Success).item
            DetailItem(bookDetail = item, onBookmarkClick = { detailViewModel.setEvent(if(it) DetailContract.DetailEvent.SaveBookmark(item) else DetailContract.DetailEvent.RemoveBookmark(item))})
        }
    }
}

@Composable
fun DetailItem(
    bookDetail: PhotoEntities.Document,
    onBookmarkClick: (Boolean) -> Unit
) {
    var isBookmarked by remember { mutableStateOf(bookDetail.isBookMark) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberImagePainter(data = bookDetail.thumbnailUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            com.photo.component.BookmarkIcon(
                isBookmarked = isBookmarked,
                onBookmarkClick = {
                    isBookmarked = !isBookmarked
                    onBookmarkClick(isBookmarked)
                }
            )
        }
    }
}
