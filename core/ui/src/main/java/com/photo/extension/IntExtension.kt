package com.photo.extension

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun Int.textDp(density: Density?): TextUnit =
    if(density == null) {
        this.sp
    }else {
        with(density) {
            this@textDp.dp.toSp()
        }
    }


