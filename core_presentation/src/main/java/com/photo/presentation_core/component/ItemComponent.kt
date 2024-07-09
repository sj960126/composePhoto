package com.photo.presentation_core.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.photo.domain.common.entities.BookEntities
import com.photo.presentation_core.design_system.LocalColors
import com.photo.presentation_core.design_system.LocalTypography
import com.photo.presentation_core.extension.convertPriceFormat
import com.photo.presentation_core.extension.noRippleClickable
import com.google.gson.Gson

@Composable
fun ItemCard(modifier: Modifier,onItemClick: (String) -> Unit, item: BookEntities.Document, onBookmarkClick : (Boolean) -> Unit) {
    var isBookmarked by remember { mutableStateOf(item.isBookMark) }
    LaunchedEffect(item.isBookMark) {
        isBookmarked = item.isBookMark
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .noRippleClickable {
                onItemClick.invoke(Uri.encode(Gson().toJson(item)))
            }
    ) {
        Column(
            modifier = Modifier
                .background(LocalColors.current.white)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = item.thumbnail,builder = {
                        crossfade(true)
                    }),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    BookmarkIcon(
                        isBookmarked = isBookmarked,
                        onBookmarkClick = {
                            isBookmarked = !isBookmarked
                            onBookmarkClick.invoke(!item.isBookMark)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (item.title?.isNotEmpty() == true )Text(
                text = item.title ?:"",
                style = LocalTypography.current.caption1,
                color= LocalColors.current.black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "글쓴이 ${item.authors?.joinToString()}", style = LocalTypography.current.caption2, color = LocalColors.current.gray02)
            if((item.price ?: 0) > (item.salePrice ?: 0)) Row{
                Text(text = "원가", style = LocalTypography.current.caption2, color = LocalColors.current.primary)
                Text(modifier = Modifier.padding(start = 3.dp),text = item.price?.convertPriceFormat() ?:"", style = LocalTypography.current.caption2, color = LocalColors.current.gray02)
            }
            Row{
                Text(text = "판매가", style = LocalTypography.current.caption2, color = LocalColors.current.primary)
                Text(modifier = Modifier.padding(start = 3.dp),text = item.salePrice?.convertPriceFormat() ?:"", style = LocalTypography.current.caption2, color = LocalColors.current.gray02)
            }
        }
    }
}

@Composable
fun BookmarkIcon(
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit
) {
    IconButton(
        onClick = onBookmarkClick,
    ) {
        Icon(
            imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = null,
            tint = if (isBookmarked) LocalColors.current.primary else LocalColors.current.gray02
        )
    }
}
@Composable
fun EmptyLayout(title : String) {
    Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = LocalTypography.current.title2,
            color = LocalColors.current.gray03
        )
    }
}

@Composable
fun ItemRow(
    firstItem: BookEntities.Document,
    secondItem: BookEntities.Document?,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (Pair<BookEntities.Document, Boolean>) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        ItemCard(modifier = Modifier.weight(1f),item = firstItem, onItemClick = onItemClick, onBookmarkClick = {onBookmarkClick.invoke(Pair(firstItem,it))})
        if (secondItem != null) ItemCard(modifier = Modifier.weight(1f),item = secondItem, onItemClick = onItemClick, onBookmarkClick = {onBookmarkClick.invoke(Pair(secondItem,it))}) else Spacer(modifier = Modifier.weight(1f))
    }
}