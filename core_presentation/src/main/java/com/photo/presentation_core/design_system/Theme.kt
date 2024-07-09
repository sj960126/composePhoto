package com.photo.presentation_core.design_system

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity

val LocalColors = staticCompositionLocalOf { lightColors }

val LocalTypography = staticCompositionLocalOf { TypographySystem(lightColors) }

@Composable
fun MainTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if(isDarkMode) darkColors else lightColors
    val typographySystem = TypographySystem(colors, LocalDensity.current)
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typographySystem,
        content = content
    )
}