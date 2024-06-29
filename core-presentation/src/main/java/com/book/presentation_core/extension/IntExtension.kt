package com.book.presentation_core.extension

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

/**
 * @author songhyeonsu
 * Created 6/26/24 at 2:05 PM
 */

fun Int.textDp(density: Density?): TextUnit =
    if(density == null) {
        this.sp
    }else {
        with(density) {
            this@textDp.dp.toSp()
        }
    }


fun Int.convertPriceFormat(): String{
    val format = NumberFormat.getCurrencyInstance(Locale.KOREA) as DecimalFormat
    val customFormatSymbols = DecimalFormatSymbols(Locale.KOREA)
    customFormatSymbols.currencySymbol = ""
    format.decimalFormatSymbols = customFormatSymbols
    format.negativePrefix = "-"
    return format.format(this)+"원"
}


