package com.book.presentation_core.design_system

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import com.book.presentation_core.R
import com.book.presentation_core.extension.textDp

@Immutable
data class TypographySystem(
    private val colors: ColorSystem,
    private val density: Density? = null,
    val baseTextStyle: TextStyle = TextStyle(
        color = colors.black,
    ),
    val headLine1: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.textDp(density),
        lineHeight = 30.textDp(density)
    ),
    val headLine2: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Bold,
        fontSize = 24.textDp(density),
    ),
    val headLine3: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.textDp(density),
    ),
    val headLine4: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.textDp(density),
    ),
    val headLine5: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Black,
        fontSize = 15.textDp(density),
    ),
    val headLine6: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Normal,
        fontSize = 20.textDp(density),
    ),
    val largeTitle1: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Bold,
        fontSize = 18.textDp(density),
        lineHeight = 26.textDp(density)
    ),
    val largeTitle2: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.textDp(density),
        lineHeight = 26.textDp(density)
    ),
    val largeTitle3: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.textDp(density),
        lineHeight = 26.textDp(density)
    ),
    val largeTitle4: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.textDp(density),
        lineHeight = 26.textDp(density)
    ),
    val largeTitle5: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 17.textDp(density),
        lineHeight = 24.textDp(density)
    ),
    val largeTitle6: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 17.textDp(density),
        lineHeight = 24.textDp(density)
    ),
    val largeTitle7: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.textDp(density),
        lineHeight = 24.textDp(density)
    ),
    val largeTitle8: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.textDp(density),
        lineHeight = 24.textDp(density)
    ),
    val title1: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Bold,
        fontSize = 16.textDp(density),
        lineHeight = 18.textDp(density)
    ),
    val title2: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.textDp(density),
        lineHeight = 18.textDp(density)
    ),
    val title3: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Normal,
        fontSize = 16.textDp(density),
        lineHeight = 18.textDp(density)
    ),
    val title4: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Normal,
        fontSize = 14.textDp(density),
        lineHeight = 18.textDp(density)
    ),
    val title5: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.textDp(density),
        lineHeight = 16.textDp(density)
    ),
    val title6: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Bold,
        fontSize = 14.textDp(density),
        lineHeight = 16.textDp(density)
    ),
    val title7: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Bold,
        fontSize = 16.textDp(density),
        lineHeight = 20.textDp(density)
    ),
    val body1: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Normal,
        fontSize = 13.textDp(density),
        lineHeight = 18.textDp(density)
    ),
    val body2: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 13.textDp(density),
        lineHeight = 18.textDp(density)
    ),
    val body3: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Bold,
        fontSize = 12.textDp(density),
        lineHeight = 14.textDp(density)
    ),
    val body4: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Normal,
        fontSize = 13.textDp(density),
        lineHeight = 14.textDp(density)
    ),
    val body5: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 12.textDp(density),
        lineHeight = 14.textDp(density)
    ),
    val body6: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Bold,
        fontSize = 12.textDp(density),
        lineHeight = 14.textDp(density)
    ),
    val body7: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 13.textDp(density),
        lineHeight = 22.textDp(density)
    ),
    val body8: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 13.textDp(density),
        lineHeight = 22.textDp(density)
    ),
    val body9: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.textDp(density),
        lineHeight = 22.textDp(density)
    ),
    val caption1: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Bold,
        fontSize = 10.textDp(density),
        lineHeight = 14.textDp(density)
    ),
    val caption2: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Normal,
        fontSize = 10.textDp(density),
        lineHeight = 16.textDp(density)
    ),
    val caption3: TextStyle = baseTextStyle.copy(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 10.textDp(density),
        lineHeight = 14.textDp(density)
    ),
    val caption4: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 12.textDp(density),
        lineHeight = 16.textDp(density)
    ),
    val caption5: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.textDp(density),
        lineHeight = 16.textDp(density)
    ),
    val caption6: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 10.textDp(density),
        lineHeight = 14.textDp(density)
    ),
    val caption7: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 10.textDp(density),
        lineHeight = 13.textDp(density)
    ),
    val caption8: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 9.textDp(density),
        lineHeight = 13.textDp(density)
    ),
    val caption9: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 11.textDp(density),
        lineHeight = 14.textDp(density)
    ),
    val caption10: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 11.textDp(density),
        lineHeight = 13.textDp(density)
    ),
    val label1: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 15.textDp(density),
    ),
    val label2: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 15.textDp(density),
    ),
    val label3: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 14.textDp(density),
    ),
    val label4: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.textDp(density),
    ),
    val label5: TextStyle = baseTextStyle.copy(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.textDp(density),
    ),
    )


val nanumSquare = FontFamily(
    Font(R.font.nanumsquare_neo_heavy,FontWeight.Black, FontStyle.Normal),
    Font(R.font.nanumsquare_otfb,FontWeight.Bold, FontStyle.Normal),
    Font(R.font.nanumsquare_otfeb,FontWeight.ExtraBold,FontStyle.Normal),
    Font(R.font.nanumsquare_otfl,FontWeight.Light,FontStyle.Normal),
    Font(R.font.nanumsquare_otfr,FontWeight.Normal,FontStyle.Normal)
)

private val pretendard = FontFamily(
    Font(R.font.pretendard_bold,FontWeight.Bold,FontStyle.Normal),
    Font(R.font.pretendard_extra_bold,FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.pretendard_regular,FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_semi_bold,FontWeight.SemiBold,FontStyle.Normal)
)
