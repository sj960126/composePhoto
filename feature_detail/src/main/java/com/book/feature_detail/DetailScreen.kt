package com.book.feature_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.book.domain.common.entities.BookEntities
import com.book.presentation_core.component.BookmarkIcon
import com.book.presentation_core.design_system.LocalColors
import com.book.presentation_core.design_system.LocalTypography
import com.book.presentation_core.design_system.MainTheme
import com.book.presentation_core.extension.convertPriceFormat


@Composable
fun DetailScreen(bookDetail: BookEntities.Document, detailViewModel: DetailViewModel = hiltViewModel()) {
    val viewUiState by detailViewModel.uiState.collectAsState()
    LaunchedEffect(bookDetail) {
        detailViewModel.setEvent(DetailContract.DetailEvent.Rending(bookDetail))
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
                        painter = rememberAsyncImagePainter(bookDetail.thumbnail),
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
@Preview
@Composable
fun PreviewDetailScreen() {
    MainTheme() {
        DetailScreen(
            bookDetail = BookEntities.Document(
                authors = arrayListOf("이상미", "문혜영"),
                contents = "에듀윌 IT자격증은 가장 효율적이고 빠른 합격의 길을 연구합니다. 〈2023 에듀윌 IT자격증 컴퓨터활용능력 1급 필기 초단기끝장〉은 딱! 공부할 핵심요약만, 딱! 풀어볼 기출문제만 수록하고, IT자격증 합격을 위한 다양한 부가서비스를 제공하여 누구보다 빠르게 IT자격증을 취득할 수 있는 교재입니다.",
                datetime = "2022-10-26T00:00:00.000+09:00",
                isbn = "1136020330 9791136020338",
                price = 22000,
                publisher = "에듀윌",
                salePrice = 19800,
                status = "정상판매",
                thumbnail = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F6205264%3Ftimestamp%3D20240625153254",
                title = "2023 에듀윌 EXIT 컴퓨터활용능력 1급 필기 초단기끝장",
                translators = arrayListOf(),
                url = "https://search.daum.net/search?w=bookpage&bookId=6205264&q=2023+%EC%97%90%EB%93%80%EC%9C%8C+EXIT+%EC%BB%B4%ED%93%A8%ED%84%B0%ED%99%9C%EC%9A%A9%EB%8A%A5%EB%A0%A5+1%EA%B8%89+%ED%95%84%EA%B8%B0+%EC%B4%88%EB%8B%A8%EA%B8%B0%EB%81%9D%EC%9E%A5"
            )
        )
    }
}