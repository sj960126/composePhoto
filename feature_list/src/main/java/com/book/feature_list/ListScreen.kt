package com.book.feature_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.book.presentation_core.design_system.LocalTypography
import com.book.presentation_core.extension.noRippleClickable

data class ListItem(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String, // You can use ImageBitmap, painter, etc. for actual images
    var isFavorite: Boolean = false
)

val dummyData = List(20) { index ->
    ListItem(
        id = index,
        title = "Title $index",
        description = "Description for item $index",
        thumbnail = ""
    )
}

@Composable
fun ListScreen(navController: NavController) {
    Column{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(dummyData) { item ->
                ListItemCard(navController, item)
            }
        }
    }
}

@Composable
fun ListItemCard(navController: NavController, item: ListItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .noRippleClickable {
                // Navigate to detail screen
                // navController.navigate("detail/${item.id}")
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = LocalTypography.current.body2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.description,
                style = LocalTypography.current.body2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}