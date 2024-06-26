package com.book.presentation_core.extension

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author songhyeonsu
 * Created 6/26/24 at 2:05 PM
 */

fun Int.textDp(density: Density?): TextUnit {
    return  if(density == null) {
        this.sp
    }else {
        with(density) {
            this@textDp.dp.toSp()
        }
    }
}