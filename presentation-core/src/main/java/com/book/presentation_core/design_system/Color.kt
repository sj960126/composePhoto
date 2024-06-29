package com.book.presentation_core.design_system

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color


/**
 * @author songhyeonsu
 * Created 3/5/24 at 11:23 AM
 */

@Immutable
data class ColorSystem(
    val black : Color,
    val gray01 : Color,
    val gray02 : Color,
    val gray03 : Color,
    val gray04 : Color,
    val gray05 : Color,
    val gray06 : Color,
    val gray07 : Color,
    val gray08 : Color,
    val gray09 : Color,
    val white :Color,
    val primary :Color,
    val secondary : Color,
    val tertiary : Color,
    val dimMax :Color,
    val dimHigh : Color,
    val dimMid : Color,
    val dimLow : Color,
    val transparent : Color,
    val tintWhite : Color
)

val lightColors: ColorSystem = ColorSystem(
    black = Color(0xFF1a1c20),
    gray01 =Color(0xFF292A2B),
    gray02 =Color(0xFF363A3C),
    gray03 =Color(0xFF4D5256),
    gray04 =Color(0xFF878D91),
    gray05 =Color(0xFFA9AFB3),
    gray06 =Color(0xFFCED3D6),
    gray07 =Color(0xFFE1E4E6),
    gray08 =Color(0xFFF2F4F5),
    gray09 =Color(0xFFFAFAFA),
    white = Color(0xFFFFFFFF),
    primary = Color(0xFFF3554B),
    secondary = Color(0xFFEE7C63),
    tertiary = Color(0xFF454A53),
    transparent = Color(0),
    dimMax = Color(0xFF000000),
    dimHigh = Color(0xB3000000),
    dimMid = Color(0x7F000000),
    dimLow = Color(0x4D000000),
    tintWhite = Color(0xFFFFFFFF)
)

val darkColors: ColorSystem = ColorSystem(
    black = Color(0xFFFFFFFF),
    gray01 =Color(0xFFEDEDED),
    gray02 =Color(0xFFE1E1E1),
    gray03 =Color(0xFF9E9E9E),
    gray04 =Color(0xFF949596),
    gray05 =Color(0xFF818283),
    gray06 =Color(0xFF6F7072),
    gray07 =Color(0xFF303236),
    gray08 =Color(0xFF38393C),
    gray09 =Color(0xFF282A2D),
    white = Color(0xFF191A1D),
    primary = Color(0xFFDC473E),
    secondary = Color(0xFFEE7C63),
    tertiary = Color(0xFF454A53),
    transparent = Color(0),
    dimMax = Color(0xFF000000),
    dimHigh = Color(0xB3000000),
    dimMid = Color(0x7F000000),
    dimLow = Color(0x4D000000),
    tintWhite = Color(0xFFFFFFFF)
)
