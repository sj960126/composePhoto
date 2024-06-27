package com.book.presentation_core.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.book.domain.search.entities.BookEntities
import com.book.presentation_core.design_system.LocalColors
import com.book.presentation_core.design_system.LocalTypography
import com.book.presentation_core.design_system.MainTheme
import com.book.presentation_core.extension.convertPriceFormat
import com.book.presentation_core.extension.noRippleClickable
import com.google.gson.Gson

@Composable
fun ItemCard(onItemClick: (String) -> Unit, item: BookEntities.Document) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp)
            .noRippleClickable {
                onItemClick.invoke(Uri.encode(Gson().toJson(item)))
            }
    ) {
        Column(
            modifier = Modifier
                .background(LocalColors.current.white)
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = item.thumbnail),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (item.title?.isNotEmpty() == true )Text(
                text = item.title ?:"",
                style = LocalTypography.current.caption1,
                color= LocalColors.current.black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(8.dp))
            if((item.price ?: 0) > (item.salePrice ?: 0)) Row{
                Text(text = "원가", style = LocalTypography.current.caption2, color = LocalColors.current.primary)
                Text(modifier = Modifier.padding(start = 3.dp),text = item.price?.convertPriceFormat() ?:"", style = LocalTypography.current.caption2, color = LocalColors.current.gray02)
            }
            Row{
                Text(text = "판매가", style = LocalTypography.current.caption2, color = LocalColors.current.gray02)
                Text(modifier = Modifier.padding(start = 3.dp),text = item.salePrice?.convertPriceFormat() ?:"", style = LocalTypography.current.caption2, color = LocalColors.current.gray02)
            }
        }
    }
}
