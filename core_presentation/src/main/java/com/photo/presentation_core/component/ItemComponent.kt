package com.photo.presentation_core.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
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
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberImagePainter
import com.photo.domain.common.entities.PhotoEntities
import com.photo.presentation_core.design_system.LocalColors
import com.photo.presentation_core.design_system.LocalTypography
import com.photo.presentation_core.extension.convertPriceFormat
import com.photo.presentation_core.extension.noRippleClickable
import com.google.gson.Gson

@Composable
fun SinglePaneLayout(
    lazyPagingItems: LazyPagingItems<PhotoEntities.Document>,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (Pair<PhotoEntities.Document, Boolean>) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            if (item != null) {
                ItemCard(
                    modifier = Modifier.fillMaxWidth(),
                    onItemClick = onItemClick,
                    item = item,
                    onBookmarkClick = { isBookmarked ->
                        onBookmarkClick(item to isBookmarked)
                    }
                )
            }
        }
    }
}

@Composable
fun DualPaneLayout(
    lazyPagingItems: LazyPagingItems<PhotoEntities.Document>,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (Pair<PhotoEntities.Document, Boolean>) -> Unit
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            if (item != null) {
                ItemCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    onItemClick = onItemClick,
                    item = item,
                    onBookmarkClick = { isBookmarked ->
                        onBookmarkClick(item to isBookmarked)
                    }
                )
            }
        }
    }
}
@Composable
fun SinglePaneLayout(
    itemList : List<PhotoEntities.Document>,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (Pair<PhotoEntities.Document, Boolean>) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(itemList) {
            ItemCard(
                modifier = Modifier.fillMaxWidth(),
                onItemClick = onItemClick,
                item = it,
                onBookmarkClick = { isBookmarked ->
                    onBookmarkClick(it to isBookmarked)
                }
            )
        }
    }
}

@Composable
fun DualPaneLayout(
    itemList : List<PhotoEntities.Document>,
    onItemClick: (String) -> Unit,
    onBookmarkClick: (Pair<PhotoEntities.Document, Boolean>) -> Unit
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(itemList) {
            ItemCard(
                modifier = Modifier.fillMaxWidth(),
                onItemClick = onItemClick,
                item = it,
                onBookmarkClick = { isBookmarked ->
                    onBookmarkClick(it to isBookmarked)
                }
            )
        }
    }
}
@Composable
fun ItemCard(modifier: Modifier, onItemClick: (String) -> Unit, item: PhotoEntities.Document, onBookmarkClick : (Boolean) -> Unit) {
    var isBookmarked by remember { mutableStateOf(item.isBookMark) }
    LaunchedEffect(item.isBookMark) {
        isBookmarked = item.isBookMark
    }
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .noRippleClickable {
                onItemClick.invoke(Uri.encode(Gson().toJson(item)))
            }
    ) {
        Column(
            modifier = Modifier
                .background(LocalColors.current.white)
                .padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = item.thumbnailUrl,builder = {
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
            if(!item.collection.isNullOrEmpty()) Text(text = "컬렉션 : ${item.collection}", style = LocalTypography.current.body2, color = LocalColors.current.gray02)
            if(!item.displaySitename.isNullOrEmpty()) Text(text = "출처 : ${item.displaySitename}", style = LocalTypography.current.body3, color = LocalColors.current.gray02)
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