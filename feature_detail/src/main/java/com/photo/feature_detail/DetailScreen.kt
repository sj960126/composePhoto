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
import com.photo.domain.common.entities.BookEntities
import com.photo.presentation_core.component.BookmarkIcon
import com.photo.presentation_core.design_system.LocalColors
import com.photo.presentation_core.design_system.LocalTypography
import com.photo.presentation_core.extension.convertPriceFormat
import com.photo.presentation_core.extension.showToast


@Composable
fun DetailScreen(bookDetail: BookEntities.Document, detailViewModel: DetailViewModel = hiltViewModel()) {
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
fun DetailItem(bookDetail: BookEntities.Document, onBookmarkClick : (Boolean) -> Unit){
    var isBookmarked by remember { mutableStateOf(bookDetail.isBookMark) }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Box{
                    Image(
                        painter = rememberImagePainter(data = bookDetail.thumbnail, builder = {
                            crossfade(true)
                        }),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
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
                Spacer(modifier = Modifier.height(16.dp))
                bookDetail.title?.let {
                    Text(
                        text = it,
                        style = LocalTypography.current.title1,
                        maxLines = 2,
                        color = LocalColors.current.gray02,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "저자: ${bookDetail.authors?.joinToString()}",
                    style = LocalTypography.current.body1,
                    color = LocalColors.current.gray02
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "출판사: ${bookDetail.publisher}",
                    style = LocalTypography.current.body1,
                    color = LocalColors.current.gray02
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "가격: ${bookDetail.salePrice?.convertPriceFormat()})",
                    style = LocalTypography.current.body1,
                    color = LocalColors.current.gray02
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "판매 상태: ${bookDetail.status}",
                    style = LocalTypography.current.body1,
                    color = LocalColors.current.gray02
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "도서 설명:",
                    style = LocalTypography.current.body1,
                    color = LocalColors.current.gray02
                )
                Spacer(modifier = Modifier.height(8.dp))
                bookDetail.contents?.let {
                    Text(
                        text = it,
                        style = LocalTypography.current.body1,
                        color = LocalColors.current.gray02
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "출판일: ${bookDetail.datetime}",
                    style = LocalTypography.current.body1,
                    color = LocalColors.current.gray02
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}