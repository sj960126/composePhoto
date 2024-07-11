package com.photo.feature_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.component.BookmarkIcon
import com.photo.presentation_core.design_system.LocalColors
import com.photo.presentation_core.design_system.LocalTypography
import com.photo.presentation_core.extension.convertPriceFormat
import com.photo.presentation_core.extension.showToast


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
                    context.showToast(effect.message)
                }
            }
        }
    }
    when(viewUiState.state){
        DetailContract.DetailState.Loading ->{}
        is DetailContract.DetailState.Success ->{
            val item = (viewUiState.state as DetailContract.DetailState.Success).item
            DetailItem(bookDetail = item, onBookmarkClick = { detailViewModel.setEvent(if(it) DetailContract.DetailEvent.AddBookmark(item) else DetailContract.DetailEvent.RemoveBookmark(item))})
        }
    }
}

@Composable
fun DetailItem(bookDetail: PhotoEntities.Document, onBookmarkClick : (Boolean) -> Unit){
    var isBookmarked by remember { mutableStateOf(bookDetail.isBookMark) }
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = rememberImagePainter(data = bookDetail.thumbnailUrl, builder = {
                crossfade(true)
            }),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd,
        ) {
            BookmarkIcon(
                isBookmarked = isBookmarked,
                onBookmarkClick = {
                    isBookmarked = !isBookmarked
                    onBookmarkClick.invoke(!bookDetail.isBookMark)
                }
            )
        }
    }
}