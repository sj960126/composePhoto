package com.book.feature_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.book.domain.search.entities.BookEntities
import com.book.presentation_core.design_system.LocalColors
import com.book.presentation_core.design_system.LocalTypography
import com.book.presentation_core.extension.noRippleClickable

@Composable
fun DetailScreen(bookDetail: BookEntities.Document) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "도서 상세 정보") },
            backgroundColor = LocalColors.current.primary
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = bookDetail.thumbnail),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(16.dp))
            bookDetail.title?.let {
                Text(
                    text = it,
                    style = LocalTypography.current.headLine1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "저자: ${bookDetail.authors?.joinToString()}",
                style = LocalTypography.current.headLine2,
                color = LocalColors.current.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "출판사: ${bookDetail.publisher}",
                style = LocalTypography.current.headLine2,
                color = LocalColors.current.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "가격: ${bookDetail.salePrice}원 (정상가 ${bookDetail.price}원)",
                style = LocalTypography.current.headLine2,
                color = LocalColors.current.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "판매 상태: ${bookDetail.status}",
                style = LocalTypography.current.headLine2,
                color = LocalColors.current.secondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "도서 설명:",
                style = LocalTypography.current.headLine2,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = LocalColors.current.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            bookDetail.contents?.let {
                Text(
                    text = it,
                    style = LocalTypography.current.headLine2,
                    color = LocalColors.current.secondary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "출판일: ${bookDetail.datetime}",
                style = LocalTypography.current.headLine2,
                color = LocalColors.current.secondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "자세히 보기",
                style = LocalTypography.current.headLine2,
                color = LocalColors.current.primary,
                modifier = Modifier.noRippleClickable {
                    // Handle click to navigate to the detail URL
                    // 예를 들어서 브라우저 열기 등의 액션을 수행할 수 있습니다.
                }
            )
        }
    }
}
